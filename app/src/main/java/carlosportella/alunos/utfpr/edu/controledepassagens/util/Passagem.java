package carlosportella.alunos.utfpr.edu.controledepassagens.util;

import java.util.Date;

public class Passagem {

    private String cidade;
    private Pais pais;
    private Date dataIda;
    private Date dataVolta;
    private TipoPassagem tipoPassagem;
    private boolean bagagem;

    public Passagem(String cidade, Pais pais, Date dataIda, Date dataVolta, TipoPassagem tipoPassagem, boolean bagagem) {
        this.cidade = cidade;
        this.pais = pais;
        this.dataIda = dataIda;
        this.dataVolta = dataVolta;
        this.tipoPassagem = tipoPassagem;
        this.bagagem = bagagem;
    }


    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Date getDataIda() {
        return dataIda;
    }

    public void setDataIda(Date dataIda) {
        this.dataIda = dataIda;
    }

    public Date getDataVolta() {
        return dataVolta;
    }

    public void setDataVolta(Date dataVolta) {
        this.dataVolta = dataVolta;
    }

    public TipoPassagem getTipoPassagem() {
        return tipoPassagem;
    }

    public void setTipoPassagem(TipoPassagem tipoPassagem) {
        this.tipoPassagem = tipoPassagem;
    }

    public Object isBagagem() {
        if (bagagem) {
            return "Bagagem comprada";
        } else {
            return "Sem bagagem";
        }

    }

    public void setBagagem(boolean bagagem) {
        this.bagagem = bagagem;
    }

    @Override
    public String toString() {

        return "Passagem{" +
                "cidade='" + cidade + '\'' +
                ", pais=" + pais +
                ", dataIda=" + dataIda +
                ", dataVolta=" + dataVolta +
                ", tipoPassagem=" + tipoPassagem +
                ", bagagem=" + bagagem +
                '}';
    }
}

