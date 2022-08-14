package carlosportella.alunos.utfpr.edu.controledepassagens;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
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
    private ListaPassagensAdapter listaPassagensAdapter;

    private int posicaoSelecionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        listViewPassagens = findViewById(R.id.listViewPassagens);

        popularLista();

        registerForContextMenu(listViewPassagens);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.principal_opcoes, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.menuItemAdicionar:
//
//                return true;
//
//            case R.id.menuItemSobre:
//
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


    private void popularLista() {

        passagemLista = new ArrayList<>();
        listaPassagensAdapter = new ListaPassagensAdapter(this, passagemLista);
        listViewPassagens.setAdapter(listaPassagensAdapter);
    }

    private void alterarPassagem(int posicao) {
        Passagem passagem = passagemLista.get(posicao);

        PassagemActivity.alterarPassagem(this, passagem);
        posicaoSelecionada = posicao;

    }

    private void excluir(int posicao) {
        passagemLista.remove(posicao);

        listaPassagensAdapter.notifyDataSetChanged();
    }

    public void adicionarPassagem(View view) {
        PassagemActivity.novaPassagem(this);
    }

    public void abrirSobre(View view) {
        SobreActivity.sobre(this);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
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

                if (requestCode == PassagemActivity.ALTERAR) {
                    Passagem passagem = passagemLista.get(posicaoSelecionada);
                    passagem.setCidade(cidade);
                    passagem.setPais(pais_entidade);
                    passagem.setDataIda(data_ida);
                    passagem.setDataVolta(data_volta);
                    passagem.setTipoPassagem(TipoPassagem.verifica(tipo));
                    passagem.setBagagem(bagagem);

                    posicaoSelecionada = -1;
                } else {
                    Passagem passagem = new Passagem(cidade, pais_entidade, data_ida, data_volta, TipoPassagem.verifica(tipo), bagagem);
                    passagemLista.add(passagem);
                }

                listaPassagensAdapter.notifyDataSetChanged();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.principal_menu_contexto_flutuante, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.menuItemAlterar:
                alterarPassagem(info.position);
                return true;

            case R.id.menuItemExcluir:
                excluir(info.position);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}