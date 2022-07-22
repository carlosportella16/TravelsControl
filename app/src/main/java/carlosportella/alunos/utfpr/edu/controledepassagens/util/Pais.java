package carlosportella.alunos.utfpr.edu.controledepassagens.util;

import android.graphics.drawable.Drawable;

public class Pais {
    private String nome;
    private Drawable bandeira;

    public Pais() {
    }
    public Pais(String nome, Drawable bandeira) {
        this.nome = nome;
        this.bandeira = bandeira;
    }

    public Pais(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Drawable getBandeira() {
        return bandeira;
    }

    public void setBandeira(Drawable bandeira) {
        this.bandeira = bandeira;
    }

    @Override
    public String toString() {
        return nome + '\'';
    }
}
