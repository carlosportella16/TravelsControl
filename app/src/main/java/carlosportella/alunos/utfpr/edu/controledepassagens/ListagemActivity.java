package carlosportella.alunos.utfpr.edu.controledepassagens;

import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.ArrayList;

import carlosportella.alunos.utfpr.edu.controledepassagens.util.Pais;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.Passagem;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.TipoPassagem;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.adapter.ListaPassagensAdapter;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.data.DataConverter;

public class ListagemActivity extends AppCompatActivity {

    private ListView listViewPassagens;

    ArrayList<Passagem> passagemLista;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        listViewPassagens = findViewById(R.id.listViewPassagens);

        // Ao clicar mostra um toast na tela com os dados da passagem
        listViewPassagens.setOnItemClickListener((parent, view, position, id) -> {

            Passagem passagem = passagemLista.get(position);

            Toast.makeText(getApplicationContext(), getString(R.string.passagem_para) +
                    passagem.getCidade() +
                    getString(R.string.traco)+
                    passagem.getPais().getNome() +
                    getString(R.string.foi_clicado),
                    Toast.LENGTH_LONG).show();
        });

        try {
            popularLista();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void popularLista() throws ParseException {
        String[] cidadades = getResources().getStringArray(R.array.cidades);

        String[] paisesNomes = getResources().getStringArray(R.array.pais);
        TypedArray bandeiras = getResources()
                .obtainTypedArray(R.array.bandeira_lista);

        String[] dataIda = getResources().getStringArray(R.array.data_ida);
        String[] dataVolta = getResources().getStringArray(R.array.data_volta);
        String[] tipoPassagem = getResources().getStringArray(R.array.tipo_passagem);
        String[] bagagem = getResources().getStringArray(R.array.bagagem);




        ArrayList<Pais> paises = new ArrayList<>();

        for (int cont = 0; cont < paisesNomes.length; cont++) {
            paises.add(new Pais(paisesNomes[cont], bandeiras.getDrawable(cont)));
        }

        passagemLista = new ArrayList<>();

        for (int cont = 0; cont < cidadades.length; cont++) {

            passagemLista.add(new Passagem(cidadades[cont],
                    paises.get(cont),
                    DataConverter.converteStringToDate(dataIda[cont]),
                    DataConverter.converteStringToDate(dataVolta[cont]),
                    TipoPassagem.verifica(tipoPassagem[cont]),
                    verificaBagagem(bagagem[cont]))
            );

            ListaPassagensAdapter listaPassagensAdapter = new ListaPassagensAdapter(this, passagemLista);
            listViewPassagens.setAdapter(listaPassagensAdapter);
        }
    }

    //Verificar bagagem
    private boolean verificaBagagem(String value) {
        if(value.equals("true")) {
            return true;
        } else{
            return false;
        }
    }
}