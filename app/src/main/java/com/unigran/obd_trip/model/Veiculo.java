package com.unigran.obd_trip.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Veiculo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
}
