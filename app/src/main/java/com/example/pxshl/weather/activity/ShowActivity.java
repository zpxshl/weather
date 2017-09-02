package com.example.pxshl.weather.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pxshl.weather.R;
import com.example.pxshl.weather.db.Db;
import com.example.pxshl.weather.model.Weather;
import com.example.pxshl.weather.utils.HttpUtils;
import com.example.pxshl.weather.utils.WeatherHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 *
 */
public class ShowActivity extends AppCompatActivity {

    private final static int UPDATE_UI = 0x123;
    private Db mDb;
    private List<Weather> mListWeather;
    private Dialog mDialog;
    private Handler mHandler;

    private TextView city_name;
    private TextView update_time ;
    private TextView avg_degree;
    private ImageView image_weather1;
    private ImageView image_weather2 ;
    private TextView degree;
    private TextView today_weather;
    private TextView today_wind_dir;
    private TextView today_wind_power;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        init();
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        updateWeather(city);
    }

    private void init() {
        mDb = Db.getInstance(getApplicationContext());
        Button refresh = (Button)findViewById(R.id.refresh);
        Button changeCity = (Button)findViewById(R.id.changeCity);
        mDialog = new Dialog(this);
        mDialog.setCancelable(false);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case UPDATE_UI:
                        mListWeather = mDb.loadCountyWeather();
                        update_UI();
                        break;
                }
            }
        };

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWeather(null);
            }
        });

        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this,ChooseAreaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        city_name = (TextView)findViewById(R.id.city_name);
        update_time = (TextView)findViewById(R.id.update_time);
        avg_degree = (TextView)findViewById(R.id.avg_degree);
        image_weather1 = (ImageView)findViewById(R.id.image_weather1);
        image_weather2 = (ImageView)findViewById(R.id.image_weather2);
        degree = (TextView)findViewById(R.id.degree);
        today_weather = (TextView)findViewById(R.id.today_weather);
        today_wind_dir = (TextView)findViewById(R.id.today_wind_dir);
        today_wind_power = (TextView)findViewById(R.id.today_wind_power);


    }


    private void update_UI() {
        mListWeather = mDb.loadCountyWeather();
        city_name.setText(mListWeather .get(0).getCityName());
        update_time.setText(mListWeather .get(0).getUpdate_time());
        String  AvgDegree = "" + ( Integer.parseInt(mListWeather.get(0).getTemp_high()) + Integer.parseInt(mListWeather.get(0).getTemp_low()) ) / 2;
        degree.setText(mListWeather.get(0).getTemp_high() + "/" + mListWeather.get(0).getTemp_low());
        avg_degree.setText(AvgDegree + "℃");
        today_weather.setText(mListWeather.get(0).getWeather());
        today_wind_dir.setText(mListWeather.get(0).getWind_direction());
        today_wind_power.setText(mListWeather.get(0).getWind());

        HttpUtils.sendHttpRequestForBitmap(mListWeather.get(0).getWeather_icon(), new HttpUtils.HttpCallBackListener() {
            @Override
            public void onFinish(final Object response) {
                if (response != null && response instanceof Bitmap){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image_weather1.setImageBitmap((Bitmap) response);
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        HttpUtils.sendHttpRequestForBitmap(mListWeather.get(0).getWeather_icon1(), new HttpUtils.HttpCallBackListener() {
            @Override
            public void onFinish(final Object response) {
                if (response != null && response instanceof Bitmap){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image_weather2.setImageBitmap((Bitmap)response);
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        int[] week_weather={R.id.week2_weather,R.id.week3_weather,R.id.week4_weather,R.id.week5_weather,R.id.week6_weather,R.id.week7_weather};
        int[] week_temp={R.id.week2_temp,R.id.week3_temp,R.id.week4_temp,R.id.week5_temp,R.id.week6_temp,R.id.week7_temp};
        int[] week_wind={R.id.week2_wind,R.id.week3_wind,R.id.week4_wind,R.id.week5_wind,R.id.week6_wind,R.id.week7_wind};
        int[] week_date={R.id.week2_date,R.id.week3_date,R.id.week4_date,R.id.week5_date,R.id.week6_date,R.id.week7_date};
        int[] week_week={R.id.week4_week,R.id.week5_week,R.id.week6_week,R.id.week7_week};

        for (int i=0;i < mListWeather.size() -1 ;i++){

            if (i >= 2){      //前3天显示 今天明天后天 第四天开始才显示周几
                TextView textView_week = (TextView) findViewById(week_week[i-2]);
                textView_week.setText(mListWeather.get(i).getWeek());
            }

            TextView textView_weather= (TextView) findViewById(week_weather[i]);
            textView_weather.setText(mListWeather.get(i).getWeather());
            TextView textView_temp= (TextView)findViewById(week_temp[i]);
            textView_temp.setText(mListWeather.get(i).getTemp_low() + "/" + mListWeather.get(i).getTemp_high()+"℃");
            TextView textView_wind= (TextView)findViewById(week_wind[i]);
            textView_wind.setText(mListWeather.get(i).getWind());
            TextView textView_date= (TextView) findViewById(week_date[i]);
            textView_date.setText(mListWeather.get(i).getDays().substring(5,10));


        }



    }

    private void updateWeather(@Nullable String city) {
        if (city == null){
            city = mDb.getPresentCityName();
        }

       mDialog.show();
        String urls ="http://api.k780.com:88/?app=weather.future&weaid=" + city +"&appkey=19338&sign=fcd6f8a7e97a4f7790b228ae6b6e7860&format=xml";
        HttpUtils.sendHttpRequestForString(urls, new HttpUtils.HttpCallBackListener() {
            @Override
            public void onFinish(Object response) {
                if (response != null && response instanceof String){
                    try {
                        FileOutputStream outputStream = openFileOutput("weather.xml", Context.MODE_PRIVATE);
                        outputStream.write(((String)response).getBytes());
                        outputStream.close();
                        File file = new File(getFilesDir(),"weather.xml");
                        SAXParserFactory factory = SAXParserFactory.newInstance();
                        SAXParser parser = factory.newSAXParser();
                        parser.parse(file,new WeatherHandler(mDb));
                        Message message = new Message();        //通知主线程更新UI
                        message.what = UPDATE_UI;
                        mHandler.sendMessage(message);

                    }catch (Exception e)
                    {
                        onError(e);
                    }finally {
                       if (mDialog.isShowing()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mDialog.cancel();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
               if (mDialog.isShowing()) {
                    runOnUiThread(new Runnable() {      //处理UI需要在主线程执行
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            Toast.makeText(ShowActivity.this,"查询天气失败，请检查网络连接",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
