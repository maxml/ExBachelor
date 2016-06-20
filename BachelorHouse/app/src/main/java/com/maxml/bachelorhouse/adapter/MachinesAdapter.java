package com.maxml.bachelorhouse.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.maxml.bachelorhouse.R;
import com.maxml.bachelorhouse.entity.Machine;

import java.util.List;

/**
 * Created by Maxml on 16.05.2016.
 */
public class MachinesAdapter extends ArrayAdapter<Machine> {

    private Context context;
    private List<Machine> machines;

    public MachinesAdapter(Context context, List<Machine> machines) {
        super(context, R.layout.item_machine, machines);

        this.machines = machines;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Machine machine = getItem(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.item_machine, null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(machine.getName());
        if (machine.getTemperature() != -1)
            holder.temperature.setText(holder.temperature.getText().toString().substring(0, 13) + machine.getTemperature());
        else {
            holder.temperature.setVisibility(View.GONE);
        }
        if (machine.getWet() != -1)
            holder.wet.setText(holder.wet.getText().toString().substring(0, 5) + machine.getWet());
        else {
            holder.wet.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {

        private TextView name, temperature, wet;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.item_name);
            temperature = (TextView) view.findViewById(R.id.item_temperature);
            wet = (TextView) view.findViewById(R.id.item_wet);
        }

    }
}