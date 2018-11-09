package com.mteam.multichoicequiz.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mteam.multichoicequiz.model.Category;
import com.mteam.multichoicequiz.model.Question;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteAssetHelper {
    private static final String DB_NAME = "QuizData.db";
    private static final int DB_VERSION = 1;
    private static DBHelper dbHelper;

    public DBHelper(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, storageDirectory, factory, version);
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public static synchronized DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    //get all Lisst Category

    public List<Category> getListCategory() {
        List<Category> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Category", null);
        while (cursor.moveToNext()) {
            int id =cursor.getInt(0);

            Category category = new Category(id, cursor.getString(cursor.getColumnIndex("Name")), cursor.getString(cursor.getColumnIndex("Image")));
            Log.d("TAG", "getListCategory: " + category.getId());
            list.add(category);
        }
        cursor.close();
        dbHelper.close();
        return list;
    }

    public List<Question> getListQuestionByCategory(int category) {
        List<Question> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(String.format("SELECT * FROM Question WHERE CategoryID= %d ORDER BY RANDOM() LIMIT 30",category), null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String questionText = cursor.getString(cursor.getColumnIndex("QuestionText"));
            String questionImage = cursor.getString(cursor.getColumnIndex("QuestionImage"));
            String answerA = cursor.getString(cursor.getColumnIndex("AnswerA"));
            String answerB = cursor.getString(cursor.getColumnIndex("AnswerB"));
            String answerC = cursor.getString(cursor.getColumnIndex("AnswerC"));
            String answerD = cursor.getString(cursor.getColumnIndex("AnswerD"));
            String correctAnswer = cursor.getString(cursor.getColumnIndex("CorrectAnswer"));
            int isImageQuestion = cursor.getInt(cursor.getColumnIndex("IsImageQuestion"));
            int categoryID = cursor.getInt(cursor.getColumnIndex("CategoryID"));
            Question question = new Question(id, questionText, questionImage, answerA, answerB, answerC, answerD, correctAnswer, isImageQuestion, categoryID);
            list.add(question);
        }
        cursor.close();
        dbHelper.close();
        return list;
    }
}