package com.unigran.obd_trip.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Trajeto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private int usuario_id;
    private int veiculo_id;
    private int empresa_id;
    private String hora_inicio;
    private String hora_fim;
    private String consumo;
    private String velocidade_maxima;
    private String temperatura_motor;
    private String distancia_km;

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getVeiculo_id() {
        return veiculo_id;
    }

    public void setVeiculo_id(int veiculo_id) {
        this.veiculo_id = veiculo_id;
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fim() {
        return hora_fim;
    }

    public void setHora_fim(String hora_fim) {
        this.hora_fim = hora_fim;
    }

    public String getConsumo() {
        return consumo;
    }

    public void setConsumo(String consumo) {
        this.consumo = consumo;
    }

    public String getVelocidade_maxima() {
        return velocidade_maxima;
    }

    public void setVelocidade_maxima(String velocidade_maxima) {
        this.velocidade_maxima = velocidade_maxima;
    }

    public String getTemperatura_motor() {
        return temperatura_motor;
    }

    public void setTemperatura_motor(String temperatura_motor) {
        this.temperatura_motor = temperatura_motor;
    }

    public String getDistancia_km() {
        return distancia_km;
    }

    public void setDistancia_km(String distancia_km) {
        this.distancia_km = distancia_km;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
