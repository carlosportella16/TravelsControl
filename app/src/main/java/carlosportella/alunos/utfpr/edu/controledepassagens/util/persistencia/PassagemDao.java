package carlosportella.alunos.utfpr.edu.controledepassagens.util.persistencia;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import carlosportella.alunos.utfpr.edu.controledepassagens.util.Passagem;

@Dao
public interface PassagemDao {

    @Insert(onConflict = REPLACE)
    long insert(Passagem passagem);

    @Delete
    void delete(Passagem passagem);

    @Update
    void update(Passagem passagem);

    @Query("SELECT * FROM passagem WHERE id = :id")
    Passagem queryForId(long id);

    @Query("SELECT * FROM passagem ORDER BY cidade ASC")
    List<Passagem> queryAll();
}
