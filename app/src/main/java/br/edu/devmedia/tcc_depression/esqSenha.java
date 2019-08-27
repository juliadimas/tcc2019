package br.edu.devmedia.tcc_depression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class esqSenha extends AppCompatActivity {
    Button bt_voltar;
    Button btConfirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esq_senha);

        bt_voltar = (Button) findViewById(R.id.bt_voltar);
        bt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(esqSenha.this, telaLogin.class);
                startActivity(intent);

                finish();
            }
        });

        btConfirmar = (Button) findViewById(R.id.btConfirmar);
        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(esqSenha.this, esqSenhaCodigo.class);
                startActivity(intent);

                finish();
            }
        });
    }
}
