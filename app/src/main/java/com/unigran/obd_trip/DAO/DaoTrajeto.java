package com.unigran.obd_trip.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.unigran.obd_trip.Entidade.ETrajeto;

public class DaoTrajeto extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private Banco banco;
    private static final String DATEBASE = "dbOBD";
    private static final int VERSION = 1;

    public DaoTrajeto(Context context){
        super(context, DATEBASE, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTrajeto ="CREATE TABLE IF NOT EXISTS trajeto(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "placa varchar(8) ," +
                "distancia float ," +
                "consumo float ," +
                "hora_inicio datetime," +
                "hora_fim datetime," +
                "velocidade_media float, " +
                "temperatura_motor float);"
                ;

        db.execSQL(sqlTrajeto);
    }

    public void abreConexao(){
        db = getWritableDatabase();
    }
    public void fechaConexao(){
        db.close();
    }

    public String salvarTrajeto(ETrajeto eTrajeto) {
        try{
            abreConexao();
            ContentValues values = new ContentValues();
            long resultado;
            values.put("distancia",eTrajeto.getDistancia());
            values.put("placa",eTrajeto.getPlaca());
            //values.put ("dataCad",eVeiculo.getDataCad().toString());
            resultado = db.insert("trajeto" , null, values);

            /*teste
            try {
                //db.execSQL("insert into carro (nome) values ('fiat')");
                db.execSQL("insert into carro (nome, placa) " +
                        "values ('" +eVeiculo.getNome()+ "'" + ",'" + eVeiculo.getPlaca()+      "')");

            } catch (Exception e) {
                e.printStackTrace();
            } */

            db.close();
            if (resultado ==-1) {
                return "Erro ao inserir registro";
            }
            else{
                return "Registro Inserido com sucesso";
            }


        }catch (Error e){
            System.out.println("erro" + e.getMessage());

        }

        return "Registro Inserido com sucesso";
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
