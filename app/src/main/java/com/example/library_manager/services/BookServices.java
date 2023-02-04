package com.example.library_manager.services;

import android.util.Log;

import com.example.library_manager.DatacClass.Book;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class BookServices {

    public static void GetBooks(IResult iResult) throws IOException, JSONException {
        StringBuilder s = new StringBuilder();
        URL url = new URL("http://192.168.1.3/Library_manager/v1/Books.php");
        Thread t=new Thread(() -> {
            try {
                URLConnection urlConnection = url.openConnection();
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    s.append(line).append("\n");
                }
                inputStreamReader.close();
                bufferedReader.close();
                if(iResult != null)
                    iResult.onResultComplete(s.toString());
            } catch (Exception e) {
            e.printStackTrace();
            }
        });
        t.start();
    }

    public static void DeleteBook(IResult iResult,int id) throws IOException, JSONException {
        URL url = new URL("http://192.168.1.3/Library_manager/v1/InsertBook.php?");

        Thread t=new Thread(() -> {
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Language", "en-US");
                urlConnection.setRequestProperty("id", String.valueOf(id));
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder s=new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    s.append(line);
                }
                inputStreamReader.close();
                bufferedReader.close();
                if(iResult != null)
                    iResult.onResultComplete(s.toString());
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    public static void InsertBook(IResult iResult, Book new_book) throws IOException {
        URL url = new URL("http://192.168.1.3/Library_manager/v1/InsertBook.php?");

        Thread t=new Thread(() -> {
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Language", "en-US");
                urlConnection.setRequestProperty("id", String.valueOf(new_book.getBOOK_ID()));
                urlConnection.setRequestProperty("Book_Sn", new_book.getBOOK_SN());
                urlConnection.setRequestProperty("Book_Name", new_book.getBOOK_NAME());
                urlConnection.setRequestProperty("Author_Name", new_book.getAUTHOR_NAME());
                urlConnection.setRequestProperty("Book_Copies", String.valueOf(new_book.getBOOK_COPIES()));

                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder s=new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    s.append(line);
                }
                inputStreamReader.close();
                bufferedReader.close();
                if(iResult != null)
                    iResult.onResultComplete(s.toString());
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();


    }

    public static void Search(IResult iResult,String key) throws IOException, JSONException {
        URL url = new URL("http://192.168.1.3/Library_manager/v1/InsertBook.php?");

        Thread t=new Thread(() -> {
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Language", "en-US");
                urlConnection.setRequestProperty("key", key);
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder s=new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    s.append(line).append("\n");;
                }
                inputStreamReader.close();
                bufferedReader.close();
                if(iResult != null)
                    iResult.onResultComplete(s.toString());
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }
}
