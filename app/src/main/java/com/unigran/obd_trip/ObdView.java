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
import com.unigran.obd_trip.model.ETrajeto;

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
    private TextView mObdInfoConsole;
    private TextView viewDistancia;
    private TextView viewConsumo;
    private  TextView viewVelocidade;
    private  TextView viewTemp;
    private File diretorio;
    private String nomeDiretorio = "LOGOBD";
    private String diretorioApp;
    private  ETrajeto trajetoobj = new ETrajeto();

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
        //mObdInfoConsole = findViewById(R.id.textConsole);


        viewDistancia.setText("Distancia: " + "80" +" KM");
        viewConsumo.setText("Consumo: " + "15" + "Litros");
        viewVelocidade.setText("Velocidade: " + "76" + "KM/H");
        viewTemp.setText("Temperatura motor: " + "345 Cº");


        //pegar nome kdo motorista via bundle
        if(null != getIntent()){
            trajetoobj.setNome_motorista(getIntent().getStringExtra("nomeMotorista"));

        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

         /*
         adicione o comando required em arrayList e defina como ObdConfiguration.
         * Se você não definir nenhum comando ou passar null, então todos os comandos OBD serão solicitados.
         * Portanto, recomenda-se definir o comando que é necessário apenas como uma linha comentada.

          */

        //lista de comandos
/*
        ArrayList<ObdCommand> obdCommands = new ArrayList<>();
        obdCommands.add(new SpeedCommand());
        obdCommands.add(new RPMCommand());
        ObdConfiguration.setmObdCommands(this, obdCommands); */


        // Passar null significa que estamos executando tod o comando OBD por enquanto,
        // mas você deve adicionar o comando requerido para recuperação rápida, como as linhas comentadas acima.

        ObdConfiguration.setmObdCommands(this, null);


        // setar o preco da gasolina para o calculo . Default is 7 $/l
        float gasPrice = 7; // per litre, you should initialize according to your requirement.
        ObdPreferences.get(this).setGasPrice(gasPrice);
        /**
         * Registre o receptor com alguma ação relacionada ao status da conexão OBD
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_READ_OBD_REAL_TIME_DATA);
        intentFilter.addAction(ACTION_OBD_CONNECTION_STATUS);
        registerReceiver(mObdReaderReceiver, intentFilter);

        //start service which will execute in background for connecting and execute command until you stop
        //iniciar o serviço que será executado em segundo plano para conectar e executar o comando até que você pare

        startService(new Intent(this, ObdReaderService.class));




        //while(mObdInfoTextView.length() > 15){
        //    salvaLog();

        //}

    }


    public void btnSalva(View view){
        salvaBanco();
    }



    public void salvaBanco(){



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
     * Broadcast Receiver to receive OBD connection status and real time data
     *
     * Receptor de Broadcast para receber status de conexão OBD e dados em tempo real
     */
    private final BroadcastReceiver mObdReaderReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            findViewById(R.id.progressBar).setVisibility(View.GONE);
            mObdInfoTextView.setVisibility(View.VISIBLE);
            String action = intent.getAction();
            Toast.makeText(ObdView.this, action, Toast.LENGTH_SHORT).show();
            //salvaLog();

            if (action.equals(ACTION_OBD_CONNECTION_STATUS)) {

                String connectionStatusMsg = intent.getStringExtra(ObdReaderService.INTENT_OBD_EXTRA_DATA);
                mObdInfoTextView.setText(connectionStatusMsg);
                //Toast.makeText(ObdView.this, connectionStatusMsg, Toast.LENGTH_SHORT).show();

                if (connectionStatusMsg.equals(getString(R.string.obd_connected))) {
                    //OBD connected  do what want after OBD connection
                    //OBD conectado fazer o que quiser depois da conexão OBD

                   /* while(mObdInfoTextView != null){
                        salvaLog();
                    } */


                } else if (connectionStatusMsg.equals(getString(R.string.connect_lost))) {
                    //OBD disconnected  do what want after OBD disconnection
                } else {
                    //aqui você pode verificar a conexão OBD e o status de emparelhamento
                }

            } else if (action.equals(ACTION_READ_OBD_REAL_TIME_DATA)) {

                TripRecord tripRecord = TripRecord.getTripRecode(ObdView.this);
                //mObdInfoTextView.setText(tripRecord.toString());
                //salvaLog();

                // aqui você pode buscar dados em tempo real do TripRecord usando métodos getter como

                //mObdInfoConsole.setText(tripRecord.getSpeed().toString());

                viewDistancia.setText("Distancia: " + tripRecord.getmDistanceTravel() +" KM");
                viewConsumo.setText("Consumo: " + tripRecord.getmDrivingFuelConsumption() + "Litros");
                viewVelocidade.setText("Velocidade: " + tripRecord.getSpeedMax().toString() + "KM/H");

                viewTemp.setText("Temperatura motor: " + tripRecord.getmEngineCoolantTemp());

                // Preencher objeto com os dados do veiculo
                try{
                    trajetoobj.setDistancia(tripRecord.getmDistanceTravel()); //pega distancia percorrida
                    trajetoobj.setConsumo(tripRecord.getmFuelConsumptionRate()); //pega taxa de consumo de combustivel
                    trajetoobj.setHora_inicio( new Date().toString()); //pega a data de inicio do percurso
                    trajetoobj.setVelocidade_max(tripRecord.getSpeedMax());
                    trajetoobj.setTemperatura_motor(tripRecord.getmEngineOilTemp());
                }catch (Exception err){
                    Toast.makeText(getApplicationContext(), "Erro ao salvar no objeto: " + err.getMessage(), Toast.LENGTH_LONG).show();
                }





            }

        }
    };


    public void btnEnviaTrajeto(View view){
        makeJson();
        onDestroy();
        this.finish();
        Toast.makeText(getApplicationContext(), "Trajeto finalizado!", Toast.LENGTH_SHORT).show();
    }




    public void makeJson(){
        try{
            trajetoobj.setHora_fim(new Date().toString()); //pega a hora final do fim do trajeto

            //se nao conseguir pegar hora inicial, gravamos a do fim.
            if(trajetoobj.getHora_inicio() == null){
                trajetoobj.setHora_inicio(new Date().toString()); //pega a hora final do fim do trajeto
            }
            if(trajetoobj.getConsumo() == null || trajetoobj.getTemperatura_motor() == null){
                trajetoobj.setConsumo("null");
                trajetoobj.setTemperatura_motor("null");
            }

            JSONObject trajeto = new JSONObject();

            trajeto.put("distancia", trajetoobj.getDistancia());
            trajeto.put("consumo", trajetoobj.getConsumo());
            trajeto.put("horaInicio", trajetoobj.getHora_inicio());
            trajeto.put("horaFim", trajetoobj.getHora_fim());
            trajeto.put("velocidadeMaxima", trajetoobj.getVelocidade_max());
            trajeto.put("temperaturaMotor", trajetoobj.getTemperatura_motor());
            trajeto.put("motorista", trajetoobj.getNome_motorista());
            trajeto.put("veiculoId", 1);

            post(trajeto);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro ao enviar trajeto: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }



    public String post(final JSONObject data){
        try{
            final URL url = new URL("http://localhost:4000/trajeto/create");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            final OutputStream outputStream = connection.getOutputStream();
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            writer.write(data.toString());
            writer.flush();
            writer.close();
            outputStream.close();

            connection.connect();
            final InputStream stream = connection.getInputStream();
            return new Scanner(stream, "UTF-8").next();
        }catch (Exception e){
            Log.e("Errpr", "Erro 1", e);
        }

        return null;
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
