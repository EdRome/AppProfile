package com.profile.profileapp.database.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.profile.profileapp.database.dao.profileDAO;
import com.profile.profileapp.database.entity.profileModel;

@Database(entities = {profileModel.class}, version = 1, exportSchema = false)
public abstract class databaseRoom extends RoomDatabase {

    public abstract profileDAO foodsDao();

    public static volatile databaseRoom INSTANCE;

    public static databaseRoom getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (databaseRoom.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            databaseRoom.class,
                            "profile_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
