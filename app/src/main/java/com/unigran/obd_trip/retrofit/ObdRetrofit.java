package com.unigran.obd_trip.retrofit;

import com.unigran.obd_trip.model.Veiculo;
import com.unigran.obd_trip.retrofit.service.VeiculoService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
Classe responsável pela comunicação com o WebService via Retrofit
 */
public class ObdRetrofit {
    private final VeiculoService veiculoService;
    public ObdRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.15:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        veiculoService = retrofit.create(VeiculoService.class);

    }

    public VeiculoService getVeiculoService() {
        return veiculoService;
    }
}
