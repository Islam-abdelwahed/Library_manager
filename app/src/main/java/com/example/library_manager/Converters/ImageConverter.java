package com.example.library_manager.Converters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.library_manager.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageConverter {

    public static byte[] bitmapToByte(Bitmap bitmap) {
        try { ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] x = stream.toByteArray();
            stream.close();
            return x;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getImageDataInBitmap(byte[] imageData) {
        if (imageData != null) {
            return BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        }
        return  null;
    }
}
