package br.edu.devmedia.tcc_depression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class esqSenhaNew extends AppCompatActivity {
    Button btVolta;
    Button btConfirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esq_senha_new);

        btVolta = (Button) findViewById(R.id.btvolta);
        btVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(esqSenhaNew.this, esqSenhaCodigo.class);
                startActivity(intent);

                finish();
            }
        });

        btConfirmar = (Button) findViewById(R.id.btConfirmar);
        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(esqSenhaNew.this, telaLogin.class);
                startActivity(intent);

                finish();
            }
        });
    }
}
