package com.aquafadas.tag_the_bus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aquafadas.tag_the_bus.R;
import com.aquafadas.tag_the_bus.entities.Station;


import java.util.List;

/**
 * Created by Yahya on 27/09/2016.
 */
public class StationAdapter extends ArrayAdapter<Station> {
    Context context;
    int layoutResourceId;
    List<Station> stationList = null;


    public StationAdapter(Context context, int layoutResourceId,
                          List<Station> stations) {
        super(context, layoutResourceId, stations);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.stationList = stations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        StationHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new StationHolder();

            Station station = stationList.get(position);
            holder.streetHolder = (TextView) row.findViewById(R.id.stationName);
            holder.streetHolder.setText(station.getStreet());
            holder.busesHolder = (TextView) row.findViewById(R.id.buses);
            holder.busesHolder.setText(station.getBuses());

            row.setTag(holder);
        }


        return row;
    }

    public class  StationHolder {

        TextView streetHolder;
        TextView busesHolder;

    }
}
