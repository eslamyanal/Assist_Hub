package com.example.assisthub.Fragment;

import static com.example.assisthub.Activity.SignUpActivity.checkFullName;
import static com.example.assisthub.Activity.SignUpActivity.checkPassword;
import static com.example.assisthub.Activity.SignUpActivity.checkPhone;
import static com.example.assisthub.Activity.SignUpActivity.isNotEmpty;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.assisthub.Activity.HomeActivity;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.updateUserDataRetrofit;
import com.example.assisthub.Shared_pereferences_profile.SharedPrefManager;
import com.example.assisthub.model.Result_User_Model;
import com.example.assisthub.model.User_model;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserDataFragment extends Fragment implements View.OnClickListener {
    private TextInputLayout FullName, PhoneNumber, Password, Nationality_Number, Nationality;
    private Button cancel_buttonUpdate, updatedata;


    @Override
    public  void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle back button press
                Intent intent = new Intent(requireContext(), HomeActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_user_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

            //mirror object
            FullName = view.findViewById(R.id.FullName);
            PhoneNumber = view.findViewById(R.id.PhoneNumber);
            Password = view.findViewById(R.id.Password);
            Nationality = view.findViewById(R.id.Nationality);
            Nationality_Number = view.findViewById(R.id.Nationality_Number);
            updatedata = view.findViewById(R.id.updatedata);
            cancel_buttonUpdate = view.findViewById(R.id.cancel_buttonUpdate);

            User_model userData = SharedPrefManager.getInstance(getContext()).getUserData();
            if (userData != null) {
                setTextInputValue(FullName, userData.getName_User());
                setTextInputValue(PhoneNumber, userData.getPhone());
                setTextInputValue(Password, userData.getPassword_User());
                setTextInputValue(Nationality_Number, userData.getNationaltyOrPassportNumber());
                setTextInputValue(Nationality, userData.getNationality());
            }

            // Disable uneditable fields
            Nationality_Number.setEnabled(false);
            Nationality.setEnabled(false);

            // events
            cancel_buttonUpdate.setOnClickListener(this);
            updatedata.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_buttonUpdate) {
            getActivity().finish();
            getActivity().overridePendingTransition(0, 0);
            startActivity(new Intent(getContext(), HomeActivity.class));
            getActivity().overridePendingTransition(0, 0);
        }else if (v.getId() == R.id.updatedata) {
            updateUserData();
        }
    }

    private void updateUserData() {
        try {

             if (isNotEmpty(FullName) && checkFullName(FullName) && isNotEmpty(PhoneNumber)
                     &&checkPhone(PhoneNumber) && isNotEmpty(Password) && checkPassword(Password)) {

                // Make API call
                Call<Result_User_Model> call = updateUserDataRetrofit.checkInstance().getServicesInterface()
                        .UpdateUserData(
                                SharedPrefManager.getInstance(getContext()).getUserData().getId(),
                                getTextInputValue(FullName),
                                getTextInputValue(PhoneNumber),
                                getTextInputValue(Password));

                     call.enqueue(new Callback<Result_User_Model>() {
                         @Override
                         public void onResponse(Call<Result_User_Model> call, Response<Result_User_Model> response) {

                             if (!response.body().isError()) {
                                 User_model userModel = new User_model(
                                         response.body().getUser().getId(),
                                         response.body().getUser().getName_User(),
                                         response.body().getUser().getPhone(),
                                         response.body().getUser().getNationality(),
                                         response.body().getUser().getPassword_User(),
                                         response.body().getUser().getNationaltyOrPassportNumber(),
                                         response.body().getUser().getHealth_Insurance(),
                                         response.body().getUser().getFile_Name()
                                 );

                                 SharedPrefManager.getInstance(getContext()).updateData(userModel);

                                 HomeActivity activity = (HomeActivity) getActivity();
                                     activity.setHeaderData();

                                 Toast.makeText(getContext(), response.body().getMessage() , Toast.LENGTH_LONG).show();

                                 startActivity(new Intent(getContext(), HomeActivity.class));

                             } else {
                                 Log.e("API Error", "Error: " + response.body().getMessage());
                                 Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<Result_User_Model> call, Throwable t) {
                             Log.e("API Failure", "Throwable: ", t);
                             Toast.makeText(getContext(), "Failed to update user data.", Toast.LENGTH_SHORT).show();
                         }
                     });


                 }
        } catch (Exception e) {
            Log.e("UpdateUserData", "Error: " + e.getMessage());
            Toast.makeText(getContext(), "An error occurred. Please check your input.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setTextInputValue(TextInputLayout input, String value) {
        if (input.getEditText() != null) {
            input.getEditText().setText(value != null ? value.trim() : "");
        }
    }


    //return data from input
    private String getTextInputValue(TextInputLayout input) {
        return input.getEditText() != null ? input.getEditText().getText().toString().trim() : "";
    }
}
