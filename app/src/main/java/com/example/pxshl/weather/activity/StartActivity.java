package com.example.pxshl.weather.activity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.pxshl.weather.db.Db;
import com.example.pxshl.weather.utils.CityHandler;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class StartActivity extends AppCompatActivity {

    private final static int SUCCESS = 0x111;
    private Db mDb;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = Db.getInstance(getApplicationContext());
        if (mDb.isNoCity()){        //如果数据库的city表为空
          writeCityToDb();
        }

        if (mDb.isNoWeather()){  //如果数据库的weather表为空，说明还没有选择天气地区
            Intent intent = new Intent(StartActivity.this,ChooseAreaActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(StartActivity.this,ShowActivity.class);
            startActivity(intent);
        }
        finish();


    }

    private void writeCityToDb()
    {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        InputStream is = StartActivity.this.getAssets().open("strings.xml");
                        SAXParserFactory factory = SAXParserFactory.newInstance();
                        SAXParser saxParser = factory.newSAXParser();
                        saxParser.parse(is, new CityHandler(mDb));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
    }


}