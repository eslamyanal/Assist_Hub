package com.example.assisthub.Fragment;

import static com.example.assisthub.Activity.ServiceActivity.IDHospital;

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
import com.example.assisthub.Adapter.ServicesAdapter;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.ServiceRetrofit;
import com.example.assisthub.model.ServiceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class show_service_Fragment extends Fragment {


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


    private ListView serviceList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.service_listview, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        serviceList= view.findViewById(R.id.serviceList);
        fetchService(IDHospital, 1, 0, 0, 0);
    }



    public void fetchService(int IDHospital , int ChooseDoctor , int ChooseBed , int SectionDoctorPosition , int SectionBedPosition) {


        Call<List<ServiceModel>> call = ServiceRetrofit.checkInstance()
                .getServicesInterface().getService
                        (IDHospital,ChooseDoctor,ChooseBed,SectionDoctorPosition,SectionBedPosition);

        call.enqueue(new Callback<List<ServiceModel>>() {
            @Override
            public void onResponse(Call<List<ServiceModel>> call, Response<List<ServiceModel>> response) {
                    List<ServiceModel> serviceModels = response.body();
                        ServicesAdapter adapter = new ServicesAdapter(getContext(), serviceModels);
                        serviceList.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<ServiceModel>> call, Throwable t) {
                Log.d("API Failure return service", "Error: " + t.getMessage());

            }
        });

    }





}
