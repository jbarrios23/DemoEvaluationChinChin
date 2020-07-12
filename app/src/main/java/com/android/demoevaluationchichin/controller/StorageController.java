package com.android.demoevaluationchichin.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.demoevaluationchichin.LoginActivity;
import com.android.demoevaluationchichin.model.CurrencyParameter;
import com.android.demoevaluationchichin.model.LoginParameter;
import com.google.gson.Gson;

public class StorageController {

    private static StorageController mInstance;
    private static Context context;
    private SharedPreferences mSharedPreferences;
    public static String CLASS_TAG= StorageController.class.getSimpleName();

    public StorageController(Context context) {
        this.context = context;
        this.mSharedPreferences = this.context.getApplicationContext().getSharedPreferences("AppCallback", 0);
    }

    public static synchronized StorageController getInstance(Context context_one) {
        if (mInstance == null) {
            mInstance = new StorageController(context_one);
        }
        context = context_one;
        return mInstance;
    }

    /**
     * Method save token
     * @param token: token from login user
     */
    public void saveToken(String token){
        SharedPreferences.Editor data_user=mSharedPreferences.edit();
        data_user.putString("Token",token);
        data_user.apply();
        Log.d(CLASS_TAG,"Token Push Saved");

    }
    /**
     * Method hasTokenRegister lets Know if token is registered in local storage
     *
     */
    public boolean hasTokenRegister(){
        boolean hasToken = false;
        String token=mSharedPreferences.getString("Token","");
        if(token.length()>0){
            hasToken=true;

        }
        Log.d(CLASS_TAG,"Token from storage controller");

        return hasToken;
    }
    /**
     * Method get token registered in local storage
     *
     */
    public String getToken(){

        String token=mSharedPreferences.getString("Token","");
        Log.d(CLASS_TAG,"get token push"+token);

        return token;
    }

    public void deleteToken(){
        mSharedPreferences=context.getSharedPreferences("AppCallback", 0);
        SharedPreferences.Editor editor_login = mSharedPreferences.edit();
        editor_login.clear();
        editor_login.commit();
    }

    /**
     * Method save token
     * @param username: user name from login user
     */
    public void saveNameUser(String username){
        SharedPreferences.Editor data_user=mSharedPreferences.edit();
        data_user.putString("UserName",username);
        data_user.apply();
        Log.d(CLASS_TAG,"User Name Saved");

    }
    /**
     * Method hasTokenRegister lets Know if user name is registered in local storage
     *
     */
    public boolean hasUserNameRegister(){
        boolean hasToken = false;
        String token=mSharedPreferences.getString("UserName","");
        if(token.length()>0){
            hasToken=true;

        }
        Log.d(CLASS_TAG,"User Name from storage controller "+hasToken);

        return hasToken;
    }
    /**
     * Method get user name registered in local storage
     *
     */
    public String getUserName(){
        String token=mSharedPreferences.getString("UserName","");
        Log.d(CLASS_TAG,"get token push"+token);
        return token;
    }

    public void deleteUserName(){
        mSharedPreferences=context.getSharedPreferences("AppCallback", 0);
        SharedPreferences.Editor editor_login = mSharedPreferences.edit();
        editor_login.clear();
        editor_login.commit();
    }

    /**
     * Method save Login parameter registered in local storage
     *
     */

    public void saveLoginParameter(LoginParameter parameter){
        SharedPreferences.Editor data_user=mSharedPreferences.edit();
        Gson gson = new Gson();
        String jsonTags = gson.toJson(parameter);
        data_user.putString("LoginParameter",jsonTags);
        data_user.apply();
        Log.i(CLASS_TAG,"Login Parameter Saved in Storage Controller ");

    }
    /**
     * Method isRegisterDevice lets Know if Device is registered in local storage
     *
     */
    public boolean isRegisterLogin(){
        boolean hasToken = false;
        String token=mSharedPreferences.getString("LoginParameter","");
        if(token.length()>0){
            hasToken=true;

        }
        Log.i(CLASS_TAG,"From Local Storage isRegisterLoginParameter "+hasToken);
        return hasToken;
    }
    /**
     * Method get Device registered in local storage
     *
     */
    public LoginParameter getLoginParameter(){

        Gson gson = new Gson();
        String values=mSharedPreferences.getString("LoginParameter","");
        LoginParameter loginParameter=gson.fromJson(values,LoginParameter.class);

        Log.i(CLASS_TAG,"get Login Parameter from Local Storage ");
        return loginParameter;
    }

    public void deleteLoginParameter(){
        mSharedPreferences=context.getSharedPreferences("AppCallback", 0);
        SharedPreferences.Editor editor_login = mSharedPreferences.edit();
        editor_login.clear();
        editor_login.commit();
    }

    /**
     * Method save Login parameter registered in local storage
     *
     */

    public void saveCurrencyParameter(CurrencyParameter parameter){
        SharedPreferences.Editor data_user=mSharedPreferences.edit();
        Gson gson = new Gson();
        String jsonTags = gson.toJson(parameter);
        data_user.putString("CurrencyParameter",jsonTags);
        data_user.apply();
        Log.i(CLASS_TAG,"Currency Parameter Saved in Storage Controller ");

    }
    /**
     * Method isRegisterDevice lets Know if Device is registered in local storage
     *
     */
    public boolean isRegisterCurrency(){
        boolean hasToken = false;
        String token=mSharedPreferences.getString("CurrencyParameter","");
        if(token.length()>0){
            hasToken=true;

        }
        Log.i(CLASS_TAG,"From Local Storage isRegisterCurrencyParameter "+hasToken);
        return hasToken;
    }
    /**
     * Method get Device registered in local storage
     *
     */
    public CurrencyParameter getCurrencyParameter(){

        Gson gson = new Gson();
        String values=mSharedPreferences.getString("CurrencyParameter","");
        CurrencyParameter currencyParameter=gson.fromJson(values,CurrencyParameter.class);

        Log.i(CLASS_TAG,"get Currency Parameter from Local Storage ");
        return currencyParameter;
    }

    public void deleteCurrencyParameter(){
        mSharedPreferences=context.getSharedPreferences("AppCallback", 0);
        SharedPreferences.Editor editor_login = mSharedPreferences.edit();
        editor_login.clear();
        editor_login.commit();
    }

    /**
     * Method save token
     * @param datetime: date time from login user
     */
    public void saveDateTime(String datetime){
        SharedPreferences.Editor data_user=mSharedPreferences.edit();
        data_user.putString("DateTime",datetime);
        data_user.apply();
        Log.d(CLASS_TAG,"Date time Saved");

    }
    /**
     * Method hasTokenRegister lets Know if user name is registered in local storage
     *
     */
    public boolean hasDateTimeRegister(){
        boolean hasToken = false;
        String token=mSharedPreferences.getString("DateTime","");
        if(token.length()>0){
            hasToken=true;

        }
        Log.d(CLASS_TAG,"User Name from storage controller "+hasToken);

        return hasToken;
    }
    /**
     * Method get date time registered in local storage
     *
     */
    public String getDateTime(){
        String token=mSharedPreferences.getString("DateTime","");
        Log.d(CLASS_TAG,"get date time"+token);
        return token;
    }

    public void deleteDateTime(){
        mSharedPreferences=context.getSharedPreferences("AppCallback", 0);
        SharedPreferences.Editor editor_login = mSharedPreferences.edit();
        editor_login.clear();
        editor_login.commit();
    }




}
