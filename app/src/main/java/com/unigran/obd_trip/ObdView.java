package com.unigran.obd_trip;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.sohrab.obd.reader.application.ObdPreferences;
import com.sohrab.obd.reader.obdCommand.ObdConfiguration;
import com.sohrab.obd.reader.service.ObdReaderService;
import com.sohrab.obd.reader.trip.TripRecord;
import com.unigran.obd_trip.database.ObdDatabase;
import com.unigran.obd_trip.database.dao.TrajetoDAO;
import com.unigran.obd_trip.database.dao.VeiculoDAO;
import com.unigran.obd_trip.model.ETrajeto;
import com.unigran.obd_trip.model.Trajeto;
import com.unigran.obd_trip.model.Veiculo;

import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;
import static com.sohrab.obd.reader.constants.DefineObdReader.ACTION_OBD_CONNECTION_STATUS;
import static com.sohrab.obd.reader.constants.DefineObdReader.ACTION_READ_OBD_REAL_TIME_DATA;

public class ObdView extends AppCompatActivity {
    private TextView mObdInfoTextView;
    private TextView viewDistancia;
    private TextView viewConsumo;
    private  TextView viewVelocidade;
    private  TextView viewTemp;
    private File diretorio;
    private String nomeDiretorio = "LOGOBD";
    private String diretorioApp;
    private  ETrajeto trajetoobj = new ETrajeto();
    private int id_veiculo;
    private Veiculo veiculo;
    private Trajeto trajeto;
    private boolean obdConectado = false;

    VeiculoDAO veiculoDao;
    TrajetoDAO trajetoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        setContentView(R.layout.activity_obd_view);
        mObdInfoTextView = findViewById(R.id.tv_obd_info);
        viewConsumo = findViewById(R.id.txtConsumo);
        viewDistancia = findViewById(R.id.txtDistancia);
        viewTemp = findViewById(R.id.txtTemp);
        viewVelocidade = findViewById(R.id.txtVelocidade);
        viewDistancia.setText("Distancia Percorrida: 0 KM");
        viewConsumo.setText("Combustível consumido: 0");
        viewVelocidade.setText("Velocidade: " + "76" + "KM/H");
        viewTemp.setText("Temperatura motor: " + "345 Cº");
        if(trajeto == null){
            trajeto = new Trajeto();
        }



        //Pega o ID do veiculo selecionado via bundle
        id_veiculo = getIntent().getIntExtra("veiculo", 4);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //lista de comandos
/*
        ArrayList<ObdCommand> obdCommands = new ArrayList<>();
        obdCommands.add(new SpeedCommand());
        obdCommands.add(new RPMCommand());
        ObdConfiguration.setmObdCommands(this, obdCommands); */

        // Passar null significa que estamos executando tod o comando OBD por enquanto,
        // mas você deve adicionar o comando requerido para recuperação rápida, como as linhas comentadas acima.
        ObdConfiguration.setmObdCommands(this, null);


        //Setar o preço da gasolina para o calculo
        float gasPrice = 4; // Valor por litro
        ObdPreferences.get(this).setGasPrice(gasPrice);
        /**
         * Registre o receptor com alguma ação relacionada ao status da conexão OBD
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_READ_OBD_REAL_TIME_DATA);
        intentFilter.addAction(ACTION_OBD_CONNECTION_STATUS);
        registerReceiver(mObdReaderReceiver, intentFilter);
        //iniciar o serviço que será executado em segundo plano para conectar e executar o comando até que você pare
        startService(new Intent(this, ObdReaderService.class));

        //instancia o veículo
        ObdDatabase database = ObdDatabase.getInstance(this);
        veiculoDao = database.getVeiculoDAO();
        trajetoDao = database.getTrajetoDAO();
        veiculo = veiculoDao.buscaVeiculoSelecionado(id_veiculo);

        //instancia o trajeto base
        criaTrajetoOffline();



    }


    public void btnSalva(View view){
        Toast.makeText(this, "Trajeto Finalizado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                //finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    /**
     * Receptor de Broadcast para receber status de conexão OBD e dados em tempo real
     */
    private final BroadcastReceiver mObdReaderReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            findViewById(R.id.progressBar).setVisibility(View.GONE);
            mObdInfoTextView.setVisibility(View.VISIBLE);
            String action = intent.getAction();

            if (action.equals(ACTION_OBD_CONNECTION_STATUS)) {

                String connectionStatusMsg = intent.getStringExtra(ObdReaderService.INTENT_OBD_EXTRA_DATA);
                mObdInfoTextView.setText(connectionStatusMsg);
                if (connectionStatusMsg.equals(getString(R.string.obd_connected))) {
                    //OBD conectado fazer o que quiser depois da conexão OBD
                    Toast.makeText(context, "OBD conectado, bom percurso!", Toast.LENGTH_SHORT).show();

                } else if (connectionStatusMsg.equals(getString(R.string.connect_lost))) {
                    Toast.makeText(context, "A conexão com o OBD foi perdida!", Toast.LENGTH_SHORT).show();
                } else {
                    //aqui você pode verificar a conexão OBD e o status de emparelhamento
                }

            } else if (action.equals(ACTION_READ_OBD_REAL_TIME_DATA)) {  //Caso o serviço retorne a constante REAL_TIME
                TripRecord tripRecord = TripRecord.getTripRecode(ObdView.this);
                // aqui você pode buscar dados em tempo real do TripRecord usando métodos getter.

                viewDistancia.setText("Distancia: " + tripRecord.getmDistanceTravel() +" KM");
                viewConsumo.setText("Consumo: " + tripRecord.getmDrivingFuelConsumption() + "Litros");
                viewVelocidade.setText("Velocidade: " + tripRecord.getSpeedMax().toString() + "KM/H");
                viewTemp.setText("Temperatura motor: " + tripRecord.getmEngineCoolantTemp());
                obdConectado = true;
                // Preencher objeto com os dados do veiculo
                try{
                    //Busca último trajeto do banco de dados
                    trajeto = trajetoDao.buscaUltimoTrajeto();
                    trajeto.setConsumo(tripRecord.getmFuelConsumptionRate());
                    trajeto.setDistancia_km(Float.toString(tripRecord.getmDistanceTravel()));
                    trajeto.setTemperatura_motor(tripRecord.getmEngineOilTemp());
                    trajeto.setVelocidade_maxima(tripRecord.getSpeedMax().toString());
                    //Atualiza no banco
                    trajetoDao.edita(trajeto);

                }catch (Exception err){
                    Toast.makeText(getApplicationContext(), "Erro ao salvar informações: " + err.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    //Cria as informações iniciais do trajeto
    public void criaTrajetoOffline(){
        trajeto.setConsumo("38");
        trajeto.setDistancia_km("47");
        trajeto.setHora_fim(new Date().toString());
        trajeto.setHora_inicio(new Date().toString());
        trajeto.setTemperatura_motor("218");
        trajeto.setVelocidade_maxima("89");
        trajeto.setEmpresa_id(veiculo.getEmpresa_id());
        trajeto.setUsuario_id(veiculo.getUsuario_id());
        trajeto.setVeiculo_id(veiculo.getId());
        //Atualiza o os TextViews
        viewDistancia.setText("Distancia Percorrida: " + trajeto.getDistancia_km());
        viewConsumo.setText("Combustível consumido: " + trajeto.getConsumo());
        viewVelocidade.setText("Velocidade: " + trajeto.getVelocidade_maxima() + "KM/H");
        viewTemp.setText("Temperatura motor: " + trajeto.getTemperatura_motor() + " Cº");
        //Salva as informações iniciais do trajeto no banco de dados
        trajetoDao.salva(trajeto);
    }

    //Método utilizado para fins de testes locais
    public void atualizaTrajetoOffline(){
        trajeto = trajetoDao.buscaUltimoTrajeto();

        trajeto.setConsumo("999");
        trajeto.setDistancia_km("9999");
        trajeto.setEmpresa_id(1);
        trajeto.setHora_fim(new Date().toString());
        trajeto.setHora_inicio(new Date().toString());
        trajeto.setTemperatura_motor("21812");
        trajeto.setVelocidade_maxima("89622");
        trajeto.setUsuario_id(1);
        trajeto.setVeiculo_id(1);



        trajetoDao.edita(trajeto);
        viewDistancia.setText("Distancia Percorrida: " + trajeto.getDistancia_km());
        viewConsumo.setText("Combustível consumido: " + trajeto.getConsumo());
        viewVelocidade.setText("Velocidade: " + trajeto.getVelocidade_maxima() + "KM/H");
        viewTemp.setText("Temperatura motor: " + trajeto.getTemperatura_motor() + " Cº");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregister receiver
        unregisterReceiver(mObdReaderReceiver);
        //stop service
        stopService(new Intent(this, ObdReaderService.class));
        // This will stop background thread if any running immediately.
        ObdPreferences.get(this).setServiceRunningStatus(false);
    }

}
