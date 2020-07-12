package com.android.demoevaluationchichin.network;

import com.android.demoevaluationchichin.model.CurrencyParameter;
import com.android.demoevaluationchichin.model.LoginParameter;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EndPoint {

    @Headers({
            "Content-Type: application/json"
    })


    @POST("/user/login")
    Call<LoginParameter> PostLoginParameter(@Body JsonObject body);

    @GET("/currency")
    Call<CurrencyParameter> GetCurrencyParameter();

}
