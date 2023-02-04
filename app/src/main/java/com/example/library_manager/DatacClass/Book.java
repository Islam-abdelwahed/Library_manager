package com.example.library_manager.DatacClass;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Book {

    private String BOOK_SN = "null";
    private int BOOK_ID = 0;
    private String BOOK_NAME = "null";
    private String AUTHOR_NAME = "null";
    private int BOOK_COPIES = 0;
    private byte[] BOOK_IMAGE = null;

        public byte[] getBOOK_IMAGE() {return BOOK_IMAGE;}

        public void setBOOK_IMAGE(byte[] BOOK_IMAGE) {this.BOOK_IMAGE = BOOK_IMAGE;}

        public String getBOOK_NAME() {
        return BOOK_NAME;
    }

        public void setBOOK_NAME(String BOOK_NAME) {
            this.BOOK_NAME = BOOK_NAME;
        }

        public String getAUTHOR_NAME() {
            return AUTHOR_NAME;
        }

        public void setAUTHOR_NAME(String AUTHOR_NAME) {
            this.AUTHOR_NAME = AUTHOR_NAME;
        }

        public int getBOOK_COPIES() {return BOOK_COPIES;}

        public void setBOOK_COPIES(int BOOK_COPIES) {
            this.BOOK_COPIES = BOOK_COPIES;
        }

        public int getBOOK_ID() {
            return BOOK_ID;
        }

        public void setBOOK_ID(int BOOK_ID) {
            this.BOOK_ID = BOOK_ID;
        }

        public String getBOOK_SN() {
            return BOOK_SN;
        }

        public void setBOOK_SN(String BOOK_SN) {
            this.BOOK_SN = BOOK_SN;
        }

        public boolean IsEmpty(){
            return AUTHOR_NAME.isEmpty() || BOOK_NAME.isEmpty() || BOOK_SN.isEmpty() ||BOOK_COPIES < 1;
        }

        @NonNull
        public static ArrayList<Book> fromJson(String s) throws JSONException {
            ArrayList<Book> books  = new ArrayList<>();
            JSONArray itemsArray = new JSONArray(s);
            int i = 0;
            while (i < itemsArray.length()) {
                JSONObject book = itemsArray.getJSONObject(i);
                Book b = new Book();
                b.setBOOK_ID(Integer.parseInt(book.getString("id")));
                b.setBOOK_SN(book.getString("Book_Sn"));
                b.setBOOK_NAME(book.getString("Book_Name"));
                b.setAUTHOR_NAME(book.getString("Author_Name"));
                b.setBOOK_COPIES(Integer.parseInt(book.getString("Book_Copies")));
                books.add(i,b);
                i++;
            }
            return books;
        }
}

