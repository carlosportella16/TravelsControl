package carlosportella.alunos.utfpr.edu.controledepassagens;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import java.util.List;

import carlosportella.alunos.utfpr.edu.controledepassagens.util.Passagem;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.UtilsGUI;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.persistencia.PassagemDatabase;

public class PrincipalActivity extends AppCompatActivity {

    private static final int REQUEST_NOVA_PASSAGEM = 1;
    private static final int REQUEST_ALTERAR_PASSAGEM = 2;


    private ListView listViewPassagens;
    private ArrayAdapter<Passagem> listaAdapter;

    private int posicaoSelecionada = -1;
    private ActionMode actionMode;
    private View viewSelecionada;

    private static final String ARQUIVO =
            "carlosportella.alunos.utfpr.edu.controledepassagens.PREFERENCIA_ORDEM_LISTA";

    private static final int ORDENAR_DATA = 1;
    private static final int ORDENAR_CIDADE = 2;
    private int opcao = -1;
    private static final String ORDEM = "ORDEM";



//    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
//
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//
//            MenuInflater inflate = mode.getMenuInflater();
//            inflate.inflate(R.menu.principal_opcoes_selecionadas, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            return false;
//        }
//
//
//        @Override
//        public boolean onActionItemClicked(ActionMode mode,MenuItem item) {
//
//            AdapterView.AdapterContextMenuInfo info;
//
//            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//
//            Passagem passagem = (Passagem) listViewPassagens.getItemAtPosition(info.position);
//
//            switch(item.getItemId()){
//                case R.id.menuItemAlterar:
//                    PassagemActivity.alterarPassagem(PrincipalActivity.this,
//                            REQUEST_ALTERAR_PASSAGEM,
//                            passagem);
//                    return true;
//
//                case R.id.menuItemExcluir:
//                    excluirPassagem(passagem);
//                    return true;
//
//                default:
//                    return false;
//            }
//        }
//
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
//
//            if (viewSelecionada != null){
//                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
//            }
//
//            actionMode         = null;
//            viewSelecionada    = null;
//
//            listViewPassagens.setEnabled(true);
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        listViewPassagens = findViewById(R.id.listViewPassagens);

        listViewPassagens.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {

                Passagem passagem = (Passagem) parent.getItemAtPosition(position);

                PassagemActivity.alterarPassagem(PrincipalActivity.this,
                                                    REQUEST_ALTERAR_PASSAGEM,
                                                    passagem);
            }
        });

        popularLista();

        lerPreferencia();

        setTitle(getString(R.string.controle_de_passagens));
        registerForContextMenu(listViewPassagens);
    }

    private void popularLista() {

        PassagemDatabase database = PassagemDatabase.getDatabase(this);

        List<Passagem> lista = database.passagemDao().queryAll();

        listaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                lista);

        listViewPassagens.setAdapter(listaAdapter);
    }


    private void excluirPassagem(final Passagem passagem) {
        //TODO pegar string
        String mensagem = "Deseja realmente apagar?" + "\n" + passagem.getCidade();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:

                                PassagemDatabase database =
                                        PassagemDatabase.getDatabase(PrincipalActivity.this);
                                database.passagemDao().delete(passagem);

                                //TODO mudar adapter
                                listaAdapter.remove(passagem);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

        UtilsGUI.confirmaAcao(this, mensagem, listener);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_NOVA_PASSAGEM || requestCode == REQUEST_ALTERAR_PASSAGEM) &&
                resultCode == Activity.RESULT_OK) {

            popularLista();
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
                PassagemActivity.novaPassagem(this, REQUEST_NOVA_PASSAGEM);
                return true;

            case R.id.menuItemSobre:
                SobreActivity.sobre(this);
                return true;

            case R.id.menuItemOrdenarData:
                salvarPreferenciaLista(ORDENAR_DATA);
                return true;

            case R.id.menuItemOrdenarCidade:
                salvarPreferenciaLista(ORDENAR_CIDADE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ----- SHAREDPREFERENCES -------

    private void lerPreferencia(){
        SharedPreferences shared = getSharedPreferences(ARQUIVO,
                Context.MODE_PRIVATE);

        opcao = shared.getInt(ORDEM, opcao);

        ordenarLista();
    }

    private void salvarPreferenciaLista(int novoValor){
        SharedPreferences shared = getSharedPreferences(ARQUIVO,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(ORDEM, novoValor);
        editor.commit();

        opcao = novoValor;

        ordenarLista();
    }

    //TODO
    private void ordenarLista(){
        if(opcao==1) {
//            passagemLista.sort(new PassagemComparatorDataIda());
//            listaPassagensAdapter.notifyDataSetChanged();
        } else if(opcao==2) {
//            passagemLista.sort(new PassagemComparatorCidade());
//            listaPassagensAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item;

        switch(opcao){

            case 1:
                item = menu.findItem(R.id.menuItemOrdenarData);
                break;

            case 2:
                item = menu.findItem(R.id.menuItemOrdenarCidade);
                break;

            default:
                return false;
        }

        item.setChecked(true);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Passagem passagem = (Passagem) listViewPassagens.getItemAtPosition(info.position);

        switch(item.getItemId()){

            case R.id.menuItemAlterar:
                PassagemActivity.alterarPassagem(this,
                        REQUEST_ALTERAR_PASSAGEM,
                        passagem);
                return true;

            case R.id.menuItemExcluir:
                excluirPassagem(passagem);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}