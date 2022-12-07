package com.example.library_manager.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.library_manager.Borrowing;

public class BorrowingDB extends SQLiteOpenHelper {

    //---------------------------------------------------//
    public static final String DB_NAME = "borrowing_data";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "books";
    //----------------------------------------------//
    public static final String BORROWER_NAME_CLN="Borrower_name";
    public static final String BORROWER_ID_CLN="Borrower_id";
    public static final String BOOK_SN="book_SN";

    public BorrowingDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ( "+BORROWER_ID_CLN+" TEXT,"+BORROWER_NAME_CLN+" TEXT,"+BOOK_SN+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP tABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void InsertBorrowing(Borrowing b){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(BOOK_SN,b.getBOOK_SN());
        values.put(BORROWER_ID_CLN,b.getBORROWER_ID());
        values.put(BORROWER_NAME_CLN,b.getBORROWER_NAME());
        db.insert(TABLE_NAME,null,values);
    }

    public void DeleteBorrowing(Borrowing b) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(b.getBORROWER_ID())};
        db.delete(TABLE_NAME, "Borrower_id=?", args);
    }
}
