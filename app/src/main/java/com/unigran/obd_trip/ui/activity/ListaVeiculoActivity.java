package com.unigran.obd_trip.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.unigran.obd_trip.ObdView;
import com.unigran.obd_trip.R;
import com.unigran.obd_trip.model.Trajeto;
import com.unigran.obd_trip.model.Veiculo;
import com.unigran.obd_trip.ui.ListaVeiculosView;

import static com.unigran.obd_trip.ui.activity.ConstantesActivities.CHAVE_VEICULO;

public class ListaVeiculoActivity extends AppCompatActivity {
    private ListaVeiculosView listaVeiculosView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_veiculo);
        setTitle("Veículos");
        listaVeiculosView = new ListaVeiculosView(this);
        configuraLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
//        getMenuInflater()
//                .inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        return super.onContextItemSelected(item);
    }


    private void abreFormularioModoInsereVeiculo() {
//        startActivity(new Intent(this, FormularioVeiculoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaVeiculosView.atualizaVeiculos();
    }

    private void configuraLista() {
        ListView listaDeVeiculos = findViewById(R.id.activity_lista_veiculos_listview);
        listaVeiculosView.configuraAdapter(listaDeVeiculos);
        configuraListenerDeCliquePorItem(listaDeVeiculos);
        registerForContextMenu(listaDeVeiculos);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeVeiculos) {
        listaDeVeiculos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Veiculo veiculoEscolhido = (Veiculo) adapterView.getItemAtPosition(posicao);
                ListaVeiculoActivity.this.abreObdView(veiculoEscolhido);
            }
        });
    }

    //inicia a actitivty do trajeto
    private void abreObdView(Veiculo veiculo) {
        Intent vaiParaFormularioActivity = new Intent(ListaVeiculoActivity.this, ObdView.class);
        vaiParaFormularioActivity.putExtra(CHAVE_VEICULO, veiculo);
        startActivity(vaiParaFormularioActivity);
    }

}
