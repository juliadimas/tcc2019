package br.edu.devmedia.tccdepressionmvp;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import model.Usuario;

public class Cadastrar extends AppCompatActivity {
    private EditText cadastroNome, cadastroEmail, cadastroSenha;
    private Button btVoltar, btCadastrar;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference usuarioCollectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        inicializaComponentes();
        eventoClicks();
        firebaseFirestore = FirebaseFirestore.getInstance();
        usuarioCollectionReference = firebaseFirestore.collection("usuarios");
    }

    private void eventoClicks() {
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cadastrar.this, TelaLogin.class);
                startActivity(intent);
            }
        });


        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = cadastroEmail.getText().toString().trim();
                String senha = cadastroSenha.getText().toString().trim();
                String nome = cadastroNome.getText().toString().trim();
                if(validateForm())
                    criarConta(nome, email, senha);
            }

        });
    }

    private void criarConta(String nome,String email, String senha) {
        final Usuario usuario = new Usuario(null,nome, email, senha);
        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(Cadastrar.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //coloca o id do firebaseAuth no usuario
                            usuario.setUid(auth.getCurrentUser().getUid());
                            //cadastra o usuario no banco
                            usuarioCollectionReference.document(usuario.getUid()).set(usuario);
                            alert("Usuário cadastrado com sucesso!");

                            Intent intent = new Intent(Cadastrar.this, GraficoEntrou.class);
                            startActivity(intent);
                        }else{
                            Log.e("Cadastrar",  task.getException().getMessage());
                            task.getException().printStackTrace();
                            alert("Erro no cadastro!");
                        }
                    }
                });
    }



    private void alert(String msg){
        Toast.makeText(Cadastrar.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        cadastroNome = (EditText) findViewById(R.id.cadastroNome);
        cadastroEmail = (EditText) findViewById(R.id.cadastroEmail);
        cadastroSenha = (EditText) findViewById(R.id.cadastroSenha);
        btVoltar = (Button) findViewById(R.id.btVoltar);
        btCadastrar = (Button) findViewById(R.id.btConfirmar);
    }

    @Override
    protected void onStart(){
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
    private boolean validateForm() {
        boolean valid = true;
        if (TextUtils.isEmpty(cadastroNome.getText().toString())) {
            cadastroNome.setError("Obrigatório.");
            valid = false;
        } else {
            cadastroNome.setError(null);
        }

        if (TextUtils.isEmpty(cadastroEmail.getText().toString())) {
            cadastroEmail.setError("Obrigatório.");
            valid = false;
        } else {
            cadastroEmail.setError(null);
        }

        if (TextUtils.isEmpty(cadastroSenha.getText().toString())) {
            cadastroSenha.setError("Obrigatório.");
            valid = false;
        } else {
            cadastroSenha.setError(null);
        }
        return valid;
    }
}


