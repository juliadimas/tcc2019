package br.edu.devmedia.tccdepressionmvp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Date;
import java.text.SimpleDateFormat;

import model.Link;

public class AddLink extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference linksCollectionReference;
    EditText edtLink;
    Button btnEnviarLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_add_links);
        firebaseFirestore = FirebaseFirestore.getInstance();
        linksCollectionReference = firebaseFirestore.collection("links");
        edtLink = findViewById(R.id.id_link);
        edtLink.setEnabled(true);
        btnEnviarLink = findViewById(R.id.id_btn_enviar);
        btnEnviarLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linksCollectionReference.add(montarLink()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(getApplicationContext(), "Link enviado para avaliação!", Toast.LENGTH_SHORT).show();
                        edtLink.setText("");
                    }
                });
            }
        });
    }

    private Link montarLink(){
        Link link = new Link();

        if(URLUtil.isValidUrl(edtLink.getText().toString())) {

            link.setUrl(edtLink.getText().toString());
            java.util.Date dataUtil = new java.util.Date();
            Date dataSql = new java.sql.Date(dataUtil.getTime());
            link.setData(dataSql);

        }else {

        }
        return link;
    }
}
