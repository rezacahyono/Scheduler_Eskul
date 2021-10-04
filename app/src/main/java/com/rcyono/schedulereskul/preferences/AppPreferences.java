package com.rcyono.schedulereskul.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.rcyono.schedulereskul.model.user.User;

public class AppPreferences {
    private final static String PREFS_NAME = "prefs_app";
    private final static String ID_LOGIN = "id_login";
    private final static String NAME_LOGIN = "name_login";
    private final static String EMAIL_LOGIN = "email_login";
    private final static String AVATAR_LOGIN = "avatar_login";

    private final SharedPreferences preferences;

    public AppPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public User getUser() {
        User user = new User();
        user.setId(preferences.getString(ID_LOGIN,""));
        user.setUsername(preferences.getString(NAME_LOGIN, ""));
        user.setEmail(preferences.getString(EMAIL_LOGIN,""));
        user.setImagePath(preferences.getString(AVATAR_LOGIN, ""));
        return user;
    }

    public void setUser(User user) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ID_LOGIN, user.getId());
        editor.putString(NAME_LOGIN, user.getUsername());
        editor.putString(EMAIL_LOGIN, user.getEmail());
        editor.putString(AVATAR_LOGIN, user.getImagePath());
        editor.apply();
    }
}
