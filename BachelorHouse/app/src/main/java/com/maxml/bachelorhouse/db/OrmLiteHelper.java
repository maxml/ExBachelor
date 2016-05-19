package com.maxml.bachelorhouse.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.maxml.bachelorhouse.entity.Machine;
import com.maxml.bachelorhouse.entity.Project;
import com.maxml.bachelorhouse.entity.Room;
import com.maxml.bachelorhouse.entity.Task;
import com.maxml.bachelorhouse.entity.User;

import java.sql.SQLException;

/**
 * Created by Maxml on 13.05.2016.
 */
public class OrmLiteHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "bachelor4.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 4;

    // the DAO object we use to access the SimpleData table
    private Dao<User, Integer> userDao = null;

    private Dao<Room, Integer> roomDao = null;
    private Dao<Machine, Integer> machineDao = null;

    private Dao<Project, Integer> projectsDao = null;
    private Dao<Task, Integer> tasksDao = null;

    private RuntimeExceptionDao<User, Integer> simpleRuntimeDao = null;

    public OrmLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(OrmLiteHelper.class.getName(), "onCreate");
            TableUtils.createTableIfNotExists(connectionSource, User.class);

            TableUtils.createTableIfNotExists(connectionSource, Machine.class);
            TableUtils.createTableIfNotExists(connectionSource, Room.class);

            TableUtils.createTableIfNotExists(connectionSource, Task.class);
            TableUtils.createTableIfNotExists(connectionSource, Project.class);
        } catch (SQLException e) {
            Log.e(OrmLiteHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
//        RuntimeExceptionDao<User, Integer> dao = getSimpleDataDao();
//        long millis = System.currentTimeMillis();
        // create some entries in the onCreate
//        User simple = new SimpleData(millis);
//        dao.create(simple);
//        simple = new SimpleData(millis + 1);
//        dao.create(simple);
//        Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(OrmLiteHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, User.class, true);

            TableUtils.dropTable(connectionSource, Machine.class, true);
            TableUtils.dropTable(connectionSource, Room.class, true);

            TableUtils.dropTable(connectionSource, Task.class, true);
            TableUtils.dropTable(connectionSource, Project.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(OrmLiteHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public Dao<Room, Integer> getRoomDao() throws SQLException {
        if (roomDao == null) {
            roomDao = getDao(Room.class);
        }
        return roomDao;
    }

    public Dao<Machine, Integer> getMachineDao() throws SQLException {
        if (machineDao == null) {
            machineDao = getDao(Machine.class);
        }
        return machineDao;
    }

    public Dao<Project, Integer> getProjectDao() throws SQLException {
        if (projectsDao == null) {
            projectsDao = getDao(Project.class);
        }
        return projectsDao;
    }

    public Dao<Task, Integer> getTasksDao() throws SQLException {
        if (tasksDao == null) {
            tasksDao = getDao(Task.class);
        }
        return tasksDao;
    }
    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
//    private RuntimeExceptionDao<User, Integer> getSimpleDataDao() {
//        if (simpleRuntimeDao == null) {
//            simpleRuntimeDao = getRuntimeExceptionDao(User.class);
//        }
//        return simpleRuntimeDao;
//    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        userDao = null;
        simpleRuntimeDao = null;
    }
}