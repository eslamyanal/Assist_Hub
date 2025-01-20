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
import com.example.assisthub.Adapter.ReturnRequestsRejectedAdapter;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.RequestsRejectedRetrofit;
import com.example.assisthub.Shared_pereferences_profile.SharedPrefManager;
import com.example.assisthub.model.RequestsRejectedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestsRejectedFragment extends Fragment {


    ListView RequestsRejected;

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
        return inflater.inflate(R.layout.requests_rejected_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RequestsRejected = view.findViewById(R.id.RequestsRejected);

        Call<List<RequestsRejectedModel>> call = RequestsRejectedRetrofit.getInstance()
                .getServicesInterface().RequestsRejected(
                        SharedPrefManager.getInstance(getContext()).getUserData().getId());

        call.enqueue(new Callback<List<RequestsRejectedModel>>() {
            @Override
            public void onResponse(Call<List<RequestsRejectedModel>> call, Response<List<RequestsRejectedModel>> response) {

                List<RequestsRejectedModel> list = response.body();
                ReturnRequestsRejectedAdapter adapter = new ReturnRequestsRejectedAdapter(getContext() , list);
                RequestsRejected.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<RequestsRejectedModel>> call, Throwable t) {
                Log.d("error return Requests Rejected booking " , t.getMessage());

            }
        });

    }
}
