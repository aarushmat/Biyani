package com.example.biyani;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;


public class StaticDataHelper
{

    private static SharedPreferences pref;

//    public static StaticDataHelper getInstance(Context activity)
//    {
//        return new StaticDataHelper(activity);
//    }

//    public StaticDataHelper(Context activity) {
//        this.activity = (AppCompatActivity) activity;
//    }
//
//        LayoutInflater inflater = this.activity.getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.loading_dailog, null);
//        dialogBuilder.setView(dialogView);
//        b = dialogBuilder.create();
//    }

    /*public static void showMessage(Context activity, String title, String message)
    {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                alertDialog.dismiss();
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }*/

    public static void setStringInPreferences(Context ctx, String key, String value)
    {
        pref = ctx.getSharedPreferences("orgeva", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static void setIntegerInPreferences(Context ctx, String key, int value)
    {
        pref = ctx.getSharedPreferences("orgeva", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void setBooleanInPreferences(Context ctx, String key, boolean value)
    {
        pref = ctx.getSharedPreferences("orgeva", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);

        editor.apply();
    }

    public static String getStringFromPreferences(Context ctx, String key)
    {
        pref = ctx.getSharedPreferences("orgeva", Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public static int getIntegerFromPreferences(Context ctx, String key)
    {
        pref = ctx.getSharedPreferences("orgeva", Context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    public static boolean getBooleanFromPreferences(Context ctx, String key)
    {
        pref = ctx.getSharedPreferences("orgeva", Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public static void showToast(Context ctx, String msg)
    {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static String getStringImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public static Bitmap getBitmapFromBase64(String bmp)
    {
        byte[] decodedString = Base64.decode(bmp, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static Bitmap byteArrayToBitmap(byte[] ar)
    {
        return BitmapFactory.decodeByteArray(ar, 0, ar.length);
    }

    public static byte[] bitmapToByteArray(Bitmap ar)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ar.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static String getMimeType(String url)
    {
        String extension = url.substring(url.lastIndexOf("."));
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extension);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap);
        return mimeType;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight)
    {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


}