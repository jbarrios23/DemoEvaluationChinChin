package com.android.demoevaluationchichin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.demoevaluationchichin.controller.AppController;
import com.android.demoevaluationchichin.controller.StorageController;
import com.android.demoevaluationchichin.model.CurrencyParameter;
import com.android.demoevaluationchichin.model.LoginParameter;
import com.android.demoevaluationchichin.network.ApiUtils;
import com.android.demoevaluationchichin.network.AsyncProcess;
import com.android.demoevaluationchichin.network.EndPoint;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static String CLASS_TAG=LoginActivity.class.getSimpleName();
    public EditText editText_user;
    public EditText editText_pass;
    public String userName;
    public String passWord;
    public String passWordEncode;
    public String userNameProv="jb23";
    public String passWordProv="1276";
    public Button button_login;
    public EndPoint endPoint;

    public AppController appController;
    public StorageController storageController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_user=findViewById(R.id.edit_text_user_login);
        editText_pass=findViewById(R.id.edit_text_pass_login);
        button_login=findViewById(R.id.button_login);
        appController=AppController.getInstance(this);
        storageController=StorageController.getInstance(this);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=editText_user.getText().toString();
                passWord=editText_pass.getText().toString();
                if(!userName.equals("")&&!passWord.equals("")){
                    passWordEncode=getSHAKeyPass(passWord);
                    verifyNamePass(userName,passWordEncode);
                }else{
                    Toast.makeText(getApplicationContext(),"User name o password empty",Toast.LENGTH_LONG).show();
                }
            }
        });



    }
    /**
     * Method Verify User
     @param userName: Name user.
     @param passWordEncode : pass encode SHA512
     */
    private void verifyNamePass(String userName, String passWordEncode) {

        JsonObject jsonObject = new JsonObject();
        final JSONObject requestBody=new JSONObject();
        try {
            requestBody.put("account",userName);
            requestBody.put("passphrase",passWordEncode);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser=new JsonParser();
        jsonObject=(JsonObject) jsonParser.parse(requestBody.toString());
        Log.i(CLASS_TAG, "Body: " + jsonObject.toString());
        endPoint= ApiUtils.getClientRetrofit();
        endPoint.PostLoginParameter(jsonObject).enqueue(new Callback<LoginParameter>() {
            @Override
            public void onResponse(Call<LoginParameter> call, Response<LoginParameter> response) {
                if(response.isSuccessful()) {
                    Log.i(CLASS_TAG, "Login Post User: " + new Gson().toJson(response.body()));
                    //save in RAM
                    appController.setLoginParameter(response.body());
                    appController.setFullName(response.body().getContent().getFullName());
                    appController.setToken(response.body().getContent().getToken());
                    //save in Storage
                    storageController.saveLoginParameter(response.body());
                    storageController.saveDateTime(getDateTime());
                    storageController.saveNameUser(response.body().getContent().getFullName());
                    storageController.saveToken(response.body().getContent().getToken());
                    appController.fetchCurrencyParameter(false,getApplicationContext(), new AsyncProcess() {
                        @Override
                        public void Success(Object o) {
                            goToMainActivity();
                        }

                        @Override
                        public void Error(Object o) {
                            Log.e(CLASS_TAG, "error : ");
                        }
                    });



                }else{
                    int code=response.code();
                    Log.i(CLASS_TAG, "Login code: " + code +"error body "+response.errorBody().toString());
                    Toast.makeText(getApplicationContext(),"User name o password incorrect",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<LoginParameter> call, Throwable t) {
                Log.e(CLASS_TAG,"onFailure: "+t.getMessage());
            }
        });

    }
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }




    public String getSHAKeyPass(String pass){
        MessageDigest md = null;
        StringBuilder sb = new StringBuilder();
        try {
            md = MessageDigest.getInstance("SHA-512");
            byte[] digest = md.digest(pass.getBytes());
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(CLASS_TAG,"NoSuchAlgorithmException "+e.getMessage());
        }
        return sb.toString();

    }

    private void goToMainActivity() {

        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }
}