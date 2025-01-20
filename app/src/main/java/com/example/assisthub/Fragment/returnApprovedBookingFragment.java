package com.example.assisthub.Fragment;

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
import com.example.assisthub.Adapter.returnApprovedBookingAdapter;
import com.example.assisthub.Adapter.returnBookingAdapter;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.ApprovedBookingRetrofit;
import com.example.assisthub.Retrofit.retrofitFile.returnBookingsRetrofit;
import com.example.assisthub.Shared_pereferences_profile.SharedPrefManager;
import com.example.assisthub.model.ApprovedBookingModel;
import com.example.assisthub.model.returnBookings;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class returnApprovedBookingFragment  extends Fragment {

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
    ListView returnApprovedBooking;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.return_approved_booking_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        returnApprovedBooking = view.findViewById(R.id.returnApprovedBooking);


        Call<List<ApprovedBookingModel>> call = ApprovedBookingRetrofit.checkInstance()
                .getServicesInterface().approved_booking(
                        SharedPrefManager.getInstance(getContext()).getUserData().getId());



        call.enqueue(new Callback<List<ApprovedBookingModel>>() {
            @Override
            public void onResponse(Call<List<ApprovedBookingModel>> call, Response<List<ApprovedBookingModel>> response) {

                List<ApprovedBookingModel> list = response.body();
                returnApprovedBookingAdapter adapter = new returnApprovedBookingAdapter(getContext() , list);
                returnApprovedBooking.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<ApprovedBookingModel>> call, Throwable t) {
                Log.d("error return booking " , t.getMessage());
            }
        });
    }
}
