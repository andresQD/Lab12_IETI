package com.ieti.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    public static final String TOKEN_KEY = "TOKEN_KEY";

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        SharedPreferences sharedPref = getSharedPreferences( getString( R.string.preference_file_key ), Context.MODE_PRIVATE );
        Intent inte;
        if(sharedPref.contains(TOKEN_KEY)){
            //TODO go to MainActivity
            inte = new Intent(this, MainActivity.class);
        }else{
            //TODO go to LoginActivity
            inte = new Intent (this, LoginActivity.class);
        }
        startActivity(inte);
        finish();
    }
}
