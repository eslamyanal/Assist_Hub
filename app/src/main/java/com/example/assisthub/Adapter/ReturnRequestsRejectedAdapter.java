package com.example.assisthub.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import com.example.assisthub.Activity.ReturnMoney;
import com.example.assisthub.R;
import com.example.assisthub.model.RequestsRejectedModel;
import java.util.List;

public class ReturnRequestsRejectedAdapter extends ArrayAdapter<RequestsRejectedModel> {

    private List<RequestsRejectedModel> RequestsRejectedList;
    private Context context;

    public ReturnRequestsRejectedAdapter(Context context, List <RequestsRejectedModel> RequestsRejectedList) {
        super(context , R.layout.return_rejected_bookings_custom_layout, RequestsRejectedList);
        this.context = context;
        this.RequestsRejectedList = RequestsRejectedList;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.return_rejected_bookings_custom_layout,parent,false);

        //mirror object
        TextView name_hospitalCardRejected = convertView.findViewById(R.id.name_hospitalCardRejected);
        TextView SectionCardRejected = convertView.findViewById(R.id.SectionRejectedCard);
        TextView DoctorOrBedCardRejected = convertView.findViewById(R.id.DoctorOrBedRejectedCard);
        TextView message = convertView.findViewById(R.id.message);
        Button RefundButton = convertView.findViewById(R.id.Refund);
        CardView RejectedCard = convertView.findViewById(R.id.RejectedCard);


        if(RequestsRejectedList.get(position).getId() != 0){

            name_hospitalCardRejected.setText(RequestsRejectedList.get(position).getHospital());
            SectionCardRejected.setText(RequestsRejectedList.get(position).getSection());
            message.setText("message : "+RequestsRejectedList.get(position).getMessage());
            RejectedCard.setVisibility(View.VISIBLE);
            RefundButton.setVisibility(View.VISIBLE);
            DoctorOrBedCardRejected.setVisibility(View.VISIBLE);
        }
        else{
            RejectedCard.setVisibility(View.VISIBLE);
            RefundButton.setVisibility(View.GONE);
            RefundButton.setMaxHeight(500);
            RejectedCard.setBackgroundResource(R.drawable.no_rejected);
        }

        if (RequestsRejectedList.get(position).IsReturnPayment() == 1){
            RefundButton.setEnabled(false);
            RefundButton.setTransitionAlpha(0.5f);
        }
        else
            RefundButton.setEnabled(true);

        if(RequestsRejectedList.get(position).getDoctor() != null)
            DoctorOrBedCardRejected.setText("You have an appointment with : "+RequestsRejectedList.get(position).getDoctor());
        else if (RequestsRejectedList.get(position).getBed() != null)
            DoctorOrBedCardRejected.setText("Your reservation : "+RequestsRejectedList.get(position).getBed());

        RefundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ReturnMoney.class);

                intent.putExtra("Id", RequestsRejectedList.get(position).getId());
                intent.putExtra("RefundAmount", RequestsRejectedList.get(position).getRefundAmount());
                intent.putExtra("BackMoneyRejectedActivity", 1);

                context.startActivity(intent);
            }
        });
        return  convertView;
    }

}
