package carlosportella.alunos.utfpr.edu.controledepassagens.util.persistencia;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import carlosportella.alunos.utfpr.edu.controledepassagens.util.Passagem;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.data.DataConverter;

public class PassagemDAO {

    public static final String TABELA = "PASSAGEM";
    public static final String ID = "ID";
    public static final String CIDADE = "CIDADE";
    public static final String PAIS = "PAIS";
    public static final String DATA_IDA = "DATA_IDA";
    public static final String DATA_VOLTA = "DATA_VOLTA";
    public static final String TIPO_PASSAGEM = "TIPO_PASSAGEM";
    public static final String BAGAGEM = "BAGAGEM";

    private PassagemDatabase conexao;
    public List<Passagem> lista;

    public PassagemDAO(PassagemDatabase passagemDatabase) {
        conexao = passagemDatabase;
        lista = new ArrayList<>();
    }

    public void criarTabela(SQLiteDatabase database) {

        String sql = "CREATE TABLE " + TABELA + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                CIDADE + " TEXT NOT NULL, " +
                PAIS + " TEXT NOT NULL, " +
                DATA_IDA + " DATE NOT NULL, " +
                DATA_VOLTA + " DATE NOT NULL,"+
                TIPO_PASSAGEM + "TEXT NOT NULL," +
                BAGAGEM + " BOOLEAN )";

        database.execSQL(sql);
    }

    public void apagarTabela(SQLiteDatabase database) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;

        database.execSQL(sql);
    }

    public boolean inserir(Passagem passagem) {
        ContentValues values = new ContentValues();

        values.put(CIDADE, passagem.getCidade());
        values.put(PAIS, passagem.getPais().getNome());
        values.put(DATA_IDA, DataConverter.converteDateToString(passagem.getDataIda()));
        values.put(DATA_VOLTA, DataConverter.converteDateToString(passagem.getDataVolta()));
        values.put(TIPO_PASSAGEM, passagem.getTipoPassagem().toString());
        values.put(BAGAGEM, passagem.isBagagem());

        long id = conexao.getWritableDatabase().insert(TABELA, null, values);

        passagem.setId(id);

        lista.add(passagem);

        return true;
    }

    //TODO alterar
    public boolean alterar(Passagem passagem) {
        ContentValues values = new ContentValues();

        values.put(CIDADE, passagem.getCidade());
        values.put(PAIS, passagem.getPais().getNome());
        values.put(DATA_IDA, DataConverter.converteDateToString(passagem.getDataIda()));
        values.put(DATA_VOLTA, DataConverter.converteDateToString(passagem.getDataVolta()));
        values.put(TIPO_PASSAGEM, passagem.getTipoPassagem().toString());
        values.put(BAGAGEM, passagem.isBagagem());

        String[] args = {String.valueOf(passagem.getId())};

        conexao.getWritableDatabase().update(TABELA, values, ID + " = ?", args);

        return true;

    }

    //TODO apagar
    public boolean apagar(Passagem passagem) {
        String[] args = {String.valueOf(passagem.getId())};

        conexao.getWritableDatabase().delete(TABELA, ID + " = ?", args);

        lista.remove(passagem);

        return true;
    }

    //TODO carregarTudo

    //TODO passagemPorID
    public Passagem passagemPorId(long id) {
        for(Passagem p : lista) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

}
