package br.edu.devmedia.tccdepressionmvp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
//import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class GraficoEntrou extends AppCompatActivity {
    float itensGrafico[] = {20.0f, 20.0f, 20.0f, 20.0f, 20.0f};
    String descricao[] = {"Choro", "Auterações no padrão de sono", "Irritabilidade", "Alterações de apetite", "Cansaço ou fadiga"};
    PieChart grafico;
    private TextView tvLogout;
    private ImageButton ivChat, ivNotas, ivInforme, ivControle, ivHome, ivLinks, ivAdm;
    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firebaseFirestore;
    CollectionReference colectionUsuarios;
    DocumentReference usuarioDocumentReference;
    private FirebaseUser user;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_entrou);

        grafico = (PieChart) findViewById(R.id.graficoID);

        List<PieEntry> entradasGrafico = new ArrayList<>();

        for(int i = 0; i < itensGrafico.length; i++){
            entradasGrafico.add(new PieEntry(itensGrafico[i],descricao[i]));
        }

        PieDataSet dataSet = new PieDataSet (entradasGrafico,"Legenda do Grafico");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(dataSet);

        grafico.setData(pieData);

        grafico.invalidate();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = Conexao.getFirebaseAuth();
        user = firebaseAuth.getCurrentUser();

        /*if (user != null) {
            colectionUsuarios = firebaseFirestore.collection("usuarios");
            Toast.makeText(getApplicationContext(), "Bem vindo " + user.getEmail() + "!", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, TelaLogin.class);
            startActivity(intent);
            finish();
        }*/

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

    }

   private void eventoClicksMenu() {

       ivChat.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(GraficoEntrou.this, TelaChat.class);
               startActivity(intent);
           }
       });

       ivNotas.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(GraficoEntrou.this, TelaNotas.class);
               startActivity(intent);
           }
       });

       ivInforme.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(GraficoEntrou.this, TelaInformativo.class);
               startActivity(intent);
           }
       });

       ivControle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(GraficoEntrou.this, TelaControle.class);
               startActivity(intent);
           }
       });

       ivHome.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(GraficoEntrou.this, GraficoEntrou.class);
               startActivity(intent);

               ivLinks.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(GraficoEntrou.this, TelaEnviarLinks.class);
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
                                       Intent intent = new Intent(GraficoEntrou.this, telaLinks.class);
                                       startActivity(intent);
                                   } else {
                                       Toast.makeText(GraficoEntrou.this, "Você não é um administrador!", Toast.LENGTH_SHORT).show();
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
                       Intent intent = new Intent(GraficoEntrou.this, TelaLogin.class);
                       startActivity(intent);
                   }
               });

           }

       });
   }}

