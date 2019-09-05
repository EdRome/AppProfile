package com.profile.profileapp.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.profile.profileapp.database.entity.profileModel;

@Dao
public interface profileDAO {

    @Insert
    void insert(profileModel profile);

    @Update
    void update(profileModel profile);

    @Query("SELECT * FROM profile WHERE profileId = :id")
    profileModel getProfile(Integer id);

}
