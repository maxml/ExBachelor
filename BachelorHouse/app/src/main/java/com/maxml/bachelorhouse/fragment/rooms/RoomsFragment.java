package com.maxml.bachelorhouse.fragment.rooms;


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
import com.maxml.bachelorhouse.adapter.RoomsAdapter;
import com.maxml.bachelorhouse.db.RoomsDao;
import com.maxml.bachelorhouse.entity.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomsFragment extends Fragment {

    private TextView noRooms;

    private ListView roomsView;
    private SwipeRefreshLayout refreshLayout;
    private RoomsAdapter mAdapter;

    private List<Room> rooms;

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
                    RoomsDao dao = new RoomsDao(getActivity());
                    dao.delete(rooms.get(position));

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

    public RoomsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rooms = new ArrayList<>();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rooms_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noRooms = (TextView) view.findViewById(R.id.rooms_no);
        roomsView = (ListView) view.findViewById(R.id.rooms_list);

        rooms = new RoomsDao(getActivity()).getAllRooms();

        mAdapter = new RoomsAdapter(getActivity(), rooms);
        roomsView.setAdapter(mAdapter);

        roomsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager().beginTransaction().replace(R.id.rooms_fragment_container,
                        MachinesFragment.newInstance(position)).commit();

            }
        });
        roomsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "sdfvsd", Toast.LENGTH_SHORT).show();
                RoomsFragment.this.position = position;
                if (actionMode == null)
                    actionMode = getActivity().startActionMode(callback);
                else
                    actionMode.finish();
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.rooms_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                getFragmentManager().beginTransaction().replace(R.id.rooms_fragment_container,
                        new AddRoomFragment()).commit();
            }
        });

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.rooms_refresh);
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
        rooms.clear();
        rooms.addAll(new RoomsDao(getActivity()).getAllRooms());

        mAdapter.notifyDataSetChanged();

        if (rooms.isEmpty()) {
            noRooms.setVisibility(View.VISIBLE);
        } else {
            noRooms.setVisibility(View.GONE);
        }
    }
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
