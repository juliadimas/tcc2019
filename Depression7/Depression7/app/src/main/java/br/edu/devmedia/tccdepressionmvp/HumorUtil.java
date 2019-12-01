package br.edu.devmedia.tccdepressionmvp;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

import model.HumorDiario;

/**
 * Created by feliz on 24/11/2019.
 */

public class HumorUtil {
    private static Map<Integer, String> mapaHumores;
    //é populado com o numero de vezes que cada humor aparece em determinado dia
    public static Map<Integer, Integer> mapaHumoresGrafico;
    private final List<HumorDiario> listHD;

    private List<HumorDiario> listHumorDiario;
    FirebaseFirestore firebaseFirestore;

    Query query;

    public HumorUtil() {
        listHD = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mapaHumoresGrafico =new HashMap<Integer, Integer>();

        this.mapaHumores = new HashMap<Integer, String>(22);
        mapaHumores.put(0,"Tristeza");
        mapaHumores.put(1,"Pessimismo");
        mapaHumores.put(2,"Fracasso passado");
        mapaHumores.put(3,"Perda de prazer");
        mapaHumores.put(4,"Sentimento de culpa");
        mapaHumores.put(5,"Sentimentos de punição");
        mapaHumores.put(6,"Auto-estima");
        mapaHumores.put(7,"Auto Crítica");
        mapaHumores.put(8,"Autocrítica");
        mapaHumores.put(9,"Pensamento ou desejos suicidas");
        mapaHumores.put(10,"Choro");
        mapaHumores.put(11,"Agitação");
        mapaHumores.put(12,"Perda de interesse");
        mapaHumores.put(13,"Indecisão");
        mapaHumores.put(14,"Desvalorização");
        mapaHumores.put(15,"Falta de Energia");
        mapaHumores.put(16,"Auteraçõoes no padrão de sono");
        mapaHumores.put(17,"Irritabilidade");
        mapaHumores.put(18,"Alterações do apetite");
        mapaHumores.put(19,"Dificuldade de Concentração");
        mapaHumores.put(20,"Cansaço ou fadiga");
        mapaHumores.put(21,"Perda de interesse sexual");
    }
    public void loadListHumorDiario(){

        String dataAtual = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new java.util.Date());


        query = firebaseFirestore.collection("usuarios").
                document(Conexao.getFirebaseUser().getUid()).collection("HumorDiario").
                whereEqualTo("data", dataAtual);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> listHumoresDiarios= task.getResult().getDocuments();
                for (DocumentSnapshot ds :listHumoresDiarios) {
                    listHD.add(ds.toObject(HumorDiario.class));

                }
                //return listHD;
                getDadosGrafico();
            }
        });
        //collectionHumorDiario.



    }
    public List<HumorDiario> getListHD(){
        return this.listHD;
    }

    public void getDadosGrafico(){
        List<Integer> listhd;
        mapaHumoresGrafico = new HashMap<>();
        int somaHumor=0;
        for (HumorDiario hu : listHD){
            listhd = hu.getListHumores();
            for (int i : listhd){
                if(!mapaHumoresGrafico.containsKey(i)){
                     mapaHumoresGrafico.put(i,1);
                }else{
                    somaHumor = mapaHumoresGrafico.get(i)+1;
                    mapaHumoresGrafico.put(i,somaHumor);
                }
            }

        }

    }
    public String getNomeSentimento(int i){
        return this.mapaHumores.get(i);
    }


}
