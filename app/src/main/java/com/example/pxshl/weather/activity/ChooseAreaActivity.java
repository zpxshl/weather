package com.example.pxshl.weather.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pxshl.weather.R;
import com.example.pxshl.weather.adapter.MyCursorAdapter;
import com.example.pxshl.weather.db.Db;
import com.example.pxshl.weather.utils.HttpUtils;

public class ChooseAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);
        init();
    }

    private void init() {
        AutoCompleteTextView mActv = (AutoCompleteTextView)findViewById(R.id.actv);
        MyCursorAdapter adapter = new MyCursorAdapter(this,null,0, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mActv.setAdapter(adapter);
        mActv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String city = ((TextView)view).getText().toString();
                Intent intent = new Intent(ChooseAreaActivity.this,ShowActivity.class);
                intent.putExtra("city",city);
                startActivity(intent);
                finish();
            }
        });
    }
}
