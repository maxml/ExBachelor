package com.maxml.bachelorhouse.fragment.rooms;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.maxml.bachelorhouse.adapter.MachinesAdapter;
import com.maxml.bachelorhouse.db.MachinesDao;
import com.maxml.bachelorhouse.db.RoomsDao;
import com.maxml.bachelorhouse.entity.Machine;
import com.maxml.bachelorhouse.entity.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MachinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MachinesFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PHOTO_POSITION = "photoName-position";

    private TextView noMachines;

    private ListView machinesView;
    private SwipeRefreshLayout refreshLayout;
    private MachinesAdapter mAdapter;

    private List<Machine> machines;

    private ActionMode actionMode;
    private int position;

    static Room room;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PhotoDetailsFragment.
     */
    public static MachinesFragment newInstance(int position) {
        MachinesFragment fragment = new MachinesFragment();
        Bundle args = new Bundle();
        args.putInt(PHOTO_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public MachinesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int position = getArguments().getInt(PHOTO_POSITION);
            room = new RoomsDao(getActivity()).getAllRooms().get(position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        machines = new ArrayList<>();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_machines_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Machines");

        noMachines = (TextView) view.findViewById(R.id.machine_no);
        machinesView = (ListView) view.findViewById(R.id.machine_list);

        machines = new MachinesDao(getActivity()).getAllMachines();

        mAdapter = new MachinesAdapter(getActivity(), machines);
        machinesView.setAdapter(mAdapter);

//        machinesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                getFragmentManager().beginTransaction().replace(R.id.rooms_fragment_container,
//                        MachinesFragment.newInstance(position)).commit();
//
//            }
//        });
        machinesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "sdfvsd", Toast.LENGTH_SHORT).show();
                MachinesFragment.this.position = position;
                if (actionMode == null)
                    actionMode = getActivity().startActionMode(callback);
                else
                    actionMode.finish();
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.machine_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.rooms_fragment_container,
                        new AddMachineFragment()).commit();
            }
        });

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.machine_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateAdapter();
                refreshLayout.setRefreshing(false);
            }
        });

        updateAdapter();

    }

    private void updateAdapter() {
        machines.clear();
        machines.addAll(new MachinesDao(getActivity()).getAllMachines());

        mAdapter.notifyDataSetChanged();

        if (machines.isEmpty()) {
            noMachines.setVisibility(View.VISIBLE);
        } else {
            noMachines.setVisibility(View.GONE);
        }
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
                    MachinesDao dao = new MachinesDao(getActivity());
                    dao.delete(machines.get(position));

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


}
