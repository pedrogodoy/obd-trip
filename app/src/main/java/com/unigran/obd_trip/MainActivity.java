package com.unigran.obd_trip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.unigran.obd_trip.database.ObdDatabase;
import com.unigran.obd_trip.database.dao.TrajetoDAO;
import com.unigran.obd_trip.database.dao.VeiculoDAO;
import com.unigran.obd_trip.model.Login;
import com.unigran.obd_trip.model.Trajeto;
import com.unigran.obd_trip.model.Veiculo;
import com.unigran.obd_trip.retrofit.ObdRetrofit;
import com.unigran.obd_trip.retrofit.service.TrajetoService;
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
    TrajetoDAO daoTrajeto;
    private VeiculoService service;
    private TrajetoService trajetoService;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = findViewById(R.id.edtUsuario);
        senha = findViewById(R.id.edtSenha);
        //Instancia o banco de dados
        ObdDatabase database = ObdDatabase.getInstance(this);
        dao = database.getVeiculoDAO();
        service = new ObdRetrofit().getVeiculoService();
        daoTrajeto = database.getTrajetoDAO();
        trajetoService =  new ObdRetrofit().getTrajetoService();
        intent = new Intent(this,  ListaVeiculoActivity.class);
    }


    public void btn_obd(View view){
        loginOnAPI();
    }

    public void btn_trajeto(View view){
        salvaOnAPI();
    }

    //Funçao responsável por realizar a autenticação via API
    private void loginOnAPI() {
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
                            finish();
                        }

                        Toast.makeText(getApplicationContext(), "Logado OK!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);  //Inicia a activity dos veículos
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

    //Funçao responsável por salvar o trajeto na API
    private void salvaOnAPI() {
        //buscando o ultimo trajeto realizado
        Trajeto trajeto = daoTrajeto.buscaUltimoTrajeto();

        Call<Trajeto> call = trajetoService.enviaTrajeto(trajeto); //

        call.enqueue(new Callback<Trajeto>() {
            @Override
            public void onResponse(Call<Trajeto> call, Response<Trajeto> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        Toast.makeText(MainActivity.this, "Trajeto Enviado!", Toast.LENGTH_SHORT).show();
                        daoTrajeto.removeAll(); //Após os trajetos serem enviados para API, são apagados do banco local;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Não exitem trajetos para serem enviados!", Toast.LENGTH_SHORT).show();

                }
            }

            //Caso ocorra um erro ao enviar o trajeto
            @Override
            public void onFailure(Call<Trajeto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Verifique sua conexão!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }

        });
    }

}
