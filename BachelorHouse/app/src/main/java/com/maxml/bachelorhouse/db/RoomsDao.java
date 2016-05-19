package com.maxml.bachelorhouse.db;

import android.content.Context;

import com.maxml.bachelorhouse.entity.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxml on 16.05.2016.
 */
public class RoomsDao {

    private OrmLiteHelper helper;

    public RoomsDao(Context context) {
        this.helper = new OrmLiteHelper(context);
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        try {
            rooms = helper.getRoomDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public void save(final Room room) {
        try {
            helper.getRoomDao().create(room);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Room get(final int roomId) {
        try {
            return helper.getRoomDao().queryForId(roomId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(final Room room) {
        try {
            helper.getRoomDao().update(room);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Room room) {
        try {
            helper.getRoomDao().delete(room);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
