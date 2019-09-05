package com.profile.profileapp.database.pojo;

import android.app.Application;
import android.os.AsyncTask;

import com.profile.profileapp.database.dao.profileDAO;
import com.profile.profileapp.database.entity.profileModel;
import com.profile.profileapp.database.room.databaseRoom;

public class profilePOJO {

    private profileDAO mProfileDao;
    private profileModel mProfile;

    public profilePOJO(Application application) {
        databaseRoom db = databaseRoom.getDatabase(application);
        mProfileDao = db.foodsDao();
    }

    public profileModel getProfile(Integer id) {
        return mProfileDao.getProfile(id);
    }

    public void update(profileModel profile) {
        mProfileDao.update(profile);
    }

    public void insert(profileModel profile){
        new insertAsyncTask(mProfileDao).execute(profile);
    }

    private static class insertAsyncTask extends AsyncTask<profileModel, Void, Void> {

        private profileDAO mAsyncTaskDao;

        insertAsyncTask(profileDAO dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final profileModel... profiles) {
            mAsyncTaskDao.insert(profiles[0]);
            return null;
        }
    }
}
