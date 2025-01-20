package com.example.assisthub.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.assisthub.R;
import com.example.assisthub.model.volunteer;
import java.util.List;
public class volunteerAdapter extends ArrayAdapter<volunteer> {
    private List<volunteer> volunteerList;
    private Context context;

    public volunteerAdapter(Context context, List<volunteer> volunteerList) {
        super(context , R.layout.custom_volunteer_fragment, volunteerList);

        this.context = context;
        this.volunteerList = volunteerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_volunteer_fragment,parent,false);

        // create mirror object from custom layout
        ImageView ImageVolunteer   = convertView.findViewById(R.id.ImageVolunteer);
        TextView NamePlatform      = convertView.findViewById(R.id.NamePlatform);
        TextView infoAboutPlatform = convertView.findViewById(R.id.infoAboutPlatform);
        Button volunteerbutton    = convertView.findViewById(R.id.volunteerbutton);
        CardView cardVolunteer = convertView.findViewById(R.id.cardVolunteer);


        if(volunteerList.get(position).getLink() != null){
            volunteerbutton.setVisibility(View.VISIBLE);
            NamePlatform.setText(volunteerList.get(position).getName());
            infoAboutPlatform.setText(volunteerList.get(position).getNote());
            Glide.with(getContext()).load("http://10.0.2.2/project/AssistHub/img/"
                            +volunteerList.get(position).getImage())
                    .apply(new RequestOptions().override(300,200))
                    .error( R.drawable.error).into(ImageVolunteer);

        }else
            cardVolunteer.setBackgroundResource(R.drawable.wrong_screen);

        volunteerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(volunteerList.get(position).getLink()));
               context.startActivity(browserIntent);
            }
        });
        return convertView;
    }
}
