package br.edu.devmedia.tccdepressionmvp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class TelaControle extends AppCompatActivity {
    private TextView tvLogout;
    private ImageButton ivChat, ivNotas, ivInforme, ivControle, ivHome, ivLinks, ivAdm;
    private Button btEnviar;
    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firebaseFirestore;
    CollectionReference colectionUsuarios;
    DocumentReference usuarioDocumentReference;
    private FirebaseUser user;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_controle);

        inicializaComponentes();
        eventoClicksMenu();
    }

    private void inicializaComponentes() {
        tvLogout = (TextView) findViewById(R.id.tvLogout);
        ivChat = (ImageButton) findViewById(R.id.ivChat);
        ivNotas = (ImageButton) findViewById(R.id.ivNotas);
        ivInforme = (ImageButton) findViewById(R.id.ivInforme);
        ivHome = (ImageButton) findViewById(R.id.ivHome);
        ivControle = (ImageButton) findViewById(R.id.ivControle);
        ivLinks = (ImageButton) findViewById(R.id.ivLinks);
        ivAdm = (ImageButton) findViewById(R.id.ivAdm);
        btEnviar = (Button) findViewById(R.id.btEnviar);

    }

    private void eventoClicksMenu() {

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaControle.this, GraficoEntrou.class);
                startActivity(intent);
            }
        });

        ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaControle.this, TelaChat.class);
                startActivity(intent);
            }
        });

        ivNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaControle.this, TelaNotas.class);
                startActivity(intent);
            }
        });

        ivInforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaControle.this, TelaInformativo.class);
                startActivity(intent);
            }
        });

        ivControle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaControle.this, TelaControle.class);
                startActivity(intent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaControle.this, GraficoEntrou.class);
                startActivity(intent);

                ivLinks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TelaControle.this, TelaEnviarLinks.class);
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
                                        Intent intent = new Intent(TelaControle.this, telaLinks.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(TelaControle.this, "Você não é um administrador!", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(TelaControle.this, TelaLogin.class);
                        startActivity(intent);
                    }
                });

            }

        });
    }
}

