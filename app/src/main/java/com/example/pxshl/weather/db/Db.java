package com.example.pxshl.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.pxshl.weather.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 单例模式，该类提供一系列操作数据库的方法
 */
public class Db {

    private static final String DB_NAME = "weather"; //数据库名
    private static final int VERSION = 1;            //版本
    private static SQLiteDatabase mSqLiteDatabase;
    private static Db db;

    //单例模式的实现条件--将构造函数标记为私有
    private Db (Context context)
    {
        DbOpenHelper openHelper = new DbOpenHelper(context, DB_NAME,null,VERSION);
        mSqLiteDatabase = openHelper.getWritableDatabase();
    }

    //上锁以保证线程安全
    public synchronized static Db getInstance(Context context)
    {
        if (db == null)
        {
            db = new Db(context);
        }
        return db;
    }

    public void saveCityName(String cityName)
    {
            ContentValues values = new ContentValues();
            values.put("city_name",cityName);
            mSqLiteDatabase.insert("city",null,values);
    }

    public  void saveWeather(Weather weather)
    {
        if (weather != null)
        {
            ContentValues values = new ContentValues();
            values.put("days", weather.getDays());
            values.put("week", weather.getWeek());
            values.put("city_name", weather.getCityName());
            values.put("temp_low", weather.getTemp_low());
            values.put("temp_high", weather.getTemp_high());
            values.put("weather", weather.getWeather());
            values.put("weather_icon", weather.getWeather_icon());
            values.put("weather_icon1", weather.getWeather_icon1());
            values.put("wind", weather.getWind());
            values.put("wind_direction", weather.getWind_direction());
            values.put("update_time", weather.getUpdate_time());
            mSqLiteDatabase.insert("weather",null,values);
        }
    }

    //返回当前所查城市的名字
    //程序通过此方法得知上一次所查的城市名
    public synchronized String  getPresentCityName()
    {
        String cityName;
        Cursor cursor = mSqLiteDatabase.query("weather", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
             cityName =  cursor.getString(cursor.getColumnIndex("city_name"));
        }else {
            cityName = null;
        }
        cursor.close();
        return cityName;
    }

   /*用来获取读取数据库中的天气信息*/
   //上锁原因，防止数据库更新到一半时 有人调用此方法读取数据库
    public synchronized List<Weather> loadCountyWeather() {
        List<Weather> list = new ArrayList<>();
        Cursor cursor = mSqLiteDatabase.query("weather", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Weather weather = new Weather();
                weather.setDays(cursor.getString(cursor.getColumnIndex("days")));
                weather.setWeek(cursor.getString(cursor.getColumnIndex("week")));
                weather.setTemp_low(cursor.getString(cursor.getColumnIndex("temp_low")));
                weather.setTemp_high(cursor.getString(cursor.getColumnIndex("temp_high")));
                weather.setWeather(cursor.getString(cursor.getColumnIndex("weather")));
                weather.setWeather_icon(cursor.getString(cursor.getColumnIndex("weather_icon")));
                weather.setWeather_icon1(cursor.getString(cursor.getColumnIndex("weather_icon1")));
                weather.setWind(cursor.getString(cursor.getColumnIndex("wind")));
                weather.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                weather.setWind_direction(cursor.getString(cursor.getColumnIndex("wind_direction")));
                weather.setUpdate_time(cursor.getString(cursor.getColumnIndex("update_time")));
                list.add(weather);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    //删除数据库中的天气数据，用于更新天气时使用
    public synchronized void deleteWeather()
    {
        mSqLiteDatabase.delete("weather",null,null);
    }

    /*
    *判断city表是否为空
    *程序第一次启动时调用此方法来决定是否从assets中读入数据
    */
    public boolean isNoCity()
    {
        Cursor cursor = mSqLiteDatabase.query("city",null,null,null,null,null,null);

        if (cursor.getCount() > 0){
            cursor.close();
            return false;
        }else {
            cursor.close();
            return true;
        }
    }

    public boolean isNoWeather()
    {
        Cursor cursor = mSqLiteDatabase.query("weather",null,null,null,null,null,null);

        if (cursor.getCount() > 0){
            cursor.close();
            return false;
        }else {
            cursor.close();
            return true;
        }
    }

}
