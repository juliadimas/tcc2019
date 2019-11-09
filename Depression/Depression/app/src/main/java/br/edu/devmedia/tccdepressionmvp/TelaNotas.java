package br.edu.devmedia.tccdepressionmvp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class TelaNotas extends AppCompatActivity {

    private EditText etNotas;
    private Button bt_criar;
    private ListView lvNotas;

    private ArrayList<String> Notas = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_notas);

        inicializaComponentes();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Notas);
        lvNotas.setAdapter(adapter);

        bt_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoNota = etNotas.getText().toString();
                if (textoNota.length() > 0) {
                    etNotas.setText("");
                    etNotas.findFocus();
                    Notas.add(textoNota);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        lvNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(TelaNotas.this);
                adb.setTitle("Nota");
                adb.setMessage("Você deseja apagar essa nota?");
                final int positionToRemove = i;
                adb.setNegativeButton("Não", null);
                adb.setNegativeButton("Sim", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Notas.remove(positionToRemove);
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
            }

        });
    }


    private void inicializaComponentes() {
        etNotas = (EditText) findViewById(R.id.etNotas);
        bt_criar = (Button) findViewById(R.id.bt_criar);
        lvNotas = (ListView) findViewById(R.id.lvNotas);

    }
}
