package com.unigran.obd_trip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.unigran.obd_trip.database.ObdDatabase;
import com.unigran.obd_trip.database.dao.VeiculoDAO;
import com.unigran.obd_trip.model.Login;
import com.unigran.obd_trip.model.Veiculo;
import com.unigran.obd_trip.retrofit.ObdRetrofit;
import com.unigran.obd_trip.retrofit.service.VeiculoService;
import com.unigran.obd_trip.ui.activity.ListaVeiculoActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText usuario;
    EditText senha;
    VeiculoDAO dao;
    private VeiculoService service;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = findViewById(R.id.edtUsuario);
        senha = findViewById(R.id.edtSenha);
        ObdDatabase database = ObdDatabase.getInstance(this);
        dao = database.getVeiculoDAO();
        service = new ObdRetrofit().getVeiculoService();
        intent = new Intent(this,  ListaVeiculoActivity.class);
    }


    public void btn_obd(View view){

        //Insere veículo para teste do banco local
        /*Veiculo v = new Veiculo();
        v.setAno(2001);
        v.setCor("branco");
        v.setEmpresa_id(1);
        v.setMarca("volks");
        v.setModelo("parati");
        v.setPlaca("htq-1293");
        v.setUsuario_id(1); */



//      salvaVeiculo(v);
        salvaNaApi();


    }

    //função que faz a autenticação via API
    private void salvaNaApi() {

        Call<List<Veiculo>> call = service.login(usuario.getText().toString(), senha.getText().toString()); //

        call.enqueue(new Callback<List<Veiculo>>() {
            @Override
            public void onResponse(Call<List<Veiculo>> call, Response<List<Veiculo>> response) {
                if(response.isSuccessful()){
                    //Se o login estiver retornando os veículos, autentica.

                    if(response.body() != null){
                        dao.removeAll();  //Apaga os veículos existentes no banco
                        for(Veiculo veiculo: response.body()){
                            dao.salva(veiculo); //salva a nova lista de veículo
                        }

                        Toast.makeText(getApplicationContext(), "Logado OK!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);  //Inicia a activity dos veículos
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário ou senha inválida!", Toast.LENGTH_SHORT).show();

                }
            }

            //Caso a autenticação esteja inválida
            @Override
            public void onFailure(Call<List<Veiculo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Usuário ou senha inválida!", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "Falha de comunicação: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }

        });
    }
}
