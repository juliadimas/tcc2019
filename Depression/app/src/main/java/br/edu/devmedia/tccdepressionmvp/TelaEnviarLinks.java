package br.edu.devmedia.tccdepressionmvp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.sql.Date;

import model.Link;
import model.Usuario;

public class TelaEnviarLinks extends AppCompatActivity {
    private Button btVoltar, btEnviar;
    private EditText edtLink;
    private TextView tvLogout;
    private ImageButton ivChat, ivNotas, ivInforme, ivControle, ivHome, ivLinks, ivAdm;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference linksCollectionReference;

    private FirebaseAuth firebaseAuth;
    CollectionReference colectionUsuarios;
    DocumentReference usuarioDocumentReference;
    private FirebaseUser user;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_enviar_links);
        firebaseFirestore = FirebaseFirestore.getInstance();
        linksCollectionReference = firebaseFirestore.collection("links");
        inicializaComponentes();
        eventoClicks();
        eventoClicksMenu();
    }

    private void eventoClicksMenu() {

        ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEnviarLinks.this, TelaChat.class);
                startActivity(intent);
            }
        });

        ivNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEnviarLinks.this, TelaNotas.class);
                startActivity(intent);
            }
        });

        ivInforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEnviarLinks.this, TelaInformativo.class);
                startActivity(intent);
            }
        });

        ivControle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEnviarLinks.this, TelaControle.class);
                startActivity(intent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEnviarLinks.this, GraficoEntrou.class);
                startActivity(intent);

                ivLinks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TelaEnviarLinks.this, TelaEnviarLinks.class);
                        startActivity(intent);
                    }
                });

                ivAdm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        usuarioDocumentReference = firebaseFirestore.collection("usuarios").document(user.getUid());
                        usuarioDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful()) {
                                    usuario = task.getResult().toObject(Usuario.class);
                                    if (usuario.isAdmin()) {
                                        Intent intent = new Intent(TelaEnviarLinks.this, telaLinks.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(TelaEnviarLinks.this, "Você não é um administrador!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });


                    }
                });

                tvLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Conexao.logout();
                        Intent intent = new Intent(TelaEnviarLinks.this, TelaLogin.class);
                        startActivity(intent);
                    }
                });

            }

        });
    }

    private void eventoClicks() {
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEnviarLinks.this, TelaInformativo.class);
                startActivity(intent);
            }
        });

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linksCollectionReference.add(montarLink()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(getApplicationContext(), "Link enviado para avaliação!", Toast.LENGTH_SHORT).show();
                        edtLink.setText("");
                        task.getResult().update("uid",task.getResult().getId());
                        Log.e("chave do documento==>",task.getResult().getId());
                    }
                });
            }
        });
    }

    private void inicializaComponentes() {
        btVoltar = (Button) findViewById(R.id.btVoltar);
        btEnviar = (Button) findViewById(R.id.btEnviar);
        edtLink = findViewById(R.id.edt_link);
        tvLogout = (TextView) findViewById(R.id.tvLogout);
        ivChat = (ImageButton) findViewById(R.id.ivChat);
        ivNotas = (ImageButton) findViewById(R.id.ivNotas);
        ivInforme = (ImageButton) findViewById(R.id.ivInforme);
        ivHome = (ImageButton) findViewById(R.id.ivHome);
        ivControle = (ImageButton) findViewById(R.id.ivControle);
        ivLinks = (ImageButton) findViewById(R.id.ivLinks);
        ivAdm = (ImageButton) findViewById(R.id.ivAdm);
    }
    private Link montarLink(){
        Link link = new Link();
        link.setUrl(edtLink.getText().toString());
        java.util.Date dataUtil = new java.util.Date();
        Date dataSql = new java.sql.Date(dataUtil.getTime());
        link.setData(dataSql);
        return link;
    }
}
