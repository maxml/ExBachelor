package com.maxml.bachelorhouse.activity.projects;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.maxml.bachelorhouse.R;
import com.maxml.bachelorhouse.db.ProjectsDao;
import com.maxml.bachelorhouse.entity.Project;

public class AddProjectActivity extends AppCompatActivity {

    private EditText mName, mDate, mPay, mText, mClientEmail;
    private Switch mEnabled;

    private View mAddProjectView;
    private ProgressBar mProgressBar;

    private AddProjectTask addProjectTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        mName = (EditText) findViewById(R.id.project_add_name);
        mDate = (EditText) findViewById(R.id.project_add_date);
        mPay = (EditText) findViewById(R.id.project_add_pay);
        mClientEmail = (EditText) findViewById(R.id.project_add_email);
        mText = (EditText) findViewById(R.id.project_add_text);

        mEnabled = (Switch) findViewById(R.id.project_add_switch);
        mAddProjectView = findViewById(R.id.project_add_form);
        mProgressBar = (ProgressBar) findViewById(R.id.project_add_progress);

        findViewById(R.id.project_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptAddProject();
            }
        });
    }

    private void attemptAddProject() {
        if (addProjectTask != null) {
            return;
        }

        // Reset errors.
        mName.setError(null);
        mDate.setError(null);
        mPay.setError(null);
        mClientEmail.setError(null);
        mText.setError(null);

        // Store values at the time of the login attempt.
        String name = mName.getText().toString();
        String date = mDate.getText().toString();
        String text = mText.getText().toString();
        String clientEmail = mClientEmail.getText().toString();
        double pay = Double.valueOf(mPay.getText().toString());
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

        if (TextUtils.isEmpty(date)) {
            mName.setError(getString(R.string.error_field_required));
            mName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(clientEmail)) {
            mName.setError(getString(R.string.error_field_required));
            mName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(text)) {
            mName.setError(getString(R.string.error_field_required));
            mName.requestFocus();
            return;
        }

        // Show a progress spinner, and kick off a background Task to
        // perform the room login attempt.
        showProgress(true);

        addProjectTask = new AddProjectTask(new Project(name, isEnabled, pay, date, clientEmail, text));
        addProjectTask.execute();
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

            mAddProjectView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAddProjectView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAddProjectView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mAddProjectView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isNameValid(String password) {
        return password.length() > 4;
    }

    /**
     * Represents an asynchronous login/registration Task used to authenticate
     * the project.
     */
    private class AddProjectTask extends AsyncTask<Void, Void, Boolean> {

        private final Project project;

        AddProjectTask(Project project) {
            this.project = project;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            new ProjectsDao(AddProjectActivity.this).save(project);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            addProjectTask = null;
            showProgress(false);

            if (success) {
                Toast.makeText(AddProjectActivity.this, "You add project!", Toast.LENGTH_SHORT).show();
                finish();
//                getFragmentManager().beginTransaction().replace(R.id.rooms_fragment_container,
//                        new RoomsFragment(), BachelorConstants.ROOMS_LIST_FRAGMENT_TAG).commit();

            }
        }

        @Override
        protected void onCancelled() {
            addProjectTask = null;
            showProgress(false);
        }
    }

}
