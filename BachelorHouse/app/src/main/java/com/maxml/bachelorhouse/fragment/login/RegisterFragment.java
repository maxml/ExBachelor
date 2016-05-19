package com.maxml.bachelorhouse.fragment.login;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maxml.bachelorhouse.R;
import com.maxml.bachelorhouse.db.UserDao;
import com.maxml.bachelorhouse.entity.User;
import com.maxml.bachelorhouse.util.BachelorConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText mName, mEmail, mPassword;
    private View mRegisterForm;
    private ProgressBar mProgress;

    private UserRegisterTask registerTask = null;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mName = (EditText) view.findViewById(R.id.register_name);
        mEmail = (EditText) view.findViewById(R.id.register_email);
        mPassword = (EditText) view.findViewById(R.id.register_password);

        view.findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
            }
        });
        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        mProgress = (ProgressBar) view.findViewById(R.id.register_progress);
        mRegisterForm = view.findViewById(R.id.register_form);
    }

    private void attemptRegistration() {
        if (registerTask != null) {
            return;
        }

        // Reset errors.
        mName.setError(null);
        mEmail.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        // Check for a valid name.
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

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            mEmail.requestFocus();
            return;
        }

        if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            mEmail.requestFocus();
            return;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            mPassword.requestFocus();
            return;
        }

        if (!isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            mPassword.requestFocus();
            return;
        }

        // Show a progress spinner, and kick off a background Task to
        // perform the user login attempt.
        showProgress(true);

        registerTask = new UserRegisterTask(new User(name, email, password));
        registerTask.execute();
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

            mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isNameValid(String password) {
        return password.length() > 4;
    }

    /**
     * Represents an asynchronous login/registration Task used to authenticate
     * the user.
     */
    private class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final User user;

        UserRegisterTask(User user) {
            this.user = user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            new UserDao(getActivity()).register(user);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            registerTask = null;
            showProgress(false);

            if (success) {
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        "Success registration!", Snackbar.LENGTH_LONG).show();

                getFragmentManager().beginTransaction().
                        replace(R.id.login_fragment_container, new LoginFragment(), BachelorConstants.LOGIN_FRAGMENT_TAG).commit();

            }
        }

        @Override
        protected void onCancelled() {
            registerTask = null;
            showProgress(false);
        }
    }

}
