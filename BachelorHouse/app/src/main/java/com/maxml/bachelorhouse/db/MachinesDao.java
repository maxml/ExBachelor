package com.maxml.bachelorhouse.db;

import android.content.Context;

import com.maxml.bachelorhouse.entity.Machine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxml on 16.05.2016.
 */
public class MachinesDao {

    private OrmLiteHelper helper;

    public MachinesDao(Context context) {
        this.helper = new OrmLiteHelper(context);
    }

    public List<Machine> getAllMachines() {
        List<Machine> machines = new ArrayList<>();
        try {
            machines = helper.getMachineDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return machines;
    }

    public void save(final Machine machine) {
        try {
            helper.getMachineDao().create(machine);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(final Machine machine) {
        try {
            helper.getMachineDao().update(machine);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Machine machine) {
        try {
            helper.getMachineDao().delete(machine);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
