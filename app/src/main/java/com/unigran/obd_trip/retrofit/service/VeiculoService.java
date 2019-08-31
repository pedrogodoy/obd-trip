package com.unigran.obd_trip.retrofit.service;

import com.unigran.obd_trip.model.Login;
import com.unigran.obd_trip.model.Veiculo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/*
Interface responsável pela implementação das requisições que são feitas para o webservice
 */
public interface VeiculoService {

    @FormUrlEncoded
    @POST("auth/login")
    Call<List<Veiculo>> login(
            @Field("login") String login,
            @Field("senha") String senha
    );
}
