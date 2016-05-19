package com.maxml.bachelorhouse.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.maxml.bachelorhouse.R;
import com.maxml.bachelorhouse.entity.Project;

import java.util.List;
import java.util.Random;

/**
 * Created by Maxml on 16.05.2016.
 */
public class ProjectsAdapter extends ArrayAdapter<Project> {

    private Context context;

    public ProjectsAdapter(Context context, List<Project> projects) {
        super(context, R.layout.item_room, projects);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Project project = getItem(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.item_project, null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Random random = new Random();
        holder.color.setBackgroundColor(Color.argb(255, random.nextInt(256), random.nextInt(256),
                random.nextInt(256)));

        holder.name.setText(project.getName());
        holder.status.setText(project.isStatus() ? "Enable" : "Disable");
        holder.pay.setText("$" + project.getPay());
        holder.date.setText(project.getDate());

        return convertView;
    }

    private class ViewHolder {

        private TextView name, status, pay, date;
        private View color;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.item_name);
            status = (TextView) view.findViewById(R.id.item_status);
            pay = (TextView) view.findViewById(R.id.item_pay);
            date = (TextView) view.findViewById(R.id.item_date);

            color = (View) view.findViewById(R.id.item_color);
        }

    }
}