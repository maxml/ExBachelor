package com.maxml.bachelorhouse.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.maxml.bachelorhouse.R;
import com.maxml.bachelorhouse.fragment.rooms.RoomsFragment;
import com.maxml.bachelorhouse.fragment.rooms.StatisticsFragment;
import com.maxml.bachelorhouse.util.BachelorConstants;

public class RoomsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Rooms");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.rooms_fragment_container, new RoomsFragment(),
                BachelorConstants.ROOMS_LIST_FRAGMENT_TAG).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Project")
                    .setMessage("Are you sure you want to close this project?")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
        // Handle the camera action
        if (id == R.id.nav_gallery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.rooms_fragment_container,
                    new RoomsFragment(), BachelorConstants.ROOMS_LIST_FRAGMENT_TAG).commit();
//        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            getSupportFragmentManager().beginTransaction().replace(R.id.rooms_fragment_container,
                    new StatisticsFragment(), BachelorConstants.ROOMS_LIST_FRAGMENT_TAG).commit();

//        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.fromParts(
                    "mailto:", "abc@gmail.com", null));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help : your subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Your comment");
            startActivity(Intent.createChooser(emailIntent, "Send email via:"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
