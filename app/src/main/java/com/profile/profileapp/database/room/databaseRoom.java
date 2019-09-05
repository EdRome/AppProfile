package com.profile.profileapp.database.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.profile.profileapp.database.dao.profileDAO;
import com.profile.profileapp.database.entity.profileModel;
import com.profile.profileapp.utils.Converters;

@Database(entities = {profileModel.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
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
