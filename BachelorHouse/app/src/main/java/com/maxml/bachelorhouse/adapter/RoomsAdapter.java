package com.maxml.bachelorhouse.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.maxml.bachelorhouse.R;
import com.maxml.bachelorhouse.entity.Room;

import java.util.List;
import java.util.Random;

/**
 * Created by Maxml on 16.05.2016.
 */
public class RoomsAdapter extends ArrayAdapter<Room> {

    private Context context;
    private List<Room> rooms;

    public RoomsAdapter(Context context, List<Room> rooms) {
        super(context, R.layout.item_room, rooms);

        this.rooms = rooms;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Room room = getItem(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.item_room, null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Random random = new Random();
        holder.color.setBackgroundColor(Color.argb(255, random.nextInt(256), random.nextInt(256),
                random.nextInt(256)));

        holder.name.setText(room.getName());
        if (room.getTemperature() != -1)
            holder.temperature.setText(holder.temperature.getText().toString().substring(0, 13) + room.getTemperature());
        else {
            holder.temperature.setVisibility(View.GONE);
        }
        if (room.getWet() != -1)
            holder.wet.setText(holder.wet.getText().toString().substring(0, 5) + room.getWet());
        else {
            holder.wet.setVisibility(View.GONE);
        }
        holder.enabled.setChecked(room.isEnabled());

        return convertView;
    }

    class ViewHolder {

        private TextView name, temperature, wet;
        private Switch enabled;
        private View color;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.item_name);
            temperature = (TextView) view.findViewById(R.id.item_temperature);
            wet = (TextView) view.findViewById(R.id.item_wet);
            enabled = (Switch) view.findViewById(R.id.item_switch);
            color = (View) view.findViewById(R.id.item_color);
        }

    }
}