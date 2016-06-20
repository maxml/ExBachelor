package com.maxml.bachelorhouse.activity.projects;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.maxml.bachelorhouse.R;
import com.maxml.bachelorhouse.adapter.ProjectsAdapter;
import com.maxml.bachelorhouse.db.ProjectsDao;
import com.maxml.bachelorhouse.entity.Project;
import com.maxml.bachelorhouse.util.BachelorConstants;

import java.util.List;

public class ProjectsActivity extends AppCompatActivity {

    private TextView noProjects;

    private ListView projectsView;
    private SwipeRefreshLayout mRefresh;
    private ProjectsAdapter mAdapter;

    private List<Project> projects;

    private ActionMode actionMode;
    private int position;
    private ActionMode.Callback callback = new ActionMode.Callback() {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_item_popup, menu);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    ProjectsDao dao = new ProjectsDao(ProjectsActivity.this);
                    dao.delete(projects.get(position));

                    updateAdapter();

                    mode.finish();
                    break;
            }
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noProjects = (TextView) findViewById(R.id.projects_no);
        projectsView = (ListView) findViewById(R.id.projects_list);

        projects = new ProjectsDao(this).getAllProjects();

        mAdapter = new ProjectsAdapter(this, projects);
        projectsView.setAdapter(mAdapter);

        projectsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                getFragmentManager().beginTransaction().replace(R.id.rooms_fragment_container,
//                        MachinesFragment.newInstance(position)).commit();
                Intent intent = new Intent(ProjectsActivity.this, DetailsActivity.class);
                intent.putExtra(BachelorConstants.PROJECT_ID, projects.get(position).getId());
                startActivity(intent);

                ProjectsActivity.this.finish();
            }
        });
        projectsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "sdfvsd", Toast.LENGTH_SHORT).show();
                ProjectsActivity.this.position = position;
                if (actionMode == null)
                    actionMode = startSupportActionMode(callback);
                else
                    actionMode.finish();
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.projects_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                getFragmentManager().beginTransaction().replace(R.id.rooms_fragment_container,
//                        new AddRoomFragment()).commit();
                startActivity(new Intent(ProjectsActivity.this, AddProjectActivity.class));
            }
        });

        mRefresh = (SwipeRefreshLayout) findViewById(R.id.projects_refresh);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateAdapter();
                mRefresh.setRefreshing(false);
            }
        });

        updateAdapter();
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

    private void updateAdapter() {
        projects.clear();
        projects.addAll(new ProjectsDao(this).getAllProjects());

        mAdapter.notifyDataSetChanged();

        if (projects.isEmpty()) {
            noProjects.setVisibility(View.VISIBLE);
        } else {
            noProjects.setVisibility(View.GONE);
        }
    }

}
