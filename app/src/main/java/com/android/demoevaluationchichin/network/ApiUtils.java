package com.android.demoevaluationchichin.network;

public class ApiUtils {
    public static final String BASE_URL = "http://chinchin.fsalinas34.com.ve";


    public static EndPoint getClientRetrofit(){
        return RetrofitClient.getClient(BASE_URL).create(EndPoint.class);
    }
}
