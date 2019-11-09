package br.edu.devmedia.tccdepressionmvp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import model.Link;

public class TelaInformativo extends AppCompatActivity {
    private ListView listViewLinks;
    private Button btVoltar;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference linksCollectionReference;
    private Query queryListLinks;
    ArrayList<Link> arrayListLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_informacoes);

        arrayListLinks = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        linksCollectionReference = firebaseFirestore.collection("links");//.whereEqualTo("verificado", true).orderBy("data");
        linksCollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                queryListLinks = linksCollectionReference.whereEqualTo("verificado", true);
                queryListLinks.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Link l = doc.toObject(Link.class);
                                arrayListLinks.add(l);
                            }
                            configListView();
                        }
                    }
                });


            }

        });

        btVoltar = (Button) findViewById(R.id.btVoltar);
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaInformativo.this, TelaEntrou.class);
                startActivity(intent);
            }
        });


    }
    private void configListView(){
        listViewLinks = findViewById(R.id.listLinks);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, arrayListLinks);
        listViewLinks.setAdapter(arrayAdapter);
        listViewLinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                browseTo(arrayListLinks.get(position).getUrl());
            }
        });
    }

    private void browseTo(String url){

        if (!url.startsWith("http://") && !url.startsWith("https://")){
            url = "http://" + url;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }




}
