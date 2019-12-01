package br.edu.devmedia.tccdepressionmvp;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import model.Usuario;


public class TelaChat extends AppCompatActivity {
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private static int SIGN_IN_REQUEST_CODE = 1;
    private MessageAdapter adapter;
    RelativeLayout activity_tela_chat;
    FloatingActionButton fab;
    EditText input;
    ListView listViewMessages;
    private FirebaseDatabase mFirebaseDatabase;
    //esta variavel faz referencia a uma parte especifica do banco de dados. no caso a parte das mensagens
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseFirestore firestore;
    private FirebaseAuth mFirebaseAuth;
    private List<ChatMessage> listChatMessage;
    private String mUsername;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    //carrega uma instancia do FirebaseAuth, responsavel pela autenticacao dos usuarios
    //carrega uma instancia do FirebaseStorage, responsavel por armazenar arquivos

    //aqui estou dizendo que mMessagesDatabaseReference está referenciando especificamente
    //a porção do bando de dados responsável pelas mensagens, e seus níveis mais baixos




    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out)
        {
            AuthUI.getInstance().signOut(this).addOnCompleListener(new OnCompleteListener<>(){
                @Override
                public void onComplete(@NonNull Task<void> task){
                    Snackbar.make(activity_tela_chat,"You have been signed out.", Snackbar.LENGTH_SHORT).show();
                    finish();
                }
        }}
        return true;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Snackbar.make(activity_tela_chat,"Successefully signed in.welcome!", Snackbar.LENGTH_SHORT) .show();
                //     displayChatMessage();
            }
            else{
                Snackbar.make(activity_tela_chat,"We couldn´t sigm you in.Pease try again later", Snackbar.LENGTH_SHORT) .show();
                finish();

            }}}

    @Override
    protected void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_tela_chat);

        listViewMessages = findViewById(R.id.list_Of_message);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");

        mFirebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        getNomeUsuario(mFirebaseAuth.getUid());

        listChatMessage = new ArrayList<ChatMessage>();
        input = findViewById(R.id.input);

        activity_tela_chat = (RelativeLayout) findViewById(R.id.activity_tela_chat);

        List<ChatMessage> listChatMessage = new ArrayList<>();
        adapter = new MessageAdapter(this, R.layout.list_item, listChatMessage);
        listViewMessages.setAdapter(adapter);
        getNomeUsuario(mFirebaseAuth.getUid());
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);
                mMessagesDatabaseReference.push().setValue(new ChatMessage(input.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                input.setText("");
            }
        });


        Snackbar.make(activity_tela_chat, "welcome" + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_SHORT).show();

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    fab.setEnabled(true);
                } else {
                    fab.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                ChatMessage friendlyMessage = new ChatMessage(input.getText().toString(), mUsername);
                mMessagesDatabaseReference.push().setValue(friendlyMessage);
                // Clear input box
                input.setText("");

            }
        });

        //verifica o status do usuario, se esta logado ou nao.
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            //a variavel firebaseAuth diz se o usuário está logado ou nao
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(TelaChat.this, "Você está logado", Toast.LENGTH_SHORT).show();
                    onSignedInInitialize(user.getDisplayName());
                    //usuario logado

                }
            }


        };

    }

    /*    private void displayChatMessage() {
            ListView listOfMessage = (ListView)findViewById(R.id.list_Of_message);
            adapter = new MessageAdapter(this,R.layout.list_item,listChatMessage);
            {
              *//*  @Override
            protected void populateView(View v, ChatMessage model, int position){
                TextView messageText,messageUser,messageTime;
                messageText = (TextView)findViewById(R.id.message_text);
                messageUser = (TextView)findViewById(R.id.message_user);
                messageTime = (TextView)findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-YYYY (HH:mm:ss)",model.getMessageTime()));
            }*//*
        };
        listOfMessage.setAdapter(adapter);
    }*/
    private void getNomeUsuario(String uid){
        String nmUsuario = "";
        firestore.collection("usuarios").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            @Override
            public void onComplete(@NonNull Task <DocumentSnapshot> task) {
                DocumentSnapshot usnapshot = task.getResult();
                Usuario u = usnapshot.toObject(Usuario.class);
                mUsername = u.getNome();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        //quando a activity entra no modo onPause, eu paro de ouvir se o usuário está
        //logado ou não pois a activity nao esta mais aparecendo na tela
        if(mAuthStateListener != null) {
            //
            //   mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        // desconectarListener();
        //  mMessageAdapter.clear();
    }
    @Override
    protected void onResume(){
        super.onResume();
        //quando a activity resume eu adiciono novamentee o objeto mAuthStateListener
        //para verificar se o usuario esta logado aou nao
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    private void conectarListener(){
        // aqui vou tratar os eventos que ocorrem quando um novo evento ocorre no mChildEventListener
        // quando um novo ChildEventListener é criado ele automaticamente cria vários métodos,
        //como mostrado abaixo
        if(mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {

                //Diz o que fazer quando um novo dado(mensagem) é inserido
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    //gera um objeto do tipo FriendlyMessage a partir dos dados do banco
                    ChatMessage friendlyMessage = dataSnapshot.getValue(ChatMessage.class);
                    //adiciona os dados da mensagem à lista de mensagens do seu app
                    adapter.add(friendlyMessage);
                }

                //Diz o que fazer quando um dado(mensagem) é modificado
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                //Diz o que fazer auando um dado é removido
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                //Diz o que fazer quando a posicao de um dado muda
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                //Diz o que fazer quando ocorre um erro e o dado não pode ser alterado, inserido, apagado
                //tipicamente é chamado quando o usuário não tem permissao para esta parte do banco
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void desconectarListener(){
        if(mChildEventListener !=null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }
    private void onSignedOutCleanup() {
        //mUsername = ANONYMOUS;
        adapter.clear();
        desconectarListener();

    }

    private void onSignedInInitialize(String displayName) {
        mUsername = displayName;
        conectarListener();

    }

}
