package com.epam.themes.backend.entities;

import android.support.annotation.DrawableRes;

public class Student {

    private Long id;
    @DrawableRes
    private int avatarId;
    private String name;
    private int hwCount;

    public Student(long id, int avatarId, String name, int hwCount) {
        this.id = id;
        this.avatarId = avatarId;
        this.name = name;
        this.hwCount = hwCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(@DrawableRes int avatarId) {
        this.avatarId = avatarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHwCount() {
        return hwCount;
    }

    public void setHwCount(int hwCount) {
        this.hwCount = hwCount;
    }
}
