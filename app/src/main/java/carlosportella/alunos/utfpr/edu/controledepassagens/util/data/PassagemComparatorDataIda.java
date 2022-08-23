package carlosportella.alunos.utfpr.edu.controledepassagens.util.data;

import java.util.Comparator;

import carlosportella.alunos.utfpr.edu.controledepassagens.util.Passagem;

public class PassagemComparatorDataIda implements Comparator<Passagem> {
    @Override
    public int compare(Passagem passagem1, Passagem passsagem2) {
        return passagem1.getDataIda().compareTo(passsagem2.getDataIda());
    }
}
