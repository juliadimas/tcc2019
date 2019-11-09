package br.edu.devmedia.tccdepressionmvp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import model.Usuario;

public class TelaEntrou extends AppCompatActivity {
    private TextView tv_bemVindo, tv_logout;
    private ImageButton ibt_chat, ibt_notas, ibt_info, ibt_contro, ibt_link,ibt_admin;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    CollectionReference colectionUsuarios;
    DocumentReference usuarioDocumentReference;
    private FirebaseUser user;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_entrou);
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseAuth = Conexao.getFirebaseAuth();
        user = firebaseAuth.getCurrentUser();

        if (user != null){
            colectionUsuarios = firebaseFirestore.collection("usuarios");
            Toast.makeText(getApplicationContext(), "Bem vindo " + user.getEmail() + "!", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent( this, TelaLogin.class);
            startActivity(intent);
            finish();
        }

        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
        tv_bemVindo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEntrou.this, TelaEnviarLinks.class);
                startActivity(intent);
            }
        });

        ibt_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEntrou.this, TelaChat.class);
                startActivity(intent);
            }
        });

        ibt_notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEntrou.this, TelaNotas.class);
                startActivity(intent);
            }
        });

        ibt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEntrou.this, TelaInformativo.class);
                startActivity(intent);
            }
        });

        ibt_contro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEntrou.this, TelaControle.class);
                startActivity(intent);
            }
        });
        ibt_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEntrou.this, TelaEnviarLinks.class);
                startActivity(intent);
            }
        });
        ibt_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioDocumentReference = firebaseFirestore.collection("usuarios").document(user.getUid());
                usuarioDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            usuario = task.getResult().toObject(Usuario.class);
                            if(usuario.isAdmin()) {
                                Intent intent = new Intent(TelaEntrou.this, telaLinks.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(TelaEntrou.this, "Você não é um administrador!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });


            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conexao.logout();
                Intent intent = new Intent(TelaEntrou.this, TelaLogin.class);
                startActivity(intent);
            }
        });

    }

    private void inicializaComponentes() {
        tv_bemVindo = (TextView) findViewById(R.id.tv_bemVindo);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        ibt_chat = (ImageButton) findViewById(R.id.ibt_chat);
        ibt_notas = (ImageButton) findViewById(R.id.ibt_notas);
        ibt_info = (ImageButton) findViewById(R.id.ibt_info);
        ibt_contro = (ImageButton) findViewById(R.id.ibt_contro);
        ibt_link = (ImageButton)findViewById(R.id.ibt_link);
        ibt_admin = (ImageButton)findViewById(R.id.ibt_admin);

    }
}
