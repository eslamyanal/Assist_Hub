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
import com.example.assisthub.Adapter.volunteerAdapter;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.volunteerRetrofit;
import com.example.assisthub.model.volunteer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class volunteer_fragment  extends Fragment {

    ListView  volunteerListView;


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
        return inflater.inflate(R.layout.volunteer_fragment_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        volunteerListView = view.findViewById(R.id.volunteerListView);

        Call <List<volunteer>>  call = volunteerRetrofit.
                checkInstance().getServicesInterface().volunteer();

        call.enqueue(new Callback<List<volunteer>>() {
            @Override
            public void onResponse(Call<List<volunteer>> call, Response<List<volunteer>> response) {

                List<volunteer> list = response.body();
                volunteerAdapter adapter = new volunteerAdapter(getContext() , list);
                volunteerListView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<volunteer>> call, Throwable t) {
                Log.d("error volunteer " , t.getMessage());
            }
        });
    }
}
