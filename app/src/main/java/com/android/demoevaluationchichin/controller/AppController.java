package com.android.demoevaluationchichin.controller;

import android.content.Context;
import android.util.Log;

import com.android.demoevaluationchichin.LoginActivity;
import com.android.demoevaluationchichin.model.ContentCurrency;
import com.android.demoevaluationchichin.model.CurrencyParameter;
import com.android.demoevaluationchichin.model.LoginParameter;
import com.android.demoevaluationchichin.network.ApiUtils;
import com.android.demoevaluationchichin.network.AsyncProcess;
import com.android.demoevaluationchichin.network.EndPoint;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppController {
    public static Context context;
    private static AppController mInstance;
    private String Token;
    private String fullName;
    private LoginParameter loginParameter;
    private CurrencyParameter currencyParameter;
    public EndPoint endPoint;
    public StorageController storageController;

    public static String CLASS_TAG= AppController.class.getSimpleName();
    private ContentCurrency calculateResult;
    public Double moneyInput;



    public AppController(Context context) {
        this.context = context;
        this.currencyParameter=null;
        this.storageController=StorageController.getInstance(context);
    }

    public static synchronized AppController getInstance(Context context_one) {
        if (mInstance == null) {
            mInstance = new AppController(context_one);
        }
        context = context_one;
        return mInstance;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public LoginParameter getLoginParameter() {
        return loginParameter;
    }

    public void setLoginParameter(LoginParameter loginParameter) {
        this.loginParameter = loginParameter;
    }

    public CurrencyParameter getCurrencyParameter() {
        return currencyParameter;
    }

    public void setCurrencyParameter(CurrencyParameter currencyParameter) {
        this.currencyParameter = currencyParameter;
    }

    /**
     * Method that get Device registered
     * @param forsecallservice : It allows effective device search in three ways: by instance, by shared variable or by service.
     * @param asyncProcess
     */
    public void fetchCurrencyParameter(boolean forsecallservice, Context context,
                                       final AsyncProcess asyncProcess){

        if(!forsecallservice && currencyParameter !=null){
            Log.d(CLASS_TAG,"Currency From RAM ");
            asyncProcess.Success(currencyParameter);

        }else{
            if(!forsecallservice && storageController.isRegisterCurrency()){
                currencyParameter = storageController.getCurrencyParameter();
                Log.d(CLASS_TAG,"Currency From Local Storage ");
                asyncProcess.Success(currencyParameter);

            }else{

                    endPoint= ApiUtils.getClientRetrofit();
                    Log.d(CLASS_TAG,  "Device From Service ");
                    endPoint.GetCurrencyParameter().enqueue(new Callback<CurrencyParameter>() {
                        @Override
                        public void onResponse(Call<CurrencyParameter> call, Response<CurrencyParameter> response) {
                            if(response.isSuccessful()) {
                                Log.i(CLASS_TAG, "Currency: " + new Gson().toJson(response.body()));
                                //save in RAM
                                setCurrencyParameter(response.body());
                                //save en Storage
                                storageController.saveCurrencyParameter(response.body());
                                asyncProcess.Success(response.body());

                            }else{
                                int code=response.code();
                                Log.i(CLASS_TAG, "Login code: " + code +"error body "+response.errorBody().toString());
                                asyncProcess.Error(null);
                            }
                        }

                        @Override
                        public void onFailure(Call<CurrencyParameter> call, Throwable t) {
                            Log.e(CLASS_TAG,"onFailure: "+t.getMessage());
                            asyncProcess.Error(null);
                        }
                    });
            }
        }

    }


    public void setCalculateResult(ContentCurrency currency) {
        this.calculateResult=currency;
    }

    public ContentCurrency getCalculateResult() {
        return calculateResult;
    }
    public Double getMoneyInput() {
        return moneyInput;
    }

    public void setMoneyInput(Double money_input) {
        this.moneyInput = money_input;
    }

    public void initialize(JSONObject dataReceive) {
        ContentCurrency currency=new ContentCurrency();
        try {

            currency.setBTC(dataReceive.getString("btc"));
            currency.setETH(dataReceive.getString("eth"));
            currency.setPTR(dataReceive.getString("ptr"));
            currency.setUSD(dataReceive.getString("usd"));
            currency.setEUR(dataReceive.getString("eur"));
            currency.setBS(dataReceive.getString("bs"));
            setMoneyInput(dataReceive.getDouble("amount"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        setCalculateResult(currency);



    }
}
