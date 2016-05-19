package com.maxml.bachelorhouse.db;

import android.content.Context;

import com.maxml.bachelorhouse.entity.Project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxml on 16.05.2016.
 */
public class ProjectsDao {

    private OrmLiteHelper helper;

    public ProjectsDao(Context context) {
        this.helper = new OrmLiteHelper(context);
    }

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        try {
            projects = helper.getProjectDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public void save(final Project project) {
        try {
            helper.getProjectDao().create(project);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Project get(final int projectId) {
        try {
            if (projectId == -1) {
                return null;
            }
            return helper.getProjectDao().queryForId(projectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(final Project project) {
        try {
            helper.getProjectDao().update(project);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Project project) {
        try {
            helper.getProjectDao().delete(project);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
