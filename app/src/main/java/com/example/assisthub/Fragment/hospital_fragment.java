package com.example.assisthub.Fragment;

import static com.example.assisthub.Activity.HomeActivity.CurrentRegionPosition;
import static com.example.assisthub.Activity.HomeActivity.TypeOfHospitalPosition;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assisthub.Activity.HomeActivity;
import com.example.assisthub.Adapter.HospitalAdapter;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.HospitalRetrofit;
import com.example.assisthub.model.hospital_model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class hospital_fragment extends Fragment {


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

    private ListView HospitalList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listview_for_home_user_to_show_hospital, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HospitalList = view.findViewById(R.id.HospitalList);
        fetchHospitals();
    }

    public void fetchHospitals() {
        if (HospitalList == null) return; // Ensure the ListView is initialized

        Call<List<hospital_model>> call = HospitalRetrofit.checkInstance()
                .getServicesInterface().getHospital(CurrentRegionPosition, TypeOfHospitalPosition);

        call.enqueue(new Callback<List<hospital_model>>() {
            @Override
            public void onResponse(Call<List<hospital_model>> call, Response<List<hospital_model>> response) {
                List<hospital_model> hospitalList = response.body();
                HospitalAdapter adapter = new HospitalAdapter(getContext(), hospitalList);
                HospitalList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<hospital_model>> call, Throwable t) {
                Log.d("API Failure return hospital", "Error: " + t.getMessage());
            }
        });
    }
}

