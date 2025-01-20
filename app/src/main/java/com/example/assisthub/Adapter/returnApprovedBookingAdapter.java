package com.example.assisthub.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.example.assisthub.R;
import com.example.assisthub.model.ApprovedBookingModel;
import java.util.List;

public class returnApprovedBookingAdapter  extends ArrayAdapter<ApprovedBookingModel> {
    private List<ApprovedBookingModel> approvedBookingList;
    private Context context;

    public returnApprovedBookingAdapter(Context context, List <ApprovedBookingModel> approvedBookingList) {
        super(context , R.layout.return_approved_bookings_custom_layout, approvedBookingList);

        this.context = context;
        this.approvedBookingList = approvedBookingList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.return_approved_bookings_custom_layout,parent,false);

        //mirror object
        TextView name_hospitalCardApproved = convertView.findViewById(R.id.name_hospitalCardApproved);
        TextView SectionApprovedCard = convertView.findViewById(R.id.SectionApprovedCard);
        TextView DateBooking = convertView.findViewById(R.id.DateBooking);
        TextView DoctorOrBedApprovedCard = convertView.findViewById(R.id.DoctorOrBedApprovedCard);
        TextView periodApprovedCard = convertView.findViewById(R.id.periodApprovedCard);
        TextView Appointment_timeForDoctor = convertView.findViewById(R.id.Appointment_timeApproved);
        CardView cardApprovedBooking = convertView.findViewById(R.id.cardApprovedBooking);

        if(approvedBookingList.get(position).getHospital() != null ){
            cardApprovedBooking.setVisibility(View.VISIBLE);
            name_hospitalCardApproved.setText(approvedBookingList.get(position).getHospital());
            SectionApprovedCard.setText(approvedBookingList.get(position).getSection());
            DateBooking.setText(" Your reservation date : "+approvedBookingList.get(position).getDateBooking());
        }
        else{
            cardApprovedBooking.setVisibility(View.VISIBLE);
            cardApprovedBooking.setBackgroundResource(R.drawable.no_approved);
        }
        if(approvedBookingList.get(position).getDoctor() != null){
            //doctor data
            Appointment_timeForDoctor.setVisibility(View.VISIBLE);
            periodApprovedCard.setVisibility(View.GONE);
            DoctorOrBedApprovedCard.setVisibility(View.VISIBLE);
            String time []= approvedBookingList.get(position).getAppointment_time().split(":");
            Appointment_timeForDoctor.setText("Appointment time : "+time[0]+":"+time[1]);
            DoctorOrBedApprovedCard.setText("You have an appointment with : "+approvedBookingList.get(position).getDoctor());
        }
        else if (approvedBookingList.get(position).getBed() != null){
            //bed data
            Appointment_timeForDoctor.setVisibility(View.GONE);
            periodApprovedCard.setVisibility(View.VISIBLE);
            DoctorOrBedApprovedCard.setVisibility(View.VISIBLE);

            periodApprovedCard.setText("Your reservation period : "+approvedBookingList.get(position).getPeriod());
            DoctorOrBedApprovedCard.setText("Your reservation : "+approvedBookingList.get(position).getBed());
        }

        return convertView;
    }
}
