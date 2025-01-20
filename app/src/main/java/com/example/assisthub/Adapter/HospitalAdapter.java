package com.example.assisthub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.assisthub.Activity.ServiceActivity;
import com.example.assisthub.R;
import com.example.assisthub.model.hospital_model;

import java.util.List;

public class HospitalAdapter extends ArrayAdapter<hospital_model> {

    private  List <hospital_model> hospitalList;
    private  Context context;

    public HospitalAdapter(Context context, List<hospital_model> hospitalList) {
        super(context , R.layout.hospital_custom_layout, hospitalList);

        this.context = context;
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hospital_custom_layout,parent,false);

        //mirror object from custom layout
        TextView name_hospital = convertView.findViewById(R.id.name_hospital);
        ImageView image_hospital = convertView.findViewById(R.id.image_hospital);
        CardView cardHospital = convertView.findViewById(R.id.cardHospital);
        LinearLayout LinearLayoutHospitalCard = convertView.findViewById(R.id.LinearLayoutHospitalCard);


        if(hospitalList.get(position).getName() == null){
            cardHospital.setVisibility(View.GONE);
            LinearLayoutHospitalCard.setBackgroundResource(R.drawable.wrong_screen);
        }
        else
        {
            cardHospital.setVisibility(View.VISIBLE);

            name_hospital.setText(hospitalList.get(position).getName());
            Glide.with(getContext()).load("http://10.0.2.2/project/AssistHub/img/"
                            +hospitalList.get(position).getImage())
                    .apply(new RequestOptions().override(300,200))
                    .error(R.drawable.error).into(image_hospital);

            cardHospital.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), ServiceActivity.class);
                    i.putExtra("IDHospital",hospitalList.get(position).getId());
                    i.putExtra("NameHospital",hospitalList.get(position).getName());
                    context.startActivity(i);
                }
            });
        }

        return convertView;
    }

}
