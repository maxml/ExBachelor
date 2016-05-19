package com.maxml.bachelorhouse.db;

import android.content.Context;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.maxml.bachelorhouse.entity.User;
import com.maxml.bachelorhouse.util.BachelorConstants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxml on 13.05.2016.
 */
public class UserDao {

    private OrmLiteHelper helper;

    public UserDao(Context context) {
        this.helper = new OrmLiteHelper(context);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            users = helper.getUserDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean login(String email, String password) {
        try {
            QueryBuilder<User, Integer> queryBuilder = helper.getUserDao().queryBuilder();
            queryBuilder.where().eq(BachelorConstants.USER_EMAIL, email);
            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            List<User> users = helper.getUserDao().query(preparedQuery);

            for (User u : users) {
                if (u.getPassword().equals(password))
                    return true;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void register(final User user) {
        try {
            helper.getUserDao().create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(final User user) {
        try {
            helper.getUserDao().update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(User user) {
        try {
            helper.getUserDao().delete(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
