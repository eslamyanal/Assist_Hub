package com.example.assisthub.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import com.example.assisthub.Activity.HomeActivity;
import com.example.assisthub.Activity.ReturnMoney;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.DeleteRequest;
import com.example.assisthub.model.deleteRequestModel;
import com.example.assisthub.model.returnBookings;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class returnBookingAdapter extends ArrayAdapter<returnBookings> {
    private List<returnBookings> returnBookings;
    private Context context;

    public returnBookingAdapter(Context context, List <returnBookings> returnBookingsList) {
        super(context , R.layout.return_bookings_custom_layout, returnBookingsList);

        this.context = context;
        this.returnBookings = returnBookingsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.return_bookings_custom_layout,parent,false);

        //mirror object
        TextView name_hospital = convertView.findViewById(R.id.name_hospitalCard);
        TextView SectionCard = convertView.findViewById(R.id.SectionCard);
        TextView DoctorOrBedCard = convertView.findViewById(R.id.DoctorOrBedCard);
        TextView periodCard = convertView.findViewById(R.id.periodCard);
        TextView stateCard = convertView.findViewById(R.id.stateCard);
        TextView DateBedCard = convertView.findViewById(R.id.DateBedCard);
        Button cancelButtonCard = convertView.findViewById(R.id.cancelButtonCard);
        CardView cardReturnBooking = convertView.findViewById(R.id.cardReturnBooking);

        if(returnBookings.get(position).getId() != 0){

            cardReturnBooking.setVisibility(View.VISIBLE);
            name_hospital.setText(returnBookings.get(position).getHospital());
            SectionCard.setText(returnBookings.get(position).getSection());
            stateCard.setText(returnBookings.get(position).getState());

        }
        else{
            cardReturnBooking.setVisibility(View.VISIBLE);
            cardReturnBooking.setBackgroundResource(R.drawable.no_reservation);
            cardReturnBooking.setMinimumWidth(200);
            cardReturnBooking.setMinimumHeight(500);
            name_hospital.setVisibility(View.GONE);
            SectionCard.setVisibility(View.GONE);
            DoctorOrBedCard.setVisibility(View.GONE);
            stateCard.setVisibility(View.GONE);
            cancelButtonCard.setVisibility(View.GONE);

        }


        if(returnBookings.get(position).getDoctor() != null){
            DoctorOrBedCard.setText("you have booked with : "+returnBookings.get(position).getDoctor());
            periodCard.setVisibility(View.GONE);
            DateBedCard.setVisibility(View.GONE);
        }
        else if(returnBookings.get(position).getBed() != null){
            DoctorOrBedCard.setText("you have booked : "+returnBookings.get(position).getBed());
            periodCard.setVisibility(View.VISIBLE);
            periodCard.setText("The period you booked : "+returnBookings.get(position).getPeriod());
            DateBedCard.setVisibility(View.VISIBLE);
            DateBedCard.setText("The date you booked : "+returnBookings.get(position).getDateBed());
        }

        cancelButtonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create alert dialog
                AlertDialog alertDialog =  new MaterialAlertDialogBuilder(getContext()).
                        setTitle("Are you Sure ?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Call<deleteRequestModel> call =
                                        DeleteRequest.checkInstance().getServicesInterface()
                                                .deleteRequests(returnBookings.get(position).getId());

                                call.enqueue(new Callback<deleteRequestModel>() {
                                    @Override
                                    public void onResponse(Call<deleteRequestModel> call, Response<deleteRequestModel> response) {

                                        if(!response.body().isError())
                                        {
                                             if(response.body().isReturnMoney()){

                                                Intent i = new Intent (getContext() , ReturnMoney.class);
                                                i.putExtra("Id" ,returnBookings.get(position).getId() );
                                                i.putExtra("RefundAmount" , returnBookings.get(position).getCost());
                                                i.putExtra("BackMoneyRejectedActivity", 0);
                                                context.startActivity(i);

                                                Toast.makeText(getContext() , response.body().getMessage() , Toast.LENGTH_SHORT).show();

                                             }
                                             else {
                                                context.startActivity(new Intent (getContext() , HomeActivity.class));
                                                Toast.makeText(getContext() , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                                             }

                                        }else
                                            Toast.makeText(getContext() , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onFailure(Call<deleteRequestModel> call, Throwable t) {
                                        Log.d("API Failure delete request", "Error: " + t.getMessage());
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                alertDialog.show();
            }
        });
        return convertView;
    }
}
