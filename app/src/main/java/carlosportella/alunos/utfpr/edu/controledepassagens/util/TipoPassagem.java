package carlosportella.alunos.utfpr.edu.controledepassagens.util;

public enum TipoPassagem {
    RODOVIÁRIO, AÉREO;

    public static TipoPassagem verifica(String value) {
        if(value.equals("Rodoviário")) {
            return RODOVIÁRIO;
        }
        else {
            return AÉREO;
        }
    }
}
