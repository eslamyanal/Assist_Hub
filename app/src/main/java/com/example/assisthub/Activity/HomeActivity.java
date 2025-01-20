package com.example.assisthub.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.assisthub.Fragment.About_us_fragment;
import com.example.assisthub.Fragment.RequestsRejectedFragment;
import com.example.assisthub.Fragment.TermsAndCondition_Fragment;
import com.example.assisthub.Fragment.UpdateUserDataFragment;
import com.example.assisthub.Fragment.contact_us_fragment;
import com.example.assisthub.Fragment.hospital_fragment;
import com.example.assisthub.Fragment.returnApprovedBookingFragment;
import com.example.assisthub.Fragment.returnBookingFragment;
import com.example.assisthub.Fragment.volunteer_fragment;
import com.example.assisthub.R;
import androidx.appcompat.widget.Toolbar;
import com.example.assisthub.Shared_pereferences_profile.SharedPrefManager;
import com.example.assisthub.model.User_model;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private final String [] hospitalCurrentRegion  = { "all" , "The capital, Amman" , "Jerash" , "Al-Karak"};
    private final String [] TypesOfHospitalList    = { "all" , "government " , "private" , "Military"};
    Spinner CurrentRegion , TypeOfHospital  ;
    TextView TypeOfHospitalText  , CurrentRegionText;
    public    static byte  CurrentRegionPosition = 0  , TypeOfHospitalPosition = 0  ;

    FragmentTransaction fragmentTransaction ,fragmentTransactionMenu ;
    DrawerLayout DrawerLayoutHomeUser;
    Toolbar toolBarHome;
    NavigationView NavigationViewHomeUser;


    public  static TextView phone ,name , NationalityNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        if (!SharedPrefManager.getInstance(getApplicationContext()).isLogin()) {
            startActivity(new Intent(this , SplashScreen.class));
            finish();
        }


        //mirror object
        CurrentRegion      = findViewById(R.id.CurrentRegion);
        TypeOfHospital     = findViewById(R.id.TypeOfHospitalSpinner);
        TypeOfHospitalText = findViewById(R.id.TypeOfHospitalText);
        CurrentRegionText  = findViewById(R.id.CurrentRegionText);
        toolBarHome = findViewById(R.id.toolBarHome);
        DrawerLayoutHomeUser = findViewById(R.id.DrawerLayoutHomeUser);
        NavigationViewHomeUser = findViewById(R.id.NavigationViewHome);
        View view = NavigationViewHomeUser.getHeaderView(0);
        name = view.findViewById(R.id.UserNameNavigationView);
        NationalityNavigationView = view.findViewById(R.id.NationalityNavigationView);
        phone = view.findViewById(R.id.PhoneNavigationView);
        fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutHospital,new hospital_fragment()).addToBackStack(null);
        fragmentTransactionMenu = getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutHomeMenu , new hospital_fragment()).addToBackStack(null);

        setSupportActionBar(toolBarHome);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, hospitalCurrentRegion);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, TypesOfHospitalList);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle
                (this,DrawerLayoutHomeUser,toolBarHome,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        //events
        fragmentTransaction.commit();
        callFragmentFetchHospitals();//call fragment to fetch hospitals
        CurrentRegion.setAdapter(adapter1);
        TypeOfHospital.setAdapter(adapter2);
        CurrentRegion.setOnItemSelectedListener(this);
        TypeOfHospital.setOnItemSelectedListener(this);
        NavigationViewHomeUser.setNavigationItemSelectedListener(this);
        NavigationViewHomeUser.setNavigationItemSelectedListener(this);
        DrawerLayoutHomeUser.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        setHeaderData(); //to show data inside the header
    }

    public void setHeaderData() {
        User_model userData = SharedPrefManager.getInstance(this).getUserData();
        if (userData != null) {
            name.setText(SharedPrefManager.getInstance(HomeActivity.this).getUserData().getName_User());
            phone.setText(SharedPrefManager.getInstance(HomeActivity.this).getUserData().getPhone());
            NationalityNavigationView.setText(SharedPrefManager.getInstance(HomeActivity.this).getUserData().getNationality());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setHeaderData(); // Ensure this is called whenever you return to the activity
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if ( parent.getId() == R.id.CurrentRegion){
                switch (parent.getPositionForView(view)){

                    case 1:
                        CurrentRegionPosition = 1;
                        callFragmentFetchHospitals();
                        break;
                    case 2:
                        CurrentRegionPosition = 2;
                        callFragmentFetchHospitals();
                        break;
                    case 3:
                        CurrentRegionPosition = 3;
                        callFragmentFetchHospitals();
                        break;
                    default:
                        CurrentRegionPosition = 0;
                        callFragmentFetchHospitals();

                }
            }
            else if (parent.getId() == R.id.TypeOfHospitalSpinner ){
                switch (parent.getPositionForView(view)){
                    case 1:
                        TypeOfHospitalPosition = 1;
                        callFragmentFetchHospitals();
                        break;
                    case 2:
                        TypeOfHospitalPosition = 2;
                        callFragmentFetchHospitals();
                        break;
                    case 3:
                        TypeOfHospitalPosition = 3;
                        callFragmentFetchHospitals();
                        break;
                    default:
                        TypeOfHospitalPosition = 0;
                        callFragmentFetchHospitals();
                }
            }
    }

    private void callFragmentFetchHospitals() {
        hospital_fragment fragment = (hospital_fragment)
                getSupportFragmentManager().findFragmentById(R.id.frameLayoutHospital);

        if (fragment != null)
            fragment.fetchHospitals();
        else
            Log.d("HomeActivity", "Fragment not found or not attached!");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

     if(item.getItemId()==R.id.logout_navigationView){
         finish();
         SharedPrefManager.getInstance(getApplicationContext()).logout();
     }
     else if (item.getItemId() == R.id.nav_home){
         //for those who don't want to see that blink after recreate() method simply use
         finish();
         overridePendingTransition(0, 0);
         startActivity(new Intent(getApplicationContext(), HomeActivity.class));
         overridePendingTransition(0, 0);

         TypeOfHospital.setEnabled(true);
         CurrentRegion .setEnabled(true);

     }
     else if (item.getItemId() == R.id.About_us_navigationView){
         setEditNav();

         fragmentTransaction = getSupportFragmentManager().beginTransaction()
                 .replace(R.id.frameLayoutHomeMenu,new About_us_fragment()).addToBackStack(null);
         fragmentTransaction.commit();
         DrawerLayoutHomeUser.close();
     }
     else if (item.getItemId() == R.id.Profile_navigationView) {
         setEditNav();

         fragmentTransaction = getSupportFragmentManager().beginTransaction()
                 .replace(R.id.frameLayoutHomeMenu,new UpdateUserDataFragment()).addToBackStack(null);
         fragmentTransaction.commit();
         DrawerLayoutHomeUser.close();
     }
     else if (item.getItemId() == R.id.volunteer){
         setEditNav();

         fragmentTransaction = getSupportFragmentManager().beginTransaction()
                 .replace(R.id.frameLayoutHomeMenu,new volunteer_fragment()).addToBackStack(null);
         fragmentTransaction.commit();
         DrawerLayoutHomeUser.close();
     }
     else if (item.getItemId() == R.id.TermsAndCondition) {
         setEditNav();

         fragmentTransaction = getSupportFragmentManager().beginTransaction()
                 .replace(R.id.frameLayoutHomeMenu,new TermsAndCondition_Fragment()).addToBackStack(null);
         fragmentTransaction.commit();
         DrawerLayoutHomeUser.close();
     }
     else if (item.getItemId() == R.id.BookingRequests) {
         setEditNav();

         fragmentTransaction = getSupportFragmentManager().beginTransaction()
                 .replace(R.id.frameLayoutHomeMenu,new returnBookingFragment()).addToBackStack(null);
         fragmentTransaction.commit();
         DrawerLayoutHomeUser.close();
     }
     else if (item.getItemId() == R.id.contact_us) {
         setEditNav();

         fragmentTransaction = getSupportFragmentManager().beginTransaction()
                 .replace(R.id.frameLayoutHomeMenu,new contact_us_fragment()).addToBackStack(null);
         fragmentTransaction.commit();
         DrawerLayoutHomeUser.close();
     }
     else if (item.getItemId() == R.id.ApprovedBookings){
         setEditNav();

         fragmentTransaction = getSupportFragmentManager().beginTransaction()
                 .replace(R.id.frameLayoutHomeMenu,new returnApprovedBookingFragment()).addToBackStack(null);
         fragmentTransaction.commit();
         DrawerLayoutHomeUser.close();
     }
     else if (item.getItemId() == R.id.RequestsRejected){
         setEditNav();

         fragmentTransaction = getSupportFragmentManager().beginTransaction()
                 .replace(R.id.frameLayoutHomeMenu,new RequestsRejectedFragment()).addToBackStack(null);
         fragmentTransaction.commit();
         DrawerLayoutHomeUser.close();
     }

        return true;
    }

    private void setEditNav(){

        // Clear the back stack to ensure no fragments remain behind
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        TypeOfHospital.setEnabled(false);
        CurrentRegion.setEnabled(false);
        TypeOfHospital.setVisibility(View.GONE);
        CurrentRegion.setVisibility(View.GONE);
        TypeOfHospitalText.setVisibility(View.GONE);
        CurrentRegionText.setVisibility(View.GONE);
    }
}