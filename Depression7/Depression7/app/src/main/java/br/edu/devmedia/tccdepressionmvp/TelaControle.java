package br.edu.devmedia.tccdepressionmvp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import model.HumorDiario;
import model.Usuario;

public class TelaControle extends AppCompatActivity {
    private TextView tvLogout;
    private Button btnCadastrar;
    private ImageButton ivChat, ivNotas, ivInforme, ivControle, ivHome, ivLinks, ivAdm;
    private FirebaseAuth firebaseAuth;
    //esta lista contém todos os checkbox, para nao precisar criar 22 variaveis
    private List<CheckBox> listCB;
    //este é o linearlayout que contém todos os checkbox
    private LinearLayout llListCheckBox;

    private FirebaseFirestore firebaseFirestore;
    CollectionReference colectionUsuarios;
    DocumentReference usuarioDocumentReference;
    private FirebaseUser user;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_controle);
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        usuarioDocumentReference = firebaseFirestore.collection("usuarios").document(user.getUid());

        inicializaComponentes();
        eventoClicksMenu();
    }

    private void inicializaComponentes() {
        //lista que contem os checkbox inicializada
        listCB = new ArrayList<CheckBox>();
        btnCadastrar = (Button) findViewById(R.id.btConfirmar);
      //  btnCadastrar.setVisibility(View.INVISIBLE);
        tvLogout = (TextView) findViewById(R.id.tvLogout);
        ivChat = (ImageButton) findViewById(R.id.ivChat);
        ivNotas = (ImageButton) findViewById(R.id.ivNotas);
        ivInforme = (ImageButton) findViewById(R.id.ivInforme);
        ivHome = (ImageButton) findViewById(R.id.ivHome);
        ivControle = (ImageButton) findViewById(R.id.ivControle);
        ivLinks = (ImageButton) findViewById(R.id.ivLinks);
        ivAdm = (ImageButton) findViewById(R.id.ivAdm);
        llListCheckBox = findViewById(R.id.llLisCheckBox);
        for(int i = 0; i < llListCheckBox.getChildCount(); i++) {
            View v = llListCheckBox.getChildAt(i);
            if(v instanceof CheckBox) {
                //adiciona os checkbox na lista
                listCB.add((CheckBox)v);
            }
        }

    }

    private void eventoClicksMenu() {

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
                                    btnCadastrar.setVisibility(View.VISIBLE);
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
//metodo que verifica quais os checkbox estão selecionados e os armazena em uma lista
    private List<Integer> getCheckedCheckboxes(){
        List<Integer> listCheckBoxHumor = new ArrayList<Integer>();
        for(int i =0; i<listCB.size(); i++){
            if(listCB.get(i).isChecked()){
                listCheckBoxHumor.add(i);
            }
        }

        return listCheckBoxHumor;
    }

    public void confirmar(View v){
        List<Integer> listCheckedBoxes = getCheckedCheckboxes();

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new java.util.Date());

        HumorDiario hd = new HumorDiario();
        hd.setData(currentDate);
        hd.setListHumores(listCheckedBoxes);
        CollectionReference humorDiarioCollectionReference =usuarioDocumentReference.collection("HumorDiario");

        humorDiarioCollectionReference.add(hd).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                for(int i =0; i<listCB.size(); i++){
                    listCB.get(i).setChecked(false);
                }
                Toast.makeText(TelaControle.this, "Pesquisa salva com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

