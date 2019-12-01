package br.edu.devmedia.tccdepressionmvp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class TelaEsqSenha extends AppCompatActivity {
    private EditText edEmail;
    private Button btVoltar, btConfirmar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_esqueci_senha);
        inicializarComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEsqSenha.this, TelaLogin.class);
                startActivity(intent);
            }
        });

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString().trim();
                eSenha(email);
            }
        });
    }

    private void eSenha(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(TelaEsqSenha.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            alert("Redefinir senha!");
                    }else{
                            alert("E-mail não encontrado!");
                        }
                    }
                });
    }

    private void alert(String s) {
        Toast.makeText(TelaEsqSenha.this, s, Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes() {
        btVoltar = (Button) findViewById(R.id.btVoltar);
        edEmail = (EditText) findViewById(R.id.edEmail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
