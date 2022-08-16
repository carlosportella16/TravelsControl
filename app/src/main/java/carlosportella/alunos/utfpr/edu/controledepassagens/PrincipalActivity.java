package carlosportella.alunos.utfpr.edu.controledepassagens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

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
    private ActionMode actionMode;
    private View viewSelecionada;


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.principal_opcoes_selecionadas, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch(item.getItemId()){
                case R.id.menuItemAlterar:
                    alterarPassagem();
                    mode.finish();
                    return true;

                case R.id.menuItemExcluir:
                    excluir();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode         = null;
            viewSelecionada    = null;

            listViewPassagens.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        listViewPassagens = findViewById(R.id.listViewPassagens);

        listViewPassagens.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {

                posicaoSelecionada = position;
                alterarPassagem();
            }
        });

        listViewPassagens.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewPassagens.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position,
                                                   long id) {

                        if (actionMode != null){
                            return false;
                        }

                        posicaoSelecionada = position;

                        view.setBackgroundColor(Color.LTGRAY);

                        viewSelecionada = view;

                        listViewPassagens.setEnabled(false);

                        actionMode = startSupportActionMode(mActionModeCallback);

                        return true;
                    }
                });

        popularLista();
    }

    private void popularLista() {

        passagemLista = new ArrayList<>();
        listaPassagensAdapter = new ListaPassagensAdapter(this, passagemLista);
        listViewPassagens.setAdapter(listaPassagensAdapter);
    }

    private void alterarPassagem() {

        Passagem passagem = passagemLista.get(posicaoSelecionada);
        PassagemActivity.alterarPassagem(this, passagem);
    }

    private void excluir() {
        passagemLista.remove(posicaoSelecionada);
        listaPassagensAdapter.notifyDataSetChanged();
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

                Date data_ida = DataConverter.converteStringToDate
                        (bundle.getString(PassagemActivity.DATA_IDA));
                Date data_volta = DataConverter.converteStringToDate(
                        bundle.getString(PassagemActivity.DATA_VOLTA));

                int tipo = bundle.getInt(PassagemActivity.TIPO);
                boolean bagagem = bundle.getBoolean(PassagemActivity.BAGAGEM);


                Pais pais_entidade = new Pais(pais);

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
                    Passagem passagem = new Passagem(cidade,
                            pais_entidade,
                            data_ida,
                            data_volta,
                            TipoPassagem.verifica(tipo),
                            bagagem);

                    passagemLista.add(passagem);
                }

                listaPassagensAdapter.notifyDataSetChanged();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuItemAdicionar:
                PassagemActivity.novaPassagem(this);
                return true;

            case R.id.menuItemSobre:
                SobreActivity.sobre(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}