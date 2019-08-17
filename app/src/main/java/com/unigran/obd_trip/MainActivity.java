package com.unigran.obd_trip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nome = findViewById(R.id.edtNome);
    }


    public void btn_obd(View view){

        Bundle bundle = new Bundle();
        bundle.putString("nomeMotorista", nome.getText().toString());
        Intent intent = new Intent(this, ObdView.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
