package carlosportella.alunos.utfpr.edu.controledepassagens.util.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import carlosportella.alunos.utfpr.edu.controledepassagens.util.Passagem;

@Database(entities = {Passagem.class}, version = 1, exportSchema = false)
public abstract class PassagemDatabase extends RoomDatabase {

    public abstract PassagemDao passagemDao();

    private static PassagemDatabase instance;

    public static PassagemDatabase getDatabase(final Context context) {

        if(instance == null) {
            synchronized (PassagemDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            PassagemDatabase.class,
                            "passagem.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }

}
