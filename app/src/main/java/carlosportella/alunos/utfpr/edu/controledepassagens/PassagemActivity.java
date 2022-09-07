package carlosportella.alunos.utfpr.edu.controledepassagens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;

import carlosportella.alunos.utfpr.edu.controledepassagens.util.Passagem;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.persistencia.PassagemDatabase;

public class PassagemActivity extends AppCompatActivity {

    public static final String MODO = "MODO";
    public static final String ID = "ID";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;

    private EditText editTextCidade, editTextPais, editTextDataIda, editTextDataVolta;
    private RadioGroup radioGroupTipo;
    private CheckBox checkBoxBagagem;

    private int modo;
    private Passagem passagem;

    public static void novaPassagem(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, PassagemActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, requestCode);
    }

    public static void alterarPassagem(Activity activity, int requestCode, Passagem passagem) {
        Intent intent = new Intent(activity, PassagemActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(ID, passagem.getId());

        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passagem);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextCidade = findViewById(R.id.editTextCidade);
        editTextPais = findViewById(R.id.editTextPais);
        editTextDataIda = findViewById(R.id.editTextDataIda);
        editTextDataVolta = findViewById(R.id.editTextDataVolta);
        checkBoxBagagem = findViewById(R.id.checkBoxBagagem);
        radioGroupTipo = findViewById(R.id.RadioGroupTipo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

            modo = bundle.getInt(MODO, NOVO);

            if (modo == ALTERAR) {
                setTitle(getString(R.string.alterar_passagem));

                long id = bundle.getLong(ID);

                PassagemDatabase database = PassagemDatabase.getDatabase(this);

                passagem = database.passagemDao().queryForId(id);

                editTextCidade.setText(passagem.getCidade());
                editTextPais.setText(passagem.getPais());
                editTextDataIda.setText(passagem.getDataIda());
                editTextDataVolta.setText(passagem.getDataVolta());

                int tipo = passagem.getTipoPassagem();

                RadioButton button;
                switch (tipo) {
                    case Passagem.AEREO:
                        button = findViewById(R.id.radioButtonAereo);
                        button.setChecked(true);
                        break;

                    case Passagem.RODOVIARIO:
                        button = findViewById(R.id.radioButtonRodoviario);
                        button.setChecked(true);
                        break;
                }


                checkBoxBagagem.setChecked(passagem.isBagagem());

            } else {

                setTitle(getString(R.string.nova_passagem));

                passagem = new Passagem("");

            }

    }

    public void limparCampos() {
        editTextCidade.setText(null);
        editTextPais.setText(null);
        editTextDataIda.setText(null);
        editTextDataVolta.setText(null);
        checkBoxBagagem.setChecked(false);
        radioGroupTipo.clearCheck();

        Toast.makeText(this,
                R.string.os_campos_foram_limpos,
                Toast.LENGTH_SHORT).show();
        editTextCidade.requestFocus();

    }

    public void salvar() throws ParseException {

        String cidade = editTextCidade.getText().toString();
        // Verifica se o campo Cidade está em branco
        if (cidade == null || cidade.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.destino_em_branco,
                    Toast.LENGTH_LONG).show();
            editTextCidade.requestFocus();
            return;
        }

        String pais = editTextPais.getText().toString();
        //TODO pegar string
        // Verifica se o País foi selecionado
        if (pais == null || pais.trim().isEmpty()) {
            Toast.makeText(this,
                    "O pais nao pode estar em branco",
                    Toast.LENGTH_LONG).show();
            return;
        }

        String dataIda = editTextDataIda.getText().toString();
        // Verifica se o campo Data está em branco
        if (dataIda == null || dataIda.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.data_ida_em_branco,
                    Toast.LENGTH_LONG).show();
            editTextDataIda.requestFocus();
            return;
        }

        String dataVolta = editTextDataVolta.getText().toString();
        if (dataVolta == null || dataVolta.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.data_volta_em_branco,
                    Toast.LENGTH_LONG).show();
            editTextDataVolta.requestFocus();
            return;
        }

        int tipoPassagem;

        // Verifica radioButton
        switch (radioGroupTipo.getCheckedRadioButtonId()) {
            case R.id.radioButtonRodoviario:
                tipoPassagem = Passagem.RODOVIARIO;
                break;

            case R.id.radioButtonAereo:
                tipoPassagem = Passagem.AEREO;
                break;

            default:
                Toast.makeText(this,
                        R.string.selecionar_tipo_transporte,
                        Toast.LENGTH_LONG).show();
                return;
        }

        boolean bagagem = checkBoxBagagem.isChecked();


        Intent intent = new Intent();

        passagem.setCidade(cidade);
        passagem.setPais(pais);
        passagem.setDataIda(dataIda);
        passagem.setDataVolta(dataVolta);
        passagem.setTipoPassagem(tipoPassagem);
        passagem.setBagagem(bagagem);

        PassagemDatabase database = PassagemDatabase.getDatabase(this);

        if(modo == NOVO) {
            database.passagemDao().insert(passagem);
        } else {
            database.passagemDao().update(passagem);
        }

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    public void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void onBackPressed() {
        cancelar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.passagem_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemSalvar:
                try {
                    salvar();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;

            case android.R.id.home:
                cancelar();
                return true;

            case R.id.menuItemLimpar:
                limparCampos();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
