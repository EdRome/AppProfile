package com.profile.profileapp.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "profile")
public class profileModel {

    public profileModel(@NonNull String username, @NonNull String gender, @NonNull Date birthday, @NonNull byte[] photo) {
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
        this.photo = photo;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer profileId;

    @NonNull
    private String username;

    @NonNull
    private String gender;

    @NonNull
    private Date birthday;

    @NonNull
    private byte[] photo;

    @NonNull
    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(@NonNull Integer profileId) {
        this.profileId = profileId;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getGender() {
        return gender;
    }

    public void setGender(@NonNull String gender) {
        this.gender = gender;
    }

    @NonNull
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(@NonNull Date birthday) {
        this.birthday = birthday;
    }

    @NonNull
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(@NonNull byte[] photo) {
        this.photo = photo;
    }
}
