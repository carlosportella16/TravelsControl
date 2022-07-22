package carlosportella.alunos.utfpr.edu.controledepassagens.util.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DataConverter {

    private static  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static Date converteStringToDate(String value) throws ParseException {
        return simpleDateFormat.parse(value);
    }

    public static String converteDateToString(Date date) {

        return simpleDateFormat.format(date);
    }


}
