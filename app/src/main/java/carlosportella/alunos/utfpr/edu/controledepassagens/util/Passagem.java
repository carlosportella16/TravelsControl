package carlosportella.alunos.utfpr.edu.controledepassagens.util;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity
public class Passagem {

    public static final int AEREO = 1;
    public static final int RODOVIARIO = 2;

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String cidade;
    private String pais;

    private String dataIda;
    private String dataVolta;
    private int tipoPassagem;
    private boolean bagagem;

    public Passagem(){

    }

    public Passagem(String cidade) {
        this.cidade = cidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDataIda() {
        return dataIda;
    }

    public void setDataIda(String dataIda) {
        this.dataIda = dataIda;
    }

    public String getDataVolta() {
        return dataVolta;
    }

    public void setDataVolta(String dataVolta) {
        this.dataVolta = dataVolta;
    }

    public int getTipoPassagem() {
        return tipoPassagem;
    }

    public void setTipoPassagem(int tipoPassagem) {
        this.tipoPassagem = tipoPassagem;
    }

    public boolean isBagagem() {
        if (bagagem) {
            return true;
        } else {
            return false;
        }

    }

    public void setBagagem(boolean bagagem) {
        this.bagagem = bagagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passagem passagem = (Passagem) o;
        return cidade.equals(passagem.cidade) && pais.equals(passagem.pais) && dataIda.equals(passagem.dataIda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cidade, pais, dataIda);
    }

    @Override
    public String toString() {
        return "Passagem{" +
                "id=" + id +
                ", cidade='" + cidade + '\'' +
                ", pais='" + pais + '\'' +
                ", dataIda='" + dataIda + '\'' +
                ", dataVolta='" + dataVolta + '\'' +
                ", tipoPassagem=" + tipoPassagem +
                ", bagagem=" + bagagem +
                '}';
    }
}

