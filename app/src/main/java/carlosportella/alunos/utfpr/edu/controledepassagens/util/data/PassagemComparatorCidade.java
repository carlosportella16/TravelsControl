package carlosportella.alunos.utfpr.edu.controledepassagens.util.data;

import java.util.Comparator;

import carlosportella.alunos.utfpr.edu.controledepassagens.util.Passagem;

public class PassagemComparatorCidade implements Comparator<Passagem> {
    @Override
    public int compare(Passagem passagem1, Passagem passagem2) {
        return passagem1.getCidade().compareTo(passagem2.getCidade());
    }
}
