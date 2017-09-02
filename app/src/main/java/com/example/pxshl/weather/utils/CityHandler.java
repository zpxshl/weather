package com.example.pxshl.weather.utils;

import com.example.pxshl.weather.db.Db;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX解析存放在assets文件夹中的全国全部地区的数据（xml格式），并存进数据库
 * 这样做的目的是为了配合AutoCompleteTextView实现输入自动匹配功能
 */
public class CityHandler extends DefaultHandler {

    private Db mDb;
    private String mCityName;
    public CityHandler(Db db)
    {
        mDb = db;
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
            mCityName = new String(ch,start,length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.contains("citynm")){
            mDb.saveCityName(mCityName);
        }
    }
}
