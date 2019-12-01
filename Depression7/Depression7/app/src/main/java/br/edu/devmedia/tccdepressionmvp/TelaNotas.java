package br.edu.devmedia.tccdepressionmvp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import model.Usuario;

public class TelaNotas extends AppCompatActivity {

    private EditText etNotas;
    private Button bt_criar;
    private ImageButton ivChat, ivNotas, ivInforme, ivControle, ivHome, ivLinks, ivAdm;
    private ListView lvNotas;
    private TextView tvLogout;

    private FirebaseFirestore firebaseFirestore;
    CollectionReference colectionUsuarios;
    DocumentReference usuarioDocumentReference;
    private FirebaseUser user;
    Usuario usuario;

    private ArrayList<String> Notas = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_notas);

        inicializaComponentes();
        eventoClicksMenu();


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Notas);
        lvNotas.setAdapter(adapter);

        bt_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoNota = etNotas.getText().toString();
                if (textoNota.length() > 0) {
                    etNotas.setText("");
                    etNotas.findFocus();
                    Notas.add(textoNota);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        lvNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(TelaNotas.this);
                adb.setTitle("Nota");
                adb.setMessage("Você deseja apagar essa nota?");
                final int positionToRemove = i;
                adb.setNegativeButton("Não", null);
                adb.setNegativeButton("Sim", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Notas.remove(positionToRemove);
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
            }

        });
    }

    private void eventoClicksMenu() {

        ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaNotas.this, TelaChat.class);
                startActivity(intent);
            }
        });

        ivNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaNotas.this, TelaNotas.class);
                startActivity(intent);
            }
        });

        ivInforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaNotas.this, TelaInformativo.class);
                startActivity(intent);
            }
        });

        ivControle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaNotas.this, TelaControle.class);
                startActivity(intent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaNotas.this, GraficoEntrou.class);
                startActivity(intent);

                ivLinks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TelaNotas.this, TelaEnviarLinks.class);
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
                                        Intent intent = new Intent(TelaNotas.this, telaLinks.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(TelaNotas.this, "Você não é um administrador!", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(TelaNotas.this, TelaLogin.class);
                        startActivity(intent);
                    }
                });

            }

        });
    }

    private void inicializaComponentes() {
        etNotas = (EditText) findViewById(R.id.etNotas);
        bt_criar = (Button) findViewById(R.id.bt_criar);
        lvNotas = (ListView) findViewById(R.id.lvNotas);
        ivChat = (ImageButton) findViewById(R.id.ivChat);
        ivNotas = (ImageButton) findViewById(R.id.ivNotas);
        ivInforme = (ImageButton) findViewById(R.id.ivInforme);
        ivControle = (ImageButton) findViewById(R.id.ivControle);
        ivLinks = (ImageButton) findViewById(R.id.ivLinks);
        ivHome = (ImageButton) findViewById(R.id.ivHome);
        ivAdm = (ImageButton) findViewById(R.id.ivAdm);
        tvLogout = findViewById((R.id.tvLogout));


    }
}
