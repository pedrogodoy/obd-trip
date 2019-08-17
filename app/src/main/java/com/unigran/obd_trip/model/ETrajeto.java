package com.unigran.obd_trip.model;

public class ETrajeto {
    private int id;
    private String placa;
    private float distancia;
    private String consumo;
    private String hora_inicio;
    private String hora_fim;
    private int velocidade_max;
    private String temperatura_motor;
    private String nome_motorista;

    public String getNome_motorista() {
        return nome_motorista;
    }

    public void setNome_motorista(String nome_motorista) {
        this.nome_motorista = nome_motorista;
    }

    @Override
    public String toString() {
        return "ETrajeto{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", distancia=" + distancia +
                ", consumo=" + consumo +
                ", hora_inicio='" + hora_inicio + '\'' +
                ", hora_fim='" + hora_fim + '\'' +
                ", velocidade_maxima=" + velocidade_max +
                ", temperatura_motor=" + temperatura_motor +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public String getConsumo() {
        return consumo;
    }

    public void setConsumo(String consumo) {
        this.consumo = consumo;
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

    public int getVelocidade_max() {
        return velocidade_max;
    }

    public void setVelocidade_max(int velocidade_max) {
        this.velocidade_max = velocidade_max;
    }

    public String getTemperatura_motor() {
        return temperatura_motor;
    }

    public void setTemperatura_motor(String temperatura_motor) {
        this.temperatura_motor = temperatura_motor;
    }
}
