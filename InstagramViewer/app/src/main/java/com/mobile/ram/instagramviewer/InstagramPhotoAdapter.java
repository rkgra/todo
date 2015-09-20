package com.mobile.ram.instagramviewer;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;


/**
 * Created by rgauta01 on 9/19/15.
 */
public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {


    public InstagramPhotoAdapter(Context context, List<InstagramPhoto> objects) {
        super(context,android.R.layout.simple_list_item_1 , objects);
    }

    // use template to display each photo


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the data item for this postion

        InstagramPhoto photo=getItem(position);

        // check if we are using a recycled view ,if not we need to inflate

        if(convertView ==null){
            //create view

            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_photo,parent,false);

        }

        // Lookup the view for populating tha data.

        TextView tvCaption= (TextView) convertView.findViewById(R.id.tvCaption);

        ImageView ivPhoto= (ImageView) convertView.findViewById(R.id.ivPhoto);

        ImageView ivProfile= (ImageView) convertView.findViewById(R.id.ivProfile);

        TextView tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikesCount);

        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);

        TextView tvTimesCreated = (TextView) convertView.findViewById(R.id.tvTimesCreated);



        // Insert the model data into each of the view items

        tvCaption.setText(photo.caption);

        // Clear out the old image view

        ivPhoto.setImageResource(0);

        // insert the image using picasso

       Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
       // Picasso.with(getContext()).load(photo.imageUrl).fit().centerInside().into(ivPhoto);
        //Picasso.with(getContext()).load(photo.imageUrl).resize(150,0).into(ivPhoto);

      // Profile image

        ivProfile.setImageResource(0);
        Picasso.with(getContext()).load(photo.userProfileImageUrl).into(ivProfile);

        //Picasso.with(activity).load(mayorShipImageLink).transform(new CircleTransform()).into(Ima

        //

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Picasso.with(getContext())
                .load(photo.userProfileImageUrl)
                .fit()
                .transform(transformation)
                .into(ivProfile);

        // Photo likes count

        tvLikesCount.setText(photo.likesCount+ " likes");

        // User Name

        tvUserName.setText(photo.username);

        // Created time
        //tvTimesCreated.setText(photo.created_time);

       // final CharSequence relativeDateTimeString = DateUtils.getRelativeDateTimeString(Long.parseLong(photo.created_time), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

        final CharSequence relativeDateTimeString =  DateUtils.getRelativeTimeSpanString(Long.parseLong(photo.created_time)*1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS,DateUtils.FORMAT_ABBREV_TIME);

        tvTimesCreated.setText(getFormattedTime((String) relativeDateTimeString));

        return convertView;
    }

    private String getFormattedTime( String  relativeDateTimeString )
    {

       String formattedTime = null;


        if(relativeDateTimeString.contains("hour ago")){

            formattedTime= relativeDateTimeString.replaceAll("hour ago","h");

        }else if (relativeDateTimeString.contains("hours ago")){

            formattedTime= relativeDateTimeString.replaceAll("hours ago","h");
        }
        else if (relativeDateTimeString.contains("hours ago")){

            formattedTime= relativeDateTimeString.replaceAll("hours ago","h");
        }
        else if (relativeDateTimeString.contains("minute ago")){

            formattedTime= relativeDateTimeString.replaceAll("minute ago","m");
        }
        else if (relativeDateTimeString.contains("minutes ago")){

            formattedTime= relativeDateTimeString.replaceAll("minutes ago","m");
        }
        else if (relativeDateTimeString.contains("second ago")){

            formattedTime= relativeDateTimeString.replaceAll("second ago","s");
        }
        else if (relativeDateTimeString.contains("seconds ago")){

            formattedTime= relativeDateTimeString.replaceAll("seconds ago","s");
        }


        return formattedTime;


    }
}
