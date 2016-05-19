package com.maxml.bachelorhouse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.maxml.bachelorhouse.fragment.login.LoginFragment;
import com.maxml.bachelorhouse.util.BachelorConstants;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getFragmentManager().findFragmentByTag(BachelorConstants.LOGIN_FRAGMENT_TAG) == null)
            getFragmentManager().beginTransaction().
                    add(R.id.login_fragment_container, new LoginFragment(), BachelorConstants.LOGIN_FRAGMENT_TAG).commit();
    }

}

