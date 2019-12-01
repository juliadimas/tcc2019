package br.edu.devmedia.tccdepressionmvp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class TelaSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Conexao.getFirebaseAuth();
        setContentView(R.layout.activity_tela_splash);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarLogin();
            }
        },3000);
    }

    private void mostrarLogin() {
        Intent intent = new Intent(TelaSplash.this, GraficoEntrou.class);
        startActivity(intent);
    }
}
