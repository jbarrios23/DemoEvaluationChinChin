package com.android.demoevaluationchichin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentCurrency {
    @SerializedName("ETH")
    @Expose
    private String eTH;
    @SerializedName("BTC")
    @Expose
    private String bTC;
    @SerializedName("PTR")
    @Expose
    private String pTR;
    @SerializedName("BS")
    @Expose
    private String bS;
    @SerializedName("EUR")
    @Expose
    private String eUR;
    @SerializedName("USD")
    @Expose
    private String uSD;

    public String getETH() {
        return eTH;
    }

    public void setETH(String eTH) {
        this.eTH = eTH;
    }

    public String getBTC() {
        return bTC;
    }

    public void setBTC(String bTC) {
        this.bTC = bTC;
    }

    public String getPTR() {
        return pTR;
    }

    public void setPTR(String pTR) {
        this.pTR = pTR;
    }

    public String getBS() {
        return bS;
    }

    public void setBS(String bS) {
        this.bS = bS;
    }

    public String getEUR() {
        return eUR;
    }

    public void setEUR(String eUR) {
        this.eUR = eUR;
    }

    public String getUSD() {
        return uSD;
    }

    public void setUSD(String uSD) {
        this.uSD = uSD;
    }
}
