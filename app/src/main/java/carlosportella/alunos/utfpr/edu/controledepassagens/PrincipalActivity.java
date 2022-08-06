package carlosportella.alunos.utfpr.edu.controledepassagens;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
    private ArrayList<Passagem> passagemLista;
    ListaPassagensAdapter listaPassagensAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        listViewPassagens = findViewById(R.id.listViewPassagens);

        listViewPassagens.setOnItemClickListener((parent, view, position, id) -> {

            Passagem passagem = passagemLista.get(position);

            Toast.makeText(getApplicationContext(), getString(R.string.passagem_para) +
                            passagem.getCidade() +
                            getString(R.string.traco)+
                            passagem.getPais().getNome() +
                            getString(R.string.foi_clicado),
                    Toast.LENGTH_LONG).show();
        });

        popularLista();

    }


    private void popularLista() {

        passagemLista = new ArrayList<>();
        listaPassagensAdapter = new ListaPassagensAdapter(this, passagemLista);
        listViewPassagens.setAdapter(listaPassagensAdapter);
    }

    public void adicionarPassagem(View view) {
        PassagemActivity.novaPassagem(this);
    }

    public void abrirSobre(View view) {
        SobreActivity.sobre(this);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            try {
                String cidade = bundle.getString(PassagemActivity.CIDADE);
                String pais = bundle.getString(PassagemActivity.PAIS);
                int bandeira = bundle.getInt(PassagemActivity.BANDEIRA);

                Date data_ida = DataConverter.converteStringToDate(bundle.getString(PassagemActivity.DATA_IDA));
                Date data_volta = DataConverter.converteStringToDate(bundle.getString(PassagemActivity.DATA_VOLTA));

                int tipo = bundle.getInt(PassagemActivity.TIPO);
                boolean bagagem = bundle.getBoolean(PassagemActivity.BAGAGEM);

                TypedArray bandeiras = getResources()
                        .obtainTypedArray(R.array.bandeiras_paises);


                Pais pais_entidade = new Pais(pais, bandeiras.getDrawable(bandeira));

                passagemLista.add(new Passagem(cidade, pais_entidade, data_ida, data_volta, TipoPassagem.verifica(tipo), bagagem));
                listaPassagensAdapter.notifyDataSetChanged();

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}