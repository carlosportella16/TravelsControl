package carlosportella.alunos.utfpr.edu.controledepassagens.util.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PassagemDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "passagem.db";
    private static final int DB_VERSION = 1;

    private static PassagemDatabase instance;

    private Context context;
    public PassagemDAO passagemDAO;

    public static PassagemDatabase getInstance(Context contexto) {
        if(instance == null) {
            instance = new PassagemDatabase(contexto);
        }

        return instance;
    }

    private PassagemDatabase(Context contexto) {
        super(contexto, DB_NAME, null, DB_VERSION);

        context = contexto;

        passagemDAO = new PassagemDAO(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
