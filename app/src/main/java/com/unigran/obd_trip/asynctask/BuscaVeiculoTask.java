package com.unigran.obd_trip.asynctask;

import android.os.AsyncTask;

import com.unigran.obd_trip.database.dao.VeiculoDAO;
import com.unigran.obd_trip.model.Veiculo;
import com.unigran.obd_trip.ui.adapter.ListaVeiculosAdapter;

import java.util.List;

public class BuscaVeiculoTask extends AsyncTask<Void, Void, List<Veiculo>> {
    private final VeiculoDAO dao;
    private final ListaVeiculosAdapter adapter;

    public BuscaVeiculoTask(VeiculoDAO dao, ListaVeiculosAdapter adapter) {
        this.dao = dao;
        this.adapter = adapter;

    }

    @Override
    protected List<Veiculo> doInBackground(Void[] objects) {    //realizar a operacao com o banco em background
        return dao.todos();
    }

    @Override
    protected void onPostExecute(List<Veiculo> todosVeiculos) {  //aguarda a resposta do doinbackground para executar
        super.onPostExecute(todosVeiculos);
        adapter.atualiza(todosVeiculos);
    }
}
