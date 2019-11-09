package br.edu.devmedia.tccdepressionmvp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Date;

import model.Link;

public class TelaEnviarLinks extends AppCompatActivity {
    private Button btVoltar, btEnviar;
    private EditText edtLink;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference linksCollectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_informativo);
        firebaseFirestore = FirebaseFirestore.getInstance();
        linksCollectionReference = firebaseFirestore.collection("links");
        inicializaComponentes();
        eventoClicks();
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
