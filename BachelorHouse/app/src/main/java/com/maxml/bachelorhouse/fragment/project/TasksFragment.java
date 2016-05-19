package com.maxml.bachelorhouse.fragment.project;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.maxml.bachelorhouse.R;
import com.maxml.bachelorhouse.activity.projects.AddTaskActivity;
import com.maxml.bachelorhouse.adapter.TasksAdapter;
import com.maxml.bachelorhouse.db.TasksDao;
import com.maxml.bachelorhouse.entity.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {

    private TextView noTasks;

    private ListView tasksView;
    private SwipeRefreshLayout refreshLayout;
    private TasksAdapter mAdapter;

    private List<Task> tasks;

    private ActionMode actionMode;
    private int position;

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tasks = new ArrayList<>();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noTasks = (TextView) view.findViewById(R.id.tasks_no);
        tasksView = (ListView) view.findViewById(R.id.tasks_list);

        tasks = new TasksDao(getActivity()).getAllTasks();

        mAdapter = new TasksAdapter(getActivity(), tasks);
        tasksView.setAdapter(mAdapter);

        tasksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                getFragmentManager().beginTransaction().replace(R.id.tasks_fragment_container,
//                        MachinesFragment.newInstance(position)).commit();

            }
        });
        tasksView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "sdfvsd", Toast.LENGTH_SHORT).show();
                TasksFragment.this.position = position;
                if (actionMode == null)
                    actionMode = getActivity().startActionMode(callback);
                else
                    actionMode.finish();
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.tasks_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                getFragmentManager().beginTransaction().replace(R.id.tasks_fragment_container,
//                        new AddTaskFragment()).commit();
                startActivity(new Intent(getActivity(), AddTaskActivity.class));
            }
        });

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.tasks_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateAdapter();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateAdapter() {
        tasks.clear();
        tasks.addAll(new TasksDao(getActivity()).getAllTasks());

        mAdapter.notifyDataSetChanged();

        if (tasks.isEmpty()) {
            noTasks.setVisibility(View.VISIBLE);
        } else {
            noTasks.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        updateAdapter();
    }

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
                    TasksDao dao = new TasksDao(getActivity());
                    dao.delete(tasks.get(position));

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
//    private boolean isLandscapeMode() {
//        return getResources().getConfiguration().orientation == getResources().getConfiguration().ORIENTATION_LANDSCAPE;
//    }
//
//    private int getDisplayColumns() {
//        int columnCount = 1;
//        if (isLandscapeMode()) {
//            columnCount = 2;
//        }
//        return columnCount;
//    }
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        return false;
//    }
}
