package com.example.pxshl.weather.model;

/**
 * @param
 * @return
 *  天气信息
 * 天气接口地址http://api.k780.com:88/?app=weather.future&weaid=101010100&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml
 */
public class Weather {

    private String mDays;//日期
    private String mWeek;//周几
    private String mCityName;//城市（地区）名
    private String mTemp_low; //最低温度
    private String mTemp_high;//最高温度
    private String mWeather; //天气
    private String mWeather_icon;//天气图片 （网址）
    private String mWeather_icon1;//天气图片 （网址）
    private String mWind;//风向
    private String mWind_direction;//风力
    private String mUpdate_time;//更新时间 （天气接口返回的数据中不包括此信息）

    public String getDays() {
        return mDays;
    }
    public void setDays(String days) {
        mDays = days;
    }

    public String getWeek() {
        return mWeek;
    }
    public void setWeek(String week) {
        mWeek = week;
    }

    public String getCityName() {
        return mCityName;
    }
    public void setCityName(String cityName) { mCityName= cityName;}

    public String getTemp_low() {
        return mTemp_low;
    }
    public void setTemp_low(String temp_low) {
        mTemp_low = temp_low;
    }

    public String getWeather() {
        return mWeather;
    }
    public void setWeather(String weather) {
        mWeather = weather;
    }

    public String getWeather_icon() {
        return mWeather_icon;
    }
    public void setWeather_icon(String weather_icon) {
        mWeather_icon = weather_icon;
    }

    public String getWeather_icon1() {
        return mWeather_icon1;
    }
    public void setWeather_icon1(String weather_icon1) {
        mWeather_icon1 = weather_icon1;
    }

    public String getTemp_high() {
        return mTemp_high;
    }
    public void setTemp_high(String temp_high) {
        mTemp_high = temp_high;
    }

    public String getWind() {
        return mWind;
    }
    public void setWind(String wind) {
        mWind = wind;
    }

    public String getWind_direction() {
        return mWind_direction;
    }
    public void setWind_direction(String wind_direction) {
        mWind_direction = wind_direction;
    }

    public String getUpdate_time() {
        return mUpdate_time;
    }
    public void setUpdate_time(String time) {
        mUpdate_time = time;
    }
}
