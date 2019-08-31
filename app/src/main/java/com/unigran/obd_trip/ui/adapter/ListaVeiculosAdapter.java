package com.unigran.obd_trip.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unigran.obd_trip.R;
import com.unigran.obd_trip.database.ObdDatabase;
import com.unigran.obd_trip.database.dao.VeiculoDAO;
import com.unigran.obd_trip.model.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class ListaVeiculosAdapter extends BaseAdapter {
    private final List<Veiculo> veiculos = new ArrayList<>();
    private final Context context;
    private final VeiculoDAO dao;

    public ListaVeiculosAdapter(Context context) {
        this.context = context;
        dao = ObdDatabase.getInstance(context).getVeiculoDAO(); //Instanciando o BD
    }

    @Override
    public int getCount() {
        return veiculos.size();
    }

    @Override
    public Veiculo getItem(int posicao) {
        return veiculos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return veiculos.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View viewCriada = criaView(viewGroup);
        Veiculo veiculoDevolvido = veiculos.get(posicao);
        vincula(viewCriada, veiculoDevolvido);
        return viewCriada;
    }

    private void vincula(View view, Veiculo veiculo) {
        TextView nome = view.findViewById(R.id.item_veiculo_nome);
        nome.setText(veiculo.getModelo()); //inseri o nome no textview
        TextView placa = view.findViewById(R.id.item_veiculo_placa);
        placa.setText(veiculo.getPlaca());

    }

    private View criaView(ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_veiculo, viewGroup, false);
    }

    public void atualiza(List<Veiculo> veiculos){
        this.veiculos.clear();
        this.veiculos.addAll(veiculos);
        notifyDataSetChanged();
    }

}
