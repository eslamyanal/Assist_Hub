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
import com.example.assisthub.Adapter.returnBookingAdapter;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.returnBookingsRetrofit;
import com.example.assisthub.Shared_pereferences_profile.SharedPrefManager;
import com.example.assisthub.model.returnBookings;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class returnBookingFragment extends Fragment {
    ListView returnBooking;

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
        return inflater.inflate(R.layout.return_booking_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        returnBooking = view.findViewById(R.id.returnBooking);

        Call<List<returnBookings>> call = returnBookingsRetrofit.checkInstance().
                getServicesInterface().returnBookings(
                        SharedPrefManager.getInstance(getContext()).getUserData().getId());

        call.enqueue(new Callback<List<returnBookings>>() {
            @Override
            public void onResponse(Call<List<returnBookings>> call, Response<List<returnBookings>> response) {
                List<returnBookings> list = response.body();
                returnBookingAdapter adapter = new returnBookingAdapter(getContext() , list);
                returnBooking.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<returnBookings>> call, Throwable t) {
                Log.d("error return booking " , t.getMessage());

            }
        });
    }

}
