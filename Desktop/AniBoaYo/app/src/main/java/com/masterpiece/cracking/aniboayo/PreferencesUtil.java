package com.masterpiece.cracking.aniboayo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* PreferencesUtil Class
 * 파일 형태의 저장소 (Key, Value 형태)
 * 사용목적 : 외부클래스의 데이터를 이용하기 위해서
 */
@SuppressWarnings("static-access")
public class PreferencesUtil {

    public static final String PREFS_NAME = "Manga";
    public static final String FAVORITES = "Manga_Favorite";

    public static void setPreferences(Context context, String key, String value) {
        SharedPreferences p = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPreferences(Context context, String key) {
        SharedPreferences p = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        p = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        return p.getString(key, "");
    }

    public static void delPreferences(Context context, String key) {
        SharedPreferences p = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }

    public static void setIntPreferences(Context context, String key, int value) {
        SharedPreferences p = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getIntPreferences(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("pref", Activity.MODE_PRIVATE);
        int myIntValue = sp.getInt(key, -1);
        return myIntValue;
    }

    public void saveFavorites(Context context, List<Manga> favorites){
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavs = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavs);

        editor.commit();
    }

    public void addFavorite(Context context, Manga Manga){
        List<Manga> favorites = getFavorites(context);
        if(favorites == null)
            favorites = new ArrayList<Manga>();
        favorites.add(Manga);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Manga manga) {
        ArrayList<Manga> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(manga);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Manga> getFavorites(Context context){
        SharedPreferences settings;
        List<Manga> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);

        if(settings.contains(FAVORITES)){
            String jsonFavs = settings.getString(FAVORITES,null);
            Gson gson = new Gson();
            Manga[] favoriteItems = gson.fromJson(jsonFavs,Manga[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Manga>(favorites);

        }else{
            return null;
        }
        return (ArrayList<Manga>) favorites;
    }

}
