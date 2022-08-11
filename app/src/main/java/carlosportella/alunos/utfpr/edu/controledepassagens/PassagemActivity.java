package carlosportella.alunos.utfpr.edu.controledepassagens;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import carlosportella.alunos.utfpr.edu.controledepassagens.util.Pais;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.Passagem;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.TipoPassagem;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.adapter.PaisAdapter;

public class PassagemActivity extends AppCompatActivity {

    public static final String CIDADE = "CIDADE";
    public static final String PAIS = "PAIS";
    public static final String DATA_IDA = "DATA_IDA";
    public static final String DATA_VOLTA = "DATA_VOLTA";
    public static final String TIPO = "TIPO";
    public static final String BAGAGEM = "BAGAGEM";
    public static final String BANDEIRA = "BANDEIRA";

    public static final int NOVO = 1;
    public static final int ALTERAR = 2;

    public static final String MODO = "MODO";


    private EditText editTextCidade, editTextDataIda, editTextDataVolta;
    private CheckBox checkBoxBagagem;
    private RadioGroup radioGroupTipo;
    private Spinner spinnerPaises;

    ArrayList<Pais> paises;
    PaisAdapter paisAdapter;

    private int modo;

    public static void novaPassagem(AppCompatActivity activity) {
        Intent intent = new Intent(activity, PassagemActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterarPassagem(AppCompatActivity activity, Passagem passagem) {
        Intent intent = new Intent(activity, PassagemActivity.class);

        intent.putExtra(CIDADE, passagem.getCidade());
        intent.putExtra(PAIS, passagem.getPais().getNome());
        intent.putExtra(DATA_IDA, passagem.getDataIda());
        intent.putExtra(DATA_VOLTA, passagem.getDataVolta());
        intent.putExtra(TIPO, passagem.getTipoPassagem());
        intent.putExtra(BAGAGEM, passagem.isBagagem().toString());

        activity.startActivityForResult(intent, ALTERAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passagem);

        editTextCidade = findViewById(R.id.editTextCidade);
        spinnerPaises = findViewById(R.id.spinnerPais);
        popularSpinner();

        editTextDataIda = findViewById(R.id.editTextDataIda);
        editTextDataVolta = findViewById(R.id.editTextDataVolta);
        checkBoxBagagem = findViewById(R.id.checkBoxBagagem);
        radioGroupTipo = findViewById(R.id.RadioGroupTipo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO) {
                setTitle(getString(R.string.nova_passagem));
            } else {
                String cidade = bundle.getString(CIDADE);
                editTextCidade.setText(cidade);

                String pais = bundle.getString(PAIS);

                for (int pos = 0; 0 < spinnerPaises.getAdapter().getCount(); pos++) {
                    String valor = (String) spinnerPaises.getItemAtPosition(pos);

                    if (valor.equals(pais)) {
                        spinnerPaises.setSelection(pos);
                        break;
                    }
                }

                String dataIda = bundle.getString(DATA_IDA);
                editTextDataIda.setText(dataIda);

                String dataVolta = bundle.getString(DATA_VOLTA);
                editTextDataVolta.setText(dataVolta);

                int tipo = bundle.getInt(TIPO);

                RadioButton button;
                switch (tipo) {
                    case TipoPassagem.AEREO:
                        button = findViewById(R.id.radioButtonAereo);
                        button.setChecked(true);
                        break;

                    case TipoPassagem.RODOVIARIO:
                        button = findViewById(R.id.radioButtonRodoviario);
                        button.setChecked(true);
                        break;
                }

                boolean bagagem = bundle.getBoolean(BAGAGEM);
                checkBoxBagagem.setChecked(bagagem);

                setTitle(getString(R.string.alterar_passagem));
            }

        }
        editTextCidade.requestFocus();

    }

    public void popularSpinner() {

        String[] nomes = getResources()
                .getStringArray(R.array.nomes_paises);
        TypedArray bandeiras = getResources()
                .obtainTypedArray(R.array.bandeiras_paises);

        paises = new ArrayList<>();

        for (int cont = 0; cont < nomes.length; cont++) {
            paises.add(new Pais(nomes[cont], bandeiras.getDrawable(cont)));
        }

        paisAdapter = new PaisAdapter(this, paises);
        spinnerPaises.setAdapter(paisAdapter);

    }

    public void limparCampos(View view) {
        editTextCidade.setText(null);
        editTextDataIda.setText(null);
        editTextDataVolta.setText(null);
        checkBoxBagagem.setChecked(false);
        radioGroupTipo.clearCheck();
        popularSpinner();

        Toast.makeText(this,
                R.string.os_campos_foram_limpos,
                Toast.LENGTH_SHORT).show();
        editTextCidade.requestFocus();

    }

    public void salvar(View view) {

        // Verifica se o campo Cidade está em branco
        if (editTextCidade == null || editTextCidade.getText().toString().trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.destino_em_branco,
                    Toast.LENGTH_LONG).show();
            editTextCidade.requestFocus();
            return;
        }

        // Verifica se o País foi selecionado
        if (spinnerPaises.getSelectedItemPosition() == 0) {
            Toast.makeText(this,
                    R.string.selecione_o_pais_que_ira_viajar,
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Verifica se o campo Data está em branco
        if (editTextDataIda == null || editTextDataIda.getText().toString().trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.data_ida_em_branco,
                    Toast.LENGTH_LONG).show();
            editTextDataIda.requestFocus();
            return;
        }

        if (editTextDataVolta == null || editTextDataVolta.getText().toString().trim().isEmpty()) {
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
                tipoPassagem = TipoPassagem.RODOVIARIO;
                break;

            case R.id.radioButtonAereo:
                tipoPassagem = TipoPassagem.AEREO;
                break;

            default:
                Toast.makeText(this,
                        R.string.selecionar_tipo_transporte,
                        Toast.LENGTH_LONG).show();
                return;
        }

        Intent intent = new Intent();

        intent.putExtra(CIDADE, editTextCidade.getText().toString());
        intent.putExtra(PAIS, paisAdapter.getItem(spinnerPaises.getSelectedItemPosition()).toString());
        intent.putExtra(BANDEIRA, paisAdapter.getItem(spinnerPaises.getSelectedItemPosition()).toString());
        intent.putExtra(DATA_IDA, editTextDataIda.getText().toString());
        intent.putExtra(DATA_VOLTA, editTextDataVolta.getText().toString());
        intent.putExtra(TIPO, tipoPassagem);
        intent.putExtra(BAGAGEM, checkBoxBagagem.isChecked());

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

}
