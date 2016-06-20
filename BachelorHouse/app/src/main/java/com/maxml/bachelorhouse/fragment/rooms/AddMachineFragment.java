package com.maxml.bachelorhouse.fragment.rooms;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.maxml.bachelorhouse.R;
import com.maxml.bachelorhouse.db.MachinesDao;
import com.maxml.bachelorhouse.entity.Machine;
import com.maxml.bachelorhouse.util.BachelorConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMachineFragment extends Fragment {

    private EditText mName, mTemperature, mWet;
    private Switch mEnabled;

    private View mAddMachineView;
    private ProgressBar mProgressBar;

    private AddMachineTask addMachineTask;

    public AddMachineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_machine, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mName = (EditText) view.findViewById(R.id.machine_add_name);
        mTemperature = (EditText) view.findViewById(R.id.machine_add_temp);
        mWet = (EditText) view.findViewById(R.id.machine_add_wet);

        mEnabled = (Switch) view.findViewById(R.id.machine_add_switch);
        mAddMachineView = view.findViewById(R.id.machine_add_form);
        mProgressBar = (ProgressBar) view.findViewById(R.id.machine_add_progress);

        view.findViewById(R.id.machine_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptAddMachine();
            }
        });
    }

    private void attemptAddMachine() {
        if (addMachineTask != null) {
            return;
        }

        // Reset errors.
        mName.setError(null);
        mTemperature.setError(null);
        mWet.setError(null);

        // Store values at the time of the login attempt.
        String name = mName.getText().toString();

        int temp = -1;
        if (!"".equals(mTemperature.getText().toString()))
            temp = Integer.valueOf(mTemperature.getText().toString());

        int wet = -1;
        if (!"".equals(mWet.getText().toString()))
            wet = Integer.valueOf(mWet.getText().toString());

        boolean isEnabled = mEnabled.isEnabled();

        // Check for a valid mName.
        if (TextUtils.isEmpty(name)) {
            mName.setError(getString(R.string.error_field_required));
            mName.requestFocus();
            return;
        }

        if (!isNameValid(name)) {
            mName.setError(getString(R.string.error_invalid_name));
            mName.requestFocus();
            return;
        }

        // Show a progress spinner, and kick off a background Task to
        // perform the machine login attempt.
        showProgress(true);

        addMachineTask = new AddMachineTask(new Machine(name, temp, wet, MachinesFragment.room));
        addMachineTask.execute();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mAddMachineView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAddMachineView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAddMachineView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mAddMachineView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isNameValid(String password) {
        return password.length() > 4;
    }

    /**
     * Represents an asynchronous login/registration Task used to authenticate
     * the machine.
     */
    private class AddMachineTask extends AsyncTask<Void, Void, Boolean> {

        private final Machine machine;

        AddMachineTask(Machine machine) {
            this.machine = machine;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            new MachinesDao(getActivity()).save(machine);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            addMachineTask = null;
            showProgress(false);

            if (success) {
                Toast.makeText(getActivity(), "You add needed machine!", Toast.LENGTH_SHORT).show();

                getFragmentManager().beginTransaction().replace(R.id.rooms_fragment_container,
                        new MachinesFragment(), BachelorConstants.ROOMS_LIST_FRAGMENT_TAG).commit();

            }
        }

        @Override
        protected void onCancelled() {
            addMachineTask = null;
            showProgress(false);
        }
    }


}
