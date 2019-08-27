package br.edu.devmedia.tcc_depression;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarLogin();
            }
        },3000);
    }

    private void mostrarLogin() {
        Intent intent = new Intent(MainActivity.this, telaLogin.class);
        startActivity(intent);
        finish();
    }
}
