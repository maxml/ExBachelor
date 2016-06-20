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
import android.widget.Toast;

import com.maxml.bachelorhouse.R;
import com.maxml.bachelorhouse.db.TasksDao;
import com.maxml.bachelorhouse.entity.Task;

public class AddTaskActivity extends AppCompatActivity {

    private EditText mName, mDate;

    private View mAddTaskView;
    private ProgressBar mProgressBar;

    private AddTaskTask addTaskTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mName = (EditText) findViewById(R.id.task_add_name);
        mDate = (EditText) findViewById(R.id.task_add_date);

        mAddTaskView = findViewById(R.id.task_add_form);
        mProgressBar = (ProgressBar) findViewById(R.id.task_add_progress);

        findViewById(R.id.task_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptAddTask();
            }
        });
    }

    private void attemptAddTask() {
        if (addTaskTask != null) {
            return;
        }

        // Reset errors.
        mName.setError(null);
        mDate.setError(null);

        // Store values at the time of the login attempt.
        String name = mName.getText().toString();
        String date = mDate.getText().toString();

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

        // Show a progress spinner, and kick off a background Task to
        // perform the room login attempt.
        showProgress(true);

        addTaskTask = new AddTaskTask(new Task(name, date, DetailsActivity.project));
        addTaskTask.execute();
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

            mAddTaskView.setVisibility(show ? View.GONE : View.VISIBLE);
            mAddTaskView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAddTaskView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mAddTaskView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isNameValid(String password) {
        return password.length() > 4;
    }

    /**
     * Represents an asynchronous login/registration Task used to authenticate
     * the project.
     */
    private class AddTaskTask extends AsyncTask<Void, Void, Boolean> {

        private final Task task;

        AddTaskTask(Task task) {
            this.task = task;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            new TasksDao(AddTaskActivity.this).save(task);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            addTaskTask = null;
            showProgress(false);

            if (success) {
                Toast.makeText(AddTaskActivity.this, "You add task!", Toast.LENGTH_SHORT).show();
                finish();
//                getFragmentManager().beginTransaction().replace(R.id.rooms_fragment_container,
//                        new RoomsFragment(), BachelorConstants.ROOMS_LIST_FRAGMENT_TAG).commit();

            }
        }

        @Override
        protected void onCancelled() {
            addTaskTask = null;
            showProgress(false);
        }
    }

}
