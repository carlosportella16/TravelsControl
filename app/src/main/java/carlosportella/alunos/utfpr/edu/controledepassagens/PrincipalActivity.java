package carlosportella.alunos.utfpr.edu.controledepassagens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import carlosportella.alunos.utfpr.edu.controledepassagens.util.Pais;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.Passagem;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.TipoPassagem;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.adapter.ListaPassagensAdapter;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.data.DataConverter;

public class PrincipalActivity extends AppCompatActivity {

    private ListView listViewPassagens;
    private ListaPassagensAdapter listaPassagensAdapter;
    private ArrayList<Passagem> passagemLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        listViewPassagens = findViewById(R.id.listViewPassagens);

        try {
            popularLista();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    //TODO VERIFICAR COMO FOI FEITO ANTES
    private void popularLista() throws ParseException {
        passagemLista = new ArrayList<>();
        //TODO
        //listaPassagensAdapter = new ListaPassagensAdapter();
        listViewPassagens.setAdapter(listaPassagensAdapter);
    }

    public void adicionarPassagem(View view) {
        PassagemActivity.novaPassagem(this);
    }

    public void abrirSobre(View view) {
        SobreActivity.sobre(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            try {
            String cidade = bundle.getString(PassagemActivity.CIDADE);
            String pais = bundle.getString(PassagemActivity.PAIS);

            Date data_ida = DataConverter.converteStringToDate(bundle.getString(PassagemActivity.DATA_IDA));
            Date data_volta = DataConverter.converteStringToDate(bundle.getString(PassagemActivity.DATA_VOLTA));

            int tipo = bundle.getInt(PassagemActivity.TIPO);
            boolean bagagem = bundle.getBoolean(PassagemActivity.BAGAGEM);

            Pais pais_entidade = new Pais(pais);
            Passagem passagem = new Passagem(cidade, pais_entidade, data_ida, data_volta, TipoPassagem.verifica(tipo), bagagem);

            passagemLista.add(passagem);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            listaPassagensAdapter.notifyDataSetChanged();
        }
    }
}