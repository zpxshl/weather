package com.example.pxshl.weather.utils;

import com.example.pxshl.weather.db.Db;
import com.example.pxshl.weather.model.Weather;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.SimpleDateFormat;

/**
 *SAX解析XML格式的天气信息
*接口地址http://api.k780.com:88/?app=weather.future&weaid=101010100&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml
*/
public class WeatherHandler extends DefaultHandler {

    private Db mDb;
    private Weather mWeather;
    private String mTag;

    public WeatherHandler(Db db)
    {
        mDb = db;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
        mDb.deleteWeather();  //删除数据库里面旧的天气数据
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.contains("item")){
            mWeather = new Weather();
        }
        mTag = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String values=new String(ch,start,length);
        if ("days".equals(mTag)) {
            mWeather.setDays(values);
        }else if ("week".equals(mTag)){
            mWeather.setWeek(values);
        }else if ("citynm".equals(mTag)){
            mWeather.setCityName(values);
        }else if ("temp_low".equals(mTag)){
            mWeather.setTemp_low(values);
        }else if ("temp_high".equals(mTag)){
            mWeather.setTemp_high(values);
        }else if ("weather".equals(mTag)){
            mWeather.setWeather(values);
        }else if ("weather_icon".equals(mTag)){
            mWeather.setWeather_icon(values);
        }else if ("weather_icon1".equals(mTag)){
            mWeather.setWeather_icon1(values);
        }else if ("wind".equals(mTag)){
            mWeather.setWind(values);
        }else if ("winp".equals(mTag)){             //winp是接口返回的数据中风向的标签，我也不知道为什么风向是winp
            mWeather.setWind_direction(values);
            //Update_time不是返回的数据,随便跟哪个tag放一起都可以
            mWeather.setUpdate_time(new SimpleDateFormat("MM月dd日 HH:mm").format(System.currentTimeMillis()));
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.contains("item")){
            mDb.saveWeather(mWeather);
        }
        mTag = qName;
    }

}
