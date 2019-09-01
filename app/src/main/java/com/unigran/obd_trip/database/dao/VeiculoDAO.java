package com.unigran.obd_trip.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.unigran.obd_trip.model.Trajeto;
import com.unigran.obd_trip.model.Veiculo;

import java.util.List;

@Dao
public interface VeiculoDAO {
    //métodos para o CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long salva(Veiculo veiculo);

    @Query("SELECT * FROM VEICULO")
    List<Veiculo> todos();

    @Query("SELECT * FROM VEICULO WHERE id = :id")
    Veiculo buscaVeiculoSelecionado(int id);

    @Delete
    void remove(Trajeto trajeto);

    @Query("DELETE FROM VEICULO")
    void removeAll();

    @Update
    void edita(Trajeto trajeto);

}
