package com.unigran.obd_trip.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.unigran.obd_trip.asynctask.BuscaVeiculoTask;
import com.unigran.obd_trip.database.ObdDatabase;
import com.unigran.obd_trip.database.dao.VeiculoDAO;
import com.unigran.obd_trip.ui.adapter.ListaVeiculosAdapter;

public class ListaVeiculosView {
    private final ListaVeiculosAdapter adapter;
    private final VeiculoDAO dao;
    private final Context context;

    public ListaVeiculosView(Context context) {
        this.context = context;
        this.adapter = new ListaVeiculosAdapter(this.context);
        //cria o objeto DAO do banco de dados e torna acessivel na view
        dao = ObdDatabase.getInstance(context).getVeiculoDAO();

    }


    public void atualizaVeiculos() {
        new BuscaVeiculoTask(dao, adapter).execute();

    }


    public void configuraAdapter(ListView listaDeVeiculos) {
        listaDeVeiculos.setAdapter(adapter);
    }

}
