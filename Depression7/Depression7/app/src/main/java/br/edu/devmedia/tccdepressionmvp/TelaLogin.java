package br.edu.devmedia.tccdepressionmvp;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TelaLogin extends AppCompatActivity {
    private EditText edLogin, edSenha;
    private Button btConfirmar;
    private TextView txCadastrar, tvEsenha;
    private FirebaseAuth auth;


    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
        txCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogin.this, Cadastrar.class);
                startActivity(intent);
            }
        });


        tvEsenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogin.this, TelaEsqSenha.class);
                startActivity(intent);
            }
        });

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edLogin.getText().toString().trim();
                String senha = edSenha.getText().toString().trim();
                Login(email, senha);
            }
        });
    }

    private void Login(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(TelaLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(TelaLogin.this, GraficoEntrou.class);
                            startActivity(intent);
                        }
                        else{
                            alert ("E-mail ou senha invalidos!");
                        }

                    }
                });
    }

    private void alert(String s) {

        Toast.makeText(TelaLogin.this, s, Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        edLogin = (EditText) findViewById(R.id.edEmail);
        edSenha = (EditText) findViewById(R.id.edSenha);
        btConfirmar = (Button) findViewById(R.id.btConfirmar);
        tvEsenha = (TextView) findViewById(R.id.tvEsenha);
        txCadastrar = (TextView) findViewById(R.id.txCadastrar);

    }
}


