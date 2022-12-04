package com.example.library_manager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    //---------------------------------------------------//
    public static final String DB_NAME = "library_manager";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "books";
    //----------------------------------------------//
    public static final String ID_CLN = "id";
    public static final String BOOK_SN_CLN = "book_SN";
    public static final String BOOK_NAME_CLN = "Book_name";
    public static final String AUTHOR_NAME_CLN = "Author_name";
    public static final String BOOK_COPIES_CLN = "Book_copies";
    //----------------------------------------------//

    public DataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID_CLN + " INTEGER PRIMARY KEY AUTOINCREMENT," + BOOK_SN_CLN + " TEXT, " + BOOK_NAME_CLN + " TEXT, " + AUTHOR_NAME_CLN + " TEXT, " + BOOK_COPIES_CLN + " INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //  sqLiteDatabase.execSQL("DROP tABLE IF EXISTS " + TABLE_NAME);
        // onCreate(sqLiteDatabase);
    }

    public void InsertBook(Book b) {
        SQLiteDatabase DB = getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(BOOK_SN_CLN, b.getBOOK_SN());
        Values.put(BOOK_NAME_CLN, b.getBOOK_NAME());
        Values.put(AUTHOR_NAME_CLN, b.getAUTHOR_NAME());
        Values.put(BOOK_COPIES_CLN, b.getBOOK_COPIES());
        DB.insert(TABLE_NAME, null, Values);
    }

    public long NUMBER_OF_BOOKS() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @SuppressLint("Range")
    public ArrayList<Book> GetAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        SQLiteDatabase DBR = getReadableDatabase();
        Cursor cursor = DBR.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Book bk = new Book();
                bk.BOOK_ID = cursor.getInt(cursor.getColumnIndex(ID_CLN));
                bk.BOOK_SN = cursor.getString(cursor.getColumnIndex(BOOK_SN_CLN));
                bk.BOOK_NAME = cursor.getString(cursor.getColumnIndex(BOOK_NAME_CLN));
                bk.AUTHOR_NAME = cursor.getString(cursor.getColumnIndex(AUTHOR_NAME_CLN));
                bk.BOOK_COPIES = cursor.getInt(cursor.getColumnIndex(BOOK_COPIES_CLN));
                books.add(bk);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return books;
    }

    public void DeleteBook(Book b) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(b.getBOOK_ID())};
        db.delete(TABLE_NAME, "id=?", args);
    }

    @SuppressLint("Range")
    public ArrayList<Book> search(String s) {
        ArrayList<Book> books = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + BOOK_NAME_CLN + " LIKE '%" + s + "%' OR " + BOOK_SN_CLN + " LIKE '%" + s + "%'", null);
        if (cursor.moveToFirst()) {
            do {
                Book bk = new Book();
                bk.BOOK_ID = cursor.getInt(cursor.getColumnIndex(ID_CLN));
                bk.BOOK_SN = cursor.getString(cursor.getColumnIndex(BOOK_SN_CLN));
                bk.BOOK_NAME = cursor.getString(cursor.getColumnIndex(BOOK_NAME_CLN));
                bk.AUTHOR_NAME = cursor.getString(cursor.getColumnIndex(AUTHOR_NAME_CLN));
                bk.BOOK_COPIES = cursor.getInt(cursor.getColumnIndex(BOOK_COPIES_CLN));
                books.add(bk);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return books;
    }

}
