package com.unigran.obd_trip.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.unigran.obd_trip.model.Trajeto;

import java.util.List;

@Dao
public interface TrajetoDAO {
    //métodos para o CRUD
    @Insert
    Long salva(Trajeto trajeto);

    @Query("SELECT * FROM TRAJETO")
    List<Trajeto> todos();

    @Query("SELECT * FROM TRAJETO ORDER BY id")
    Trajeto buscaUltimoTrajeto();

    @Delete
    void remove(Trajeto trajeto);

    @Query("DELETE FROM TRAJETO")
    void removeAll();

    @Update
    void edita(Trajeto trajeto);
}
