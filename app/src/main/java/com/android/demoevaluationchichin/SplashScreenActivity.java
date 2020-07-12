package com.android.demoevaluationchichin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.demoevaluationchichin.controller.StorageController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SplashScreenActivity extends AppCompatActivity {

    public static String CLASS_TAG=SplashScreenActivity.class.getSimpleName();
    public int delay=4000;
    public Intent intent;
    public StorageController storageController;
    String dateStart ="";
    String dateStop = "";
    Date d1 = null;
    Date d2 = null;
    long diffSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        storageController=StorageController.getInstance(this);

        initEventThread();





    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private void initEventThread() {
        // TODO Auto-generated method stub
        new Thread (){
            public void run(){
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(storageController.hasTokenRegister()){
                SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
                dateStart=storageController.getDateTime();
                Log.e(CLASS_TAG,"dateStart "+dateStart);
                dateStop=getDateTime();
                Log.e(CLASS_TAG,"dateStop "+dateStop);
                try {
                    d1 = format.parse(dateStart);
                    d2 = format.parse(dateStop);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long diff = d2.getTime() - d1.getTime();
                diffSeconds = diff / 1000;
                Log.e(CLASS_TAG,"diff "+diffSeconds);
                if(diffSeconds>300){
                    storageController.deleteToken();
                    intent=new Intent(SplashScreenActivity.this,LoginActivity.class);
                }else{
                    intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                }
            }else{
                intent=new Intent(SplashScreenActivity.this,LoginActivity.class);
            }

            startActivity(intent);
            SplashScreenActivity.this.finish();


        }
    };
}