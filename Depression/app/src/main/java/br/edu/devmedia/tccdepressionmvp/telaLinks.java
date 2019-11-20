package br.edu.devmedia.tccdepressionmvp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ListViewUtil.ListViewItemCheckboxBaseAdapter;
import ListViewUtil.ListViewItemDTO;
import model.Link;
import model.Usuario;

public class telaLinks extends AppCompatActivity {
    private TextView tvLogout;
    private ImageButton ivChat, ivNotas, ivInforme, ivControle, ivHome, ivLinks, ivAdm;
    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firebaseFirestore;
    CollectionReference colectionUsuarios;
    DocumentReference usuarioDocumentReference;
    private FirebaseUser user;
    Usuario usuario;
    private CollectionReference linksCollectionReference;
    ListViewItemDTO listViewItemDTO = new ListViewItemDTO();

    private String TAG = "telaLinks";
    ArrayList<Link> arrayListLinks = new ArrayList<>();
    ListView lvLinks;
    private Button btnSalvar;
    List<ListViewItemDTO> listDto;
    ListViewItemCheckboxBaseAdapter listViewDataAdapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_links);


        firebaseFirestore = FirebaseFirestore.getInstance();
        linksCollectionReference = firebaseFirestore.collection("links");
        lvLinks = findViewById(R.id.id_lv_links);
        popularListaLinks();
        //linksCollectionReference
        btnSalvar = findViewById(R.id.btnSalvarLinks);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =0; i < listDto.size();i++){

                    if(listDto.get(i).isChecked()){
                        arrayListLinks.get(i).setVerificado(true);
                        linksCollectionReference.document(arrayListLinks.get(i).getUid())
                                .update("verificado", true).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }

                }
            }
        });

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
                Intent intent = new Intent(telaLinks.this, TelaChat.class);
                startActivity(intent);
            }
        });

        ivNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaLinks.this, TelaNotas.class);
                startActivity(intent);
            }
        });

        ivInforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaLinks.this, TelaInformativo.class);
                startActivity(intent);
            }
        });

        ivControle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaLinks.this, TelaControle.class);
                startActivity(intent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(telaLinks.this, GraficoEntrou.class);
                startActivity(intent);

                ivLinks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(telaLinks.this, TelaEnviarLinks.class);
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
                                        Intent intent = new Intent(telaLinks.this, telaLinks.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(telaLinks.this, "Você não é um administrador!", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(telaLinks.this, TelaLogin.class);
                        startActivity(intent);
                    }
                });

            }

        });
    }

    private void popularListaLinks(){

       // ListViewItemDTO listViewItemDTO = new ListViewItemDTO();
        listDto = new ArrayList<>();
        Link link = new Link();
        linksCollectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Link link = document.toObject(Link.class);
                                if(!link.isVerificado()) {
                                    link = document.toObject(Link.class);
                                    arrayListLinks.add(link);
                                    listViewItemDTO.setChecked(false);
                                    listViewItemDTO.setItemText(link.getUrl());
                                    listDto.add(listViewItemDTO);
                                    listViewItemDTO = new ListViewItemDTO();
                                }

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                            listViewDataAdapter =
                                    new ListViewItemCheckboxBaseAdapter(getApplicationContext(), listDto);
                            prepararListView();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                       /* ArrayAdapter<Link> noticiasAdapter = new ArrayAdapter<>(getBaseContext(),
                                android.R.layout.simple_list_item_1, arrayListLinks);

                        lvLinks.setAdapter(noticiasAdapter);
                        lvLinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                browseTo(arrayListLinks.get(position).getUrl());
                            }
                        });*/

                    }
                });

    }

    public void browseTo(String url){

        if (!url.startsWith("http://") && !url.startsWith("https://")){
            url = "http://" + url;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void prepararListView() {
       // listViewDataAdapter.notifyDataSetChanged();

        // Set data adapter to list view.
        lvLinks.setAdapter(listViewDataAdapter);

        // When list view item is clicked.
        lvLinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                final int position = itemIndex;
                // Get user selected item.
                Object itemObject = adapterView.getAdapter().getItem(itemIndex);

                // Translate the selected item to DTO object.
                final ListViewItemDTO itemDto = (ListViewItemDTO) itemObject;

                // Get the checkbox.
                final CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.list_view_item_checkbox);
                TextView itemTextView = (TextView) view.findViewById(R.id.list_view_item_text);
                // Reverse the checkbox and clicked item check state.


                // Reverse the checkbox and clicked item check state.
                if (!itemDto.isChecked()) {
                    itemCheckbox.setChecked(true);
                    itemDto.setChecked(true);
                } else {
                    itemCheckbox.setChecked(false);
                    itemDto.setChecked(false);
                }
                itemTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        browseTo(arrayListLinks.get(position).getUrl());
                    }
                });


            }


        });
    }


}
