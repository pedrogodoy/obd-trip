package com.unigran.obd_trip.asynctask;

import android.os.AsyncTask;

import com.unigran.obd_trip.database.dao.VeiculoDAO;
import com.unigran.obd_trip.model.Veiculo;

public class SalvaVeiculoTask extends AsyncTask<Void, Void, Void> {
    private final VeiculoDAO veiculoDAO;
    private final Veiculo veiculo;
//    private final QuandoAlunoSalvoListener listener;

    public SalvaVeiculoTask(VeiculoDAO veiculoDAO, Veiculo veiculo){
        this.veiculoDAO = veiculoDAO;
        this.veiculo = veiculo;
//        this.listener = listener;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        //Salva
        //int alunoId = veiculoDAO.salva(veiculo).intValue();
        veiculoDAO.salva(veiculo);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
//        listener.quandoSalvo();     //aguarda a resposta do listener
    }



    public interface QuandoAlunoSalvoListener {
        void quandoSalvo();
    }
}