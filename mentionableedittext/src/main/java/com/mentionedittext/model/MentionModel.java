package com.mentionedittext.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author FarshidAbz
 * @version 1.0
 * @since 10/4/2016
 */
public class MentionModel {
    private String name;
    private String avatarUrl;

    MentionModel() {

    }

    public MentionModel(@NonNull String name, @Nullable String avatarUrl) {
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
