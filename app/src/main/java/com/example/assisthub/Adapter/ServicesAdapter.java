package com.example.assisthub.Adapter;

import static androidx.core.app.ActivityCompat.recreate;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.assisthub.Activity.confirmationActivity;
import com.example.assisthub.R;
import com.example.assisthub.model.ServiceModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.Calendar;
import java.util.List;

public class ServicesAdapter extends ArrayAdapter<ServiceModel> implements CalendarView.OnDateChangeListener, RadioGroup.OnCheckedChangeListener {

    private List<ServiceModel> ServiceModel;
    private  Context context;
    private static String   period = null , date = null;

    public ServicesAdapter(Context context, List<ServiceModel> ServiceModel) {
        super(context , R.layout.custom_service, ServiceModel);
        this.context = context;
        this.ServiceModel = ServiceModel;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_service,parent,false);


        // Get the current date
        Calendar calendar = Calendar.getInstance();

        // Set the minimum date to the start of the current month
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long startOfMonth = calendar.getTimeInMillis();

        // Set the maximum date to the end of the current month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long endOfMonth = calendar.getTimeInMillis();

        //mirror object
        TextView NameDoctorOrBed              = convertView.findViewById(R.id.NameDoctorOrBed);
        TextView DoctorType                   = convertView.findViewById(R.id.DoctorType);
        TextView TimeAvailableDoctor          = convertView.findViewById(R.id.TimeAvailableDoctor);
        TextView DayDoctor                    = convertView.findViewById(R.id.DayDoctor);
        ImageView ImageService                = convertView.findViewById(R.id.ImageService);
        MaterialButton reservationButton      = convertView.findViewById(R.id.reservationButton);
        LinearLayout DoctorInfoLayout         = convertView.findViewById(R.id.DoctorInfoLayout);
        LinearLayout bedInfoLayout            = convertView.findViewById(R.id.bedInfoLayout);
        CalendarView calendarView             = convertView.findViewById(R.id.calendarView);
        RadioGroup radioGroup                 = convertView.findViewById(R.id.radioGroup);

        //events
        calendarView.setOnDateChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        calendarView.setMinDate(startOfMonth);     // Apply the date range
        calendarView.setMaxDate(endOfMonth);       // Apply the date range


            //here set the data for doctor
            if(ServiceModel.get(position).getIdDoctor() == 0 && ServiceModel.get(position).getIdBed() == -1){

                DoctorInfoLayout .setVisibility(View.GONE);
                reservationButton.setVisibility(View.GONE);
                NameDoctorOrBed.setVisibility(View.GONE);
                bedInfoLayout .setVisibility(View.GONE);
                ImageService.setVisibility(View.VISIBLE);
                ImageService.setBackgroundResource(R.drawable.wrong_screen);
            }
            else if(ServiceModel.get(position).getIdDoctor() != 0){

                DoctorInfoLayout  .setVisibility(View.VISIBLE);
                bedInfoLayout     .setVisibility(View.GONE);
                reservationButton .setVisibility(View.VISIBLE);
                ImageService      .setVisibility(View.VISIBLE);
                NameDoctorOrBed   .setVisibility(View.VISIBLE);

                NameDoctorOrBed.setText (ServiceModel.get(position).getNameDoctor());
                DayDoctor.setText (ServiceModel.get(position).getDay());
                TimeAvailableDoctor.setText(ServiceModel.get(position).getTimeAvailable());
                DoctorType.setText(ServiceModel.get(position).getDoctorType());

                Intent i = new Intent(getContext(), confirmationActivity.class);
                i.putExtra("cost", ServiceModel.get(position).getCostDoctor());
                i.putExtra("idBed",0);
                i.putExtra("IdDoctor", ServiceModel.get(position).getIdDoctor());
                i.putExtra("idHopital", ServiceModel.get(position).getHospital_fk_id());
                i.putExtra("SectionInHospital", ServiceModel.get(position).getSections_fk_id());
                i.putExtra("dateForBed","-");
                i.putExtra("periodBed","-");
                i.putExtra("cost" ,  ServiceModel.get(position).getCostDoctor());

                //for img
                Glide.with(getContext()).load("http://10.0.2.2/project/AssistHub/img/"
                                +ServiceModel.get(position).getImageDoctor())
                        .apply(new RequestOptions().override(300,200))
                        .error( R.drawable.error).into(ImageService);

                reservationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //create alert dialog
                        AlertDialog alertDialog =  new MaterialAlertDialogBuilder(getContext()).
                                setTitle("Are you Sure ?")
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        i.putExtra("IdDoctor", ServiceModel.get(position).getIdDoctor());
                                        context.startActivity(i);

                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        refreshActivtiy();
                                    }
                                }).create();

                        alertDialog.show();

                    }
                });


            }
            else if(ServiceModel.get(position).getIsFull() == 1 ) {
                // this mean that full reservation
                DoctorInfoLayout.setVisibility(View.GONE);
                reservationButton.setVisibility(View.GONE);
                NameDoctorOrBed.setVisibility(View.GONE);
                bedInfoLayout.setVisibility(View.GONE);
                ImageService.setVisibility(View.VISIBLE);
                ImageService.setBackgroundResource(R.drawable.no_data_avaliable);
            }
            else{
                DoctorInfoLayout .setVisibility(View.GONE);
                bedInfoLayout    .setVisibility(View.VISIBLE);
                reservationButton.setVisibility(View.VISIBLE);
                NameDoctorOrBed.setVisibility(View.VISIBLE);
                NameDoctorOrBed.setText(ServiceModel.get(position).getNameBed());
                ImageService.setVisibility(View.VISIBLE);

                    Glide.with(getContext()).load("http://10.0.2.2/project/AssistHub/img/"
                                    +ServiceModel.get(position).getImageBed())
                            .apply(new RequestOptions().override(300,200))
                            .error( R.drawable.error).into(ImageService);
                    Intent i = new Intent(getContext(), confirmationActivity.class);
                    i.putExtra("cost",ServiceModel.get(position).getCostBed());
                    i.putExtra("idBed", ServiceModel.get(position).getIdBed());
                    i.putExtra("IdDoctor",0);
                    i.putExtra("idHopital", ServiceModel.get(position).getHospital_fk_id());
                    i.putExtra("SectionInHospital", ServiceModel.get(position).getSections_fk_id());


                    reservationButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(date == null || period == null)
                                Toast.makeText(context, "please choose date and time", Toast.LENGTH_SHORT).show();

                            else{


                                //create alert dialog
                                AlertDialog alertDialog =  new MaterialAlertDialogBuilder(getContext()).
                                        setTitle("Are you Sure ?")
                                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                i.putExtra("dateForBed",date);
                                                i.putExtra("periodBed",period);
                                                i.putExtra("cost" ,  ServiceModel.get(position).getCostBed());

                                                context.startActivity(i);

                                            }
                                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                refreshActivtiy();
                                            }
                                        }).create();

                                alertDialog.show();

                            }
                        }
                    });


          }


        return convertView;
    }


    public  void refreshActivtiy(){
        recreate((Activity) context);
    }


    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        date = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year); // dd/MM/yyyy format
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.morning)
            period = "morning";
        else if(checkedId == R.id.night)
            period = "night";
        else
            period = null ;
    }
}
