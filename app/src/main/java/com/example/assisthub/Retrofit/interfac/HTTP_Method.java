package com.example.assisthub.Retrofit.interfac;

import com.example.assisthub.model.ApprovedBookingModel;
import com.example.assisthub.model.RequestsRejectedModel;
import com.example.assisthub.model.Result_User_Model;
import com.example.assisthub.model.ServiceModel;
import com.example.assisthub.model.CheckModel;
import com.example.assisthub.model.deleteRequestModel;
import com.example.assisthub.model.hospital_model;
import com.example.assisthub.model.returnBookings;
import com.example.assisthub.model.volunteer;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface HTTP_Method {

    @FormUrlEncoded
    @POST("login.php")
    Call<Result_User_Model> loginUser(
            @Field("PhoneUser") String phoneUser,
            @Field("passwordUser") String passwordUser
    );

    @Multipart
    @POST("SignUpWithFile.php")
    Call<CheckModel> SignUpUserWithFile(
            @Part MultipartBody.Part file,
            @Part("filename") RequestBody filename,
            @Part("NameUser") RequestBody name,
            @Part("PhoneUser") RequestBody phone,
            @Part("passwordUser") RequestBody password,
            @Part("NationalityUser") RequestBody nationality,
            @Part("Nationality_Or_Passport_Number") RequestBody nationalityOrPassportNumber,
            @Part("Health_Insurance") RequestBody healthInsurance
    );


    @FormUrlEncoded
    @POST("SignUpWithoutFile.php")
    Call<CheckModel> SignUpUserWithoutFile(
            @Field("NameUser") String name,
            @Field("PhoneUser") String phone,
            @Field("passwordUser") String password,
            @Field("NationalityUser") String nationality,
            @Field("Nationality_Number") String nationalityOrPassportNumber,
            @Field("Health_Insurance") String healthInsurance
    );


    @FormUrlEncoded
    @POST("UpdateUserData.php")
    Call<Result_User_Model> UpdateUserData(
            @Field("UserId") int UserId,
            @Field("NameUser") String NameUser,
            @Field("PhoneUser") String PhoneUser,
            @Field("passwordUser") String passwordUser
    );


    @GET("hospital.php")
    Call<List<hospital_model>> getHospital(
            @Query("region") int fkIdRegion,
            @Query("TypeOfHospital") int fkIdType
    );

    @FormUrlEncoded
    @POST("Service.php")
    Call<List<ServiceModel>> getService(
            @Field("IDHospital") int idHospital,
            @Field("idChooseDoctor") int idChooseDoctor,
            @Field("idChooseBed") int idChooseBed,
            @Field("IDSectionDoctor") int idSectionDoctor,
            @Field("IDSectionBed") int idSectionBed
    );


    @FormUrlEncoded
    @POST("bookingRequests.php")
    Call <CheckModel>  checkBookingRequests(
            @Field("id_hospital_fk") int id_hospital_fk,
            @Field("section_id") int section_id,
            @Field("id_doctor") int id_doctor,
            @Field("id_bed") int id_bed,
            @Field("period") String period,
            @Field("dateBed") String dateBed,
            @Field("cost") int cost,
            @Field("User_id") int User_id
    );

    @GET("volunteer.php")
    Call<List<volunteer>> volunteer();

    @FormUrlEncoded
    @POST("returnBookings.php")
    Call<List<returnBookings>> returnBookings (
            @Field("userId") int userId
    );

    @FormUrlEncoded
    @POST("deleteRequestModel.php")
    Call <deleteRequestModel> deleteRequests (
            @Field("idRequset") int idRequest
    );
    @FormUrlEncoded
    @POST("approved_booking.php")
    Call <List<ApprovedBookingModel>> approved_booking (
            @Field("idUser") int idUser
    );


    @FormUrlEncoded
    @POST("RequestsRejectedModel.php")
    Call <List<RequestsRejectedModel>> RequestsRejected (
            @Field("idUser") int idUser
    );

    @FormUrlEncoded
    @POST("IsReturnPayment.php")
    Call <CheckModel> IsReturnPayment (
            @Field("idRequest") int idRequest
    );

    @FormUrlEncoded
    @POST("deleteReturnMoneyRetrofit.php")
    Call <CheckModel> deleteReturnMoney (
            @Field("idRequset") int idRequest
    );

    @FormUrlEncoded
    @POST("VerificationPhoneNumber.php")
    Call <CheckModel> VerificationPhoneNumber (
            @Field("PhoneUser") String  PhoneUser
    );

    @FormUrlEncoded
    @POST("updateNewPassword.php")
    Call <CheckModel> updateNewPassword (
            @Field("PhoneUser") String  PhoneUser,
            @Field("passwordUser") String  passwordUser
    );

}
