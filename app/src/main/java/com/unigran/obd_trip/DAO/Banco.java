package com.unigran.obd_trip.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

    public static final String TTrajeto = "trajeto";

    public Banco(Context context) {
        super(context, "dbOBD", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlTrajeto ="CREATE TABLE IF NOT EXISTS "+TTrajeto+"(" +
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
