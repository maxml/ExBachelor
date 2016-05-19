package com.maxml.bachelorhouse.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.maxml.bachelorhouse.R;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by Maxml on 13.05.2016.
 */
public class PermssionUtil {

    public static boolean mayRequestContacts(final Activity activity, View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (activity.checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (activity.shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(view, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            activity.requestPermissions(new String[]{READ_CONTACTS}, BachelorConstants.REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            activity.requestPermissions(new String[]{READ_CONTACTS}, BachelorConstants.REQUEST_READ_CONTACTS);
        }
        return false;
    }

}
