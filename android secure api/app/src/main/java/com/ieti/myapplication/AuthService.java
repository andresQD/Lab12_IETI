package com.ieti.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/auth")
    Call<Token> login(@Body LoginWrapper login);
}
