package com.aquafadas.tag_the_bus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aquafadas.tag_the_bus.R;
import com.aquafadas.tag_the_bus.entities.Picture;
import com.aquafadas.tag_the_bus.entities.Station;
import com.aquafadas.tag_the_bus.utils.ImageLoader;

import java.util.List;

/**
 * Created by Yahya on 27/09/2016.
 */
public class PictureAdapter extends ArrayAdapter<Picture> {
    Context context;
    int layoutResourceId;
    List<Picture> pictureList = null;
    public ImageLoader imageLoader;


    public PictureAdapter(Context context, int layoutResourceId,
                          List<Picture> pictures) {
        super(context, layoutResourceId, pictures);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.pictureList =pictures;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PictureHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new PictureHolder();
            Picture picture = pictureList.get(position);
            holder.titleHolder = (TextView) row.findViewById(R.id.title_img);
            holder.titleHolder.setText(picture.getTitle());
            holder.dateHolder = (TextView) row.findViewById(R.id.datePic);
            holder.dateHolder.setText(picture.getDate());
holder.pictureHolder=(ImageView)row.findViewById(R.id.img_station);
            imageLoader.DisplayImage(picture.getImage(), holder.pictureHolder);

            row.setTag(holder);
        }


        return row;
    }

    public class  PictureHolder {

        TextView titleHolder;
        TextView dateHolder;
        ImageView pictureHolder;

    }
}
