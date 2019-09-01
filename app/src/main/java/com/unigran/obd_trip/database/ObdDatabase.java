package com.unigran.obd_trip.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.unigran.obd_trip.database.dao.TrajetoDAO;
import com.unigran.obd_trip.database.dao.VeiculoDAO;
import com.unigran.obd_trip.model.Trajeto;
import com.unigran.obd_trip.model.Veiculo;

@Database(entities = {Trajeto.class, Veiculo.class}, version = 2, exportSchema = false)
public abstract class ObdDatabase extends RoomDatabase {
    static ObdDatabase instance;

    //envia instancia do DB
    public static ObdDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ObdDatabase.class, "obd.db")
                    .fallbackToDestructiveMigration() // limpar banco
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract TrajetoDAO getTrajetoDAO();
    public abstract VeiculoDAO getVeiculoDAO();
}
