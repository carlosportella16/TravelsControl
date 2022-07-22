package carlosportella.alunos.utfpr.edu.controledepassagens;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import carlosportella.alunos.utfpr.edu.controledepassagens.util.Pais;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.adapter.PaisAdapter;

public class MainActivity extends AppCompatActivity {

    private EditText cidade, dataIda, dataVolta;
    private CheckBox bagagem;
    private RadioGroup radioGroupTipo;
    private Spinner spinnerPaises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cidade = findViewById(R.id.editTextCidade);
        spinnerPaises = findViewById(R.id.spinnerPais);
        popularSpinner();

        dataIda = findViewById(R.id.editTextDataIda);
        dataVolta = findViewById(R.id.editTextDataVolta);
        bagagem = findViewById(R.id.checkBoxBagagem);
        radioGroupTipo = findViewById(R.id.RadioGroupTipo);

    }

    public void popularSpinner() {

        String[] nomes = getResources()
                .getStringArray(R.array.nomes_paises);
        TypedArray bandeiras = getResources()
                .obtainTypedArray(R.array.bandeiras_paises);

        ArrayList<Pais> paises = new ArrayList<>();

        for (int cont = 0; cont < nomes.length; cont++) {
            paises.add(new Pais(nomes[cont], bandeiras.getDrawable(cont)));
        }

        PaisAdapter paisAdapter = new PaisAdapter(this, paises);
        spinnerPaises.setAdapter(paisAdapter);

    }

    public void limparCampos(View view) {
        cidade.setText(null);
        dataIda.setText(null);
        dataVolta.setText(null);
        bagagem.setChecked(false);
        radioGroupTipo.clearCheck();
        popularSpinner();

        Toast.makeText(this,
                R.string.os_campos_foram_limpos,
                Toast.LENGTH_SHORT).show();
        cidade.requestFocus();

    }

    public void salvar(View view) {
        // Verifica se o campo Cidade está em branco
        if (cidade == null || cidade.getText().toString().trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.destino_em_branco,
                    Toast.LENGTH_LONG).show();
            cidade.requestFocus();
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
        if (dataIda == null || dataIda.getText().toString().trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.data_ida_em_branco,
                    Toast.LENGTH_LONG).show();
            dataIda.requestFocus();
            return;
        }
        if (dataVolta == null || dataVolta.getText().toString().trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.data_volta_em_branco,
                    Toast.LENGTH_LONG).show();
            dataVolta.requestFocus();
            return;
        }

        // Verifica se o Radio Button foi selecionado
        if (radioGroupTipo.getCheckedRadioButtonId() != R.id.radioButtonAereo &&
                radioGroupTipo.getCheckedRadioButtonId() != R.id.radioButtonRodoviario) {
            Toast.makeText(this,
                    R.string.selecionar_tipo_transporte,
                    Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this,
                R.string.dados_salvos_com_sucesso,
                Toast.LENGTH_LONG).show();

        limparCampos(view);
    }

}
