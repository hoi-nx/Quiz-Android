package com.mteam.multichoicequiz.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mteam.multichoicequiz.model.Category;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteAssetHelper {
    private static final String DB_NAME="EDMTQuiz2019.db";
    private static final int DB_VERSION=1;
    private static DBHelper dbHelper;

    public DBHelper(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, storageDirectory, factory, version);
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public static synchronized DBHelper getInstance(Context context){
        if(dbHelper==null){
            dbHelper=new DBHelper(context);
        }
        return dbHelper;
    }

    //get all Lisst Category

    public List<Category> getListCategory(){
        List<Category> list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();

        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM Category",null);
        while (cursor.moveToNext()){

            Category category=new Category(cursor.getColumnIndex("ID"),cursor.getString(cursor.getColumnIndex("Name")),cursor.getString(cursor.getColumnIndex("Image")));
            Log.d("TAG", "getListCategory: "+category.getName());
            list.add(category);
        }
        cursor.close();
        dbHelper.close();
        return list;
    }
}
