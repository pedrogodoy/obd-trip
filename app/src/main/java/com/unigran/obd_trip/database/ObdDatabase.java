package com.unigran.obd_trip.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.unigran.obd_trip.database.dao.TrajetoDAO;
import com.unigran.obd_trip.model.Trajeto;

@Database(entities = {Trajeto.class}, version = 1, exportSchema = false)
public abstract class ObdDatabase extends RoomDatabase {
    static ObdDatabase instance;

    //envia instancia do DB
    public static ObdDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ObdDatabase.class, "obd.db")
                    .fallbackToDestructiveMigration() // limpar banco
                    .build();
        }
        return instance;
    }

    public abstract TrajetoDAO getTrajetoDAO();
}
