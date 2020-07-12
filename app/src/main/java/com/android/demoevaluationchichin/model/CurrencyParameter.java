package com.android.demoevaluationchichin.model;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.demoevaluationchichin.controller.AppController;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Currency;

public class CurrencyParameter {
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("content")
    @Expose
    private ContentCurrency content;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ContentCurrency getContent() {
        return content;
    }

    public void setContent(ContentCurrency content) {
        this.content = content;
    }

    @SuppressLint("DefaultLocale")
    public void calculate(Double amount, Context context){
        ContentCurrency currency=new ContentCurrency();
        currency.setBTC(String.format("%.12f",amount/Double.parseDouble(getContent().getBTC())));
        currency.setETH(String.format("%.12f",amount/Double.parseDouble(getContent().getETH())));
        currency.setPTR(String.format("%.12f",amount/Double.parseDouble(getContent().getPTR())));
        currency.setUSD(String.format("%.12f",amount/Double.parseDouble(getContent().getUSD())));
        currency.setEUR(String.format("%.12f",amount/Double.parseDouble(getContent().getEUR())));
        currency.setBS(String.format("%.12f", amount/Double.parseDouble(getContent().getBS())));
        AppController.getInstance(context).setCalculateResult(currency);
    }

}
