package com.unigran.obd_trip.retrofit;

import com.unigran.obd_trip.model.Veiculo;
import com.unigran.obd_trip.retrofit.service.VeiculoService;

import retrofit2.Retrofit;

public class ObdRetrofit {
    private final VeiculoService veiculoService;



    public ObdRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.15:8080/")
                .build();
        veiculoService = retrofit.create(VeiculoService.class);

    }

    public VeiculoService getProdutoService() {
        return veiculoService;
    }
}
