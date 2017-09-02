package com.example.pxshl.weather.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 */
public class MyCursorAdapter extends CursorAdapter {
    private int mColumnIndex;
    private SQLiteDatabase mSqLiteDatabase;

    public MyCursorAdapter(Context context, Cursor c, int col, int flags) {
        super(context, c,flags);
        mColumnIndex = col;
        mSqLiteDatabase = context.openOrCreateDatabase("weather", 0, null);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final TextView view = (TextView) inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view).setText(cursor.getString(mColumnIndex));
    }

    @Override
    public String convertToString(Cursor cursor) {
        return cursor.getString(mColumnIndex);
    }

    @Override/*实现自动匹配的核心方法*/
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        if (constraint != null) {
            //LIKE'%en%' 将搜索在任何位置包含字母（模糊匹配）。
            String selection = "city_name like \'%" + constraint.toString() + "%\'";
            return mSqLiteDatabase.query("city", new String[] {"city_name","_id"}, selection, null, null, null, null);
        }else {
            return null;
        }
    }
}
