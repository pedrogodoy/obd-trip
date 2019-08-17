package com.unigran.obd_trip.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.unigran.obd_trip.R;
import com.unigran.obd_trip.model.Trajeto;
import com.unigran.obd_trip.model.Veiculo;
import com.unigran.obd_trip.ui.ListaVeiculosView;

public class ListaVeiculoActivity extends AppCompatActivity {
    private
    ListaVeiculosView listaVeiculosView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_veiculo);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        return super.onContextItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //listaVeiculosView.atualizaVeiculos();
    }

    private void configuraLista() {
        ListView listaDeVeiculos = findViewById(R.id.activity_lista_veiculos_listview);
        //listaVeiculosView.configuraAdapter(listaDeVeiculos);
        configuraListenerDeCliquePorItem(listaDeVeiculos);
        registerForContextMenu(listaDeVeiculos);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Veiculo alunoEscolhido = (Veiculo) adapterView.getItemAtPosition(posicao);

            }
        });
    }

}
