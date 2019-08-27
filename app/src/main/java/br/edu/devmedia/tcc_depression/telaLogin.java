package br.edu.devmedia.tcc_depression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class telaLogin extends AppCompatActivity {
    TextView tv_esenha;
    TextView txCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        tv_esenha = (TextView) findViewById(R.id.tv_esenha);
        tv_esenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaLogin.this, esqSenha.class);
                startActivity(intent);

                finish();
            }
        });

        txCadastrar = (TextView) findViewById(R.id.txCadastrar);
        txCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaLogin.this, cadastrar.class);
                startActivity(intent);

                finish();
            }
        });
    }
}