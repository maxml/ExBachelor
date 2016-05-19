package com.maxml.bachelorhouse.fragment.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.maxml.bachelorhouse.R;
import com.maxml.bachelorhouse.activity.projects.DetailsActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProjectDetailsFragment extends Fragment {

    private EditText mName, mDate, mPay, mText, mClientEmail;
    private Switch mEnabled;

    public ProjectDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mName = (EditText) view.findViewById(R.id.project_details_name);
        mDate = (EditText) view.findViewById(R.id.project_details_date);
        mPay = (EditText) view.findViewById(R.id.project_details_pay);
        mClientEmail = (EditText) view.findViewById(R.id.project_details_email);
        mText = (EditText) view.findViewById(R.id.project_details_text);

        mEnabled = (Switch) view.findViewById(R.id.project_details_switch);

        mName.setText(DetailsActivity.project.getName());
        mDate.setText(DetailsActivity.project.getDate());
        mPay.setText("" + DetailsActivity.project.getPay());
        mClientEmail.setText(DetailsActivity.project.getClientEmail());
        mText.setText(DetailsActivity.project.getText());
        mEnabled.setChecked(DetailsActivity.project.isStatus());
    }
}
