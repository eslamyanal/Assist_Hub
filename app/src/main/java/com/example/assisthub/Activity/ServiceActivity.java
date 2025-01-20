package com.example.assisthub.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.example.assisthub.Fragment.show_service_Fragment;
import com.example.assisthub.R;

public class ServiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner doctor_sectionsSpinner , bed_sectionsSpinner;
    String[] doctorSections = {"All Department", "Emergency Department", "Surgery Department"};
    String[] bedSections = {"All Beds", "Emergency Beds", "Surgery Beds"};

    FragmentTransaction fragmentTransaction;
    Toolbar toolbarService;

    public  static  byte  IDHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service);

        Intent i = getIntent();
        byte getIDHospital= (byte) i.getIntExtra("IDHospital", 0);
        String NameHospital = i.getStringExtra("NameHospital");
        IDHospital = getIDHospital;


        //mirror object
        doctor_sectionsSpinner = findViewById(R.id.doctor_sections);
        bed_sectionsSpinner = findViewById(R.id.bed_sections);
        toolbarService = findViewById(R.id.toolbarService);
        ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, doctorSections);
        ArrayAdapter<String> bedAdapter    = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bedSections);
        fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutService,new show_service_Fragment()).addToBackStack(null);


        //actions
        toolbarService.setTitle(NameHospital);
        setSupportActionBar(toolbarService);
        doctor_sectionsSpinner.setAdapter(doctorAdapter);
        bed_sectionsSpinner.setAdapter(bedAdapter);
        fragmentTransaction.commit();

        //events
        doctor_sectionsSpinner.setOnItemSelectedListener(this);
        bed_sectionsSpinner.setOnItemSelectedListener(this);
        callFragmentFetchService(1,0 ,0 , 0);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (parent.getId() == R.id.doctor_sections) {
            bed_sectionsSpinner.setSelection(0);

                switch (position) {
                    case 1:
                        callFragmentFetchService(1, 0, 1, 0);
                        break;

                    case 2:
                        callFragmentFetchService(1, 0, 2, 0);
                        break;

                    default:
                        callFragmentFetchService(1, 0, 0, 0);
                }


        }
        else if (parent.getId() == R.id.bed_sections) {

            doctor_sectionsSpinner.setSelection(0);
                switch (position) {
                    case 1:
                        callFragmentFetchService(0, 1, 0, 1);
                        break;

                    case 2:
                        callFragmentFetchService(0, 1, 0, 2);
                        break;

                    default:
                        callFragmentFetchService(0, 1, 0, 0);
                }
        }
    }


    private void callFragmentFetchService(int chooseDoctor , int chooseBed , int SectionDoctorPosition , int sectionBedPosition) {
        show_service_Fragment fragment = (show_service_Fragment)
                getSupportFragmentManager().findFragmentById(R.id.frameLayoutService);
        if (fragment != null)
            fragment.fetchService(IDHospital,chooseDoctor,chooseBed,SectionDoctorPosition, sectionBedPosition);
         else
            Log.d("ServiceActivity", "Fragment not found or not attached!");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}