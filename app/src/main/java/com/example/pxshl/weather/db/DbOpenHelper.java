package com.example.pxshl.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @param
 * @return
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    //city表储存全国城市的名字信息
    private final static String CREATE_CITY = "create table city("
            + "_id integer primary key autoincrement,"  //用_id是因为匹配输入需要
            + "city_name text) ";

    //weather表储存城市的天气信息
    private static final String CREATE_COUNTY_WEATHER="create table weather(id integer primary key autoincrement," +
            "days text," +
            "week text," +
            "city_name text," +
            "temp_low text," +
            "temp_high text," +
            "weather text," +
            "weather_icon text," +
            "weather_icon1 text," +
            "wind text," +
            "update_time text," +
            "wind_direction text)";

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        /*执行建表语句*/
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY_WEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}