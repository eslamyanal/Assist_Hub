package com.example.assisthub.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.RetrofitSignUpIndividualWithFile;
import com.example.assisthub.Retrofit.retrofitFile.RetrofitSignUpIndividualWithoutFile;
import com.example.assisthub.helper.FileUtils;
import com.example.assisthub.model.CheckModel;
import com.google.android.material.textfield.TextInputLayout;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    private   TextInputLayout FullName , PhoneNumber , Password , Nationality_Number , Passport_Number ;

    private Spinner spinnerNationality;
    private String[] NationalityForSpinner = new String[]{"Select Nationality State", "Jordanian", "Non-Jordanian"};

    private Button signUpButton , uploadButton;
    private LinearLayout loginLayout , layoutFor_NonJo, layout_jo, uploadLayout;

    private RadioGroup Health_InsuranceForJO  , Health_InsuranceForNonJO;
    private   static byte idNationality  ;
    private static String  textHealth_InsuranceJO , textHealth_InsuranceNonJO ;

    //for upload  file
    private TextView filePathTextView;
    private Uri fileUri;
    private String filePath;


    // defining our own password pattern
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        //mirror object
        FullName                 = findViewById(R.id.FullName);
        PhoneNumber              = findViewById(R.id.PhoneNumber);
        Password                 = findViewById(R.id.Password);

        spinnerNationality       = findViewById(R.id.spinnerNationality);
        layout_jo                = findViewById(R.id.layoutfor_jo);
        layoutFor_NonJo          = findViewById(R.id.layoutfornon_jo);

        Passport_Number          = findViewById(R.id.Passport_Number);
        Nationality_Number       = findViewById(R.id.Nationality_Number);

        Health_InsuranceForJO    = findViewById(R.id.Health_InsuranceForJO);
        Health_InsuranceForNonJO = findViewById(R.id.Health_InsuranceForNonJO);

        uploadLayout             = findViewById(R.id.uploadLayout);
        uploadButton             = findViewById(R.id.uploadButton);
        filePathTextView         = findViewById(R.id.filePathTextView);

        signUpButton             = findViewById(R.id.signUpButton);
        loginLayout              = findViewById(R.id.loginLayout);


        //event
        uploadButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        loginLayout .setOnClickListener(this);

        spinnerNationality      .setOnItemSelectedListener(this);
        Health_InsuranceForJO   .setOnCheckedChangeListener(this);
        Health_InsuranceForNonJO.setOnCheckedChangeListener(this);

        //set adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, NationalityForSpinner);
        spinnerNationality.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.uploadButton){

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf"); // Ensures only PDF files
            filePickerLauncher.launch(intent);

        }else if (v.getId() == R.id.loginLayout){
            startActivity(new Intent(SignUpActivity.this , LoginActivity.class));
            finish();
        }
        else if (v.getId() == R.id.signUpButton){

            if (isNotEmpty(FullName) && checkFullName(FullName) && isNotEmpty(PhoneNumber) && checkPhone(PhoneNumber) && isNotEmpty(Password) && checkPassword(Password))
            {
                if (idNationality != 0 )
                {

                    if(idNationality == 1 ){
                        //for jordanian
                        if(isNotEmpty(Nationality_Number))
                            if (textHealth_InsuranceJO == null){
                                Toast.makeText(getApplicationContext(),"choose the type of health insurance",Toast.LENGTH_LONG).show();
                                return;
                            }
                            else
                            {
                                if(textHealth_InsuranceJO.equals("No health insurance"))
                                    signupWithoutFile();
                                else if (filePath != null )
                                        uploadFile(filePath ,textHealth_InsuranceJO);
                                    else
                                        Toast.makeText(getApplicationContext(), "Please select a file first.", Toast.LENGTH_SHORT).show();

                            }

                    }else {

                        if (isNotEmpty(Passport_Number))
                             if (textHealth_InsuranceNonJO == null){
                                Toast.makeText(getApplicationContext(),"Choose the official documents type, please",Toast.LENGTH_LONG).show();
                                return;
                            }
                            else if (filePath != null )
                                uploadFile(filePath  ,textHealth_InsuranceNonJO );
                             else
                                Toast.makeText(getApplicationContext(), "Please select a file first.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"choose your Nationality ",Toast.LENGTH_LONG).show();

            }
        }
    }


    private  void signupWithoutFile(){
            Call<CheckModel> call = RetrofitSignUpIndividualWithoutFile.checkInstance()
                    .getServicesInterface() .SignUpUserWithoutFile(
                            FullName   .getEditText().getText().toString().trim(),
                            PhoneNumber.getEditText().getText().toString().trim(),
                            Password   .getEditText().getText().toString().trim(),
                            NationalityForSpinner[idNationality],
                            Nationality_Number.getEditText().getText().toString().trim(),
                            textHealth_InsuranceJO);

            call.enqueue(new Callback<CheckModel>() {
                @Override
                public void onResponse(Call<CheckModel> call, Response<CheckModel> response) {

                    if (!response.body().geterror()) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CheckModel> call, Throwable t) {
                    Log.d("API Failure", "Error: " + t.getMessage());

                }
            });

    }


    private final ActivityResultLauncher<Intent> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    fileUri = result.getData().getData();
                    filePath = FileUtils.getPath(this, fileUri);
                    if (filePath != null) {
                        filePathTextView.setText(filePath); // Display the file path
                    } else {
                        Toast.makeText(this, "Unable to retrieve file path.", Toast.LENGTH_SHORT).show();
                    }
                }
            });



    private void uploadFile(String filePath , String Health_InsuranceOrOfficialDocuments) {

        File file = new File(filePath);

        if (!file.exists()) {
            Toast.makeText(this, "File not found at the specified path.", Toast.LENGTH_SHORT).show();
            return;
        }

        sentValue(file , Health_InsuranceOrOfficialDocuments);
    }


    private void  sentValue (File file , String Health_InsuranceOrOfficialDocuments){

        String Nationality_Number_or_Passport_Number ="";

        if (idNationality == 1)
            Nationality_Number_or_Passport_Number = Nationality_Number.getEditText().getText().toString().trim() ;
         else if (idNationality == 2)
            Nationality_Number_or_Passport_Number =Passport_Number.getEditText().getText().toString().trim() ;


        RequestBody requestBody         = RequestBody.Companion.create(file, MediaType.parse("application/pdf"));
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename            = RequestBody.Companion.create(file.getName(), MediaType.parse("text/plain"));


        RequestBody name                          = RequestBody.Companion.create(FullName.getEditText().getText().toString().trim(),MediaType.parse("text/plain"));
        RequestBody phone                         = RequestBody.Companion.create(PhoneNumber.getEditText().getText().toString().trim(), MediaType.parse("text/plain"));
        RequestBody password                      = RequestBody.Companion.create(Password.getEditText().getText().toString().trim(), MediaType.parse("text/plain"));
        RequestBody nationality                   = RequestBody.Companion.create(NationalityForSpinner[idNationality], MediaType.parse("text/plain"));
        RequestBody nationalityOrPassportNumber   = RequestBody.Companion.create(Nationality_Number_or_Passport_Number, MediaType.parse("text/plain"));
        RequestBody healthInsurance               = RequestBody.Companion.create(Health_InsuranceOrOfficialDocuments, MediaType.parse("text/plain"));

        Call<CheckModel> call = RetrofitSignUpIndividualWithFile.checkInstance().getServicesInterface().SignUpUserWithFile(
                fileToUpload, filename,name, phone, password,nationality,nationalityOrPassportNumber,healthInsurance);

        call.enqueue(new Callback<CheckModel>() {

            @Override
            public void onResponse(Call<CheckModel> call, Response<CheckModel> response) {
                if (response.isSuccessful()) {
                    CheckModel responseBody = response.body();
                    if (responseBody != null) {
                        Log.d("API Response", "Success: " + responseBody.getMessage());
                        if (!responseBody.geterror()) {

                            Toast.makeText(getApplicationContext(), responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();

                        } else
                            Toast.makeText(getApplicationContext(), responseBody.getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        // Handle the case where responseBody is null
                        Log.d("API Response Error", "Response body is null.");
                        Toast.makeText(getApplicationContext(), "Failed to upload, response is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Log response code and error body if response is not successful
                    Log.d("API Response Error", "Response code: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            Log.d("API Response Error", "Error Body: " + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("API Response", "Full response: " + response.toString());
            }

            @Override
            public void onFailure(Call<CheckModel> call, Throwable t) {

                if (t instanceof IOException)
                    Log.d("API Failure", "Network error: " + t.getMessage());

                else
                    // Other errors
                    Log.d("API Failure", "Error: " + t.getMessage());

                Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public  static boolean checkPhone (TextInputLayout textInputLayout){

        String  phoneUser = textInputLayout.getEditText().getText().toString().trim();

        if (!Patterns.PHONE.matcher(phoneUser).matches()) {
            textInputLayout.setError("sorry your  phone is not correct >_< ");
            return false;
        }
        else if((phoneUser.startsWith("079") || phoneUser.startsWith("078") || phoneUser.startsWith("077"))&& phoneUser.length() == 10){
            textInputLayout.setErrorEnabled(false);
            return true;
        }
        else{
            textInputLayout.setError("sorry your  phone is not correct the phone should start with 079 or 078 or 077  >_< ");
            return false;
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getPositionForView(view)){

            case 1:
                idNationality = 1;
                layout_jo.setVisibility(View.VISIBLE);
                layoutFor_NonJo.setVisibility(View.GONE);
                uploadLayout.setVisibility(View.VISIBLE);
                break;
            case 2:
                idNationality = 2;
                layout_jo.setVisibility(View.GONE);
                layoutFor_NonJo.setVisibility(View.VISIBLE);
                uploadLayout.setVisibility(View.VISIBLE);
                break;

            default:
                idNationality = 0;
                layout_jo.setVisibility(View.GONE);
                layoutFor_NonJo.setVisibility(View.GONE);
                uploadLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.MilitaryRadioButton){
            textHealth_InsuranceJO = "Military";
            textHealth_InsuranceNonJO = null;

        } else if(checkedId == R.id.Civilian){
            textHealth_InsuranceJO = "Civilian";
            textHealth_InsuranceNonJO = null;

        }else if (checkedId == R.id.NoHealthInsurance){
            textHealth_InsuranceJO = "No health insurance";
            textHealth_InsuranceNonJO = null;

        } else if (checkedId == R.id.ALMOfauadia_Unhcr){
            textHealth_InsuranceJO = null;
            textHealth_InsuranceNonJO = "(al mofauadia )  UNHCR ";

        }else if (checkedId == R.id.ForeignMedicalTourism){
            textHealth_InsuranceJO = null;
            textHealth_InsuranceNonJO = "Foreign / Medical tourism";

        }else if (checkedId == R.id.officialDocumentsFromTheCountry){
            textHealth_InsuranceJO = null;
            textHealth_InsuranceNonJO = " official documents from the country ";

        }else{
            textHealth_InsuranceJO = null;
            textHealth_InsuranceNonJO = null;
        }

    }


    public static boolean checkFullName(TextInputLayout textInputLayout) {

        String fullName = textInputLayout.getEditText().getText().toString().trim().toUpperCase();
        String[] nameParts = fullName.split(" ");

        for (String part : nameParts)
            if (!part.matches("[A-Z]+")) {
                textInputLayout.setError("Name should contain only letters.");
                return false;
            }

        if (nameParts.length < 2) {
            textInputLayout.setError("Please enter at least two names.");
            return false;
        }

        textInputLayout.setErrorEnabled(false);
        return true;
    }


    public static boolean checkPassword(TextInputLayout textInputLayout) {

        String password = textInputLayout.getEditText().getText().toString().trim();
        Matcher matcher = PASSWORD_PATTERN.matcher(password);

        if (!matcher.matches()){
            textInputLayout.setError
            ("Weak password: The password must be 8-20 characters" +
            " long and include at least one uppercase letter, " +
            "one lowercase letter, one number, and one special character.");

            return false;
        }

        return matcher.matches();
    }


    public static boolean isNotEmpty(TextInputLayout textInputLayout ){

        String  textInput = textInputLayout.getEditText().getText().toString().trim();

        if(textInput.isBlank()){
            textInputLayout.setError("Field must not be blank >_<");
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }


}