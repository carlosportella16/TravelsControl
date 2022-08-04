package carlosportella.alunos.utfpr.edu.controledepassagens.util;

public enum TipoPassagem {

    RODOVIÁRIO, AÉREO;

    public static final int RODOVIARIO = 1;
    public static final int AEREO = 2;

    public static TipoPassagem verifica(int value) {
        if(value == 1) {
            return RODOVIÁRIO;
        }
        else {
            return AÉREO;
        }
    }
}
