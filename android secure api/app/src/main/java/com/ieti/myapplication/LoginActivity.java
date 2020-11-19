package com.ieti.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private AuthService authService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    public static final String TOKEN = "TOKEN";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .build();**/


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") //localhost for emulator
                //.client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        authService = retrofit.create(AuthService.class);
    }

    public void onLoginClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editUser = (EditText) findViewById(R.id.editTextTextEmailAddress);
        final String user = editUser.getText().toString();
        EditText editPassword = (EditText) findViewById(R.id.editTextTextPassword);
        final String password = editPassword.getText().toString().trim();
        LoginWrapper userData = new LoginWrapper(user, password);
        if (user.equalsIgnoreCase("")) {
            editUser.setError("Ingresa tu usuario");
        }
        if (password.equalsIgnoreCase("")) {
            editPassword.setError("Ingresa tu contraseña");
        }
        if (!user.matches("") && !password.matches("")) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String token = authService.login(userData).execute().body().getAccessToken();
                        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(TOKEN, token);
                        editor.commit();
                        startActivity(intent);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }



}
