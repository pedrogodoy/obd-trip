package com.unigran.obd_trip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.unigran.obd_trip.asynctask.SalvaVeiculoTask;
import com.unigran.obd_trip.database.ObdDatabase;
import com.unigran.obd_trip.database.dao.VeiculoDAO;
import com.unigran.obd_trip.model.Veiculo;
import com.unigran.obd_trip.ui.ListaVeiculosView;
import com.unigran.obd_trip.ui.activity.ListaVeiculoActivity;

public class MainActivity extends AppCompatActivity {

    EditText nome;
    VeiculoDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nome = findViewById(R.id.edtNome);
        ObdDatabase database = ObdDatabase.getInstance(this);
        dao = database.getVeiculoDAO();
    }


    public void btn_obd(View view){

        Bundle bundle = new Bundle();
        //bundle.putString("nomeMotorista", nome.getText().toString());
        Intent intent = new Intent(this,  ListaVeiculoActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

        //inserindo veículo de teste
        Veiculo v = new Veiculo();
        v.setAno(2001);
        v.setCor("branco");
        v.setEmpresa_id(1);
        v.setMarca("volks");
        v.setModelo("parati");
        v.setPlaca("htq-1293");
        v.setUsuario_id(1);

        salvaVeiculo(v);
        finish();

    }

    private void salvaVeiculo(Veiculo veiculo) {
        new SalvaVeiculoTask(dao, veiculo).execute();  //chama a tarefa que salva aluno e aguarda o listener por sucesso
    }
}
