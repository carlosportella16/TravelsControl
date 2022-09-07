package carlosportella.alunos.utfpr.edu.controledepassagens.util;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

public class UtilsGUI {

    public static void avisoErro(Context contexto, int idTexto){

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        //TODO tirar string
        builder.setTitle("aviso");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(idTexto);

        //TODO tirar string
        builder.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void confirmaAcao(Context contexto,
                                    String mensagem,
                                    DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle("Confirmacao");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(mensagem);

        builder.setPositiveButton("Yes", listener);
        builder.setNegativeButton("NO", listener);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static String validaCampoTexto(Context contexto,
                                          EditText editText,
                                          int idMensagemErro){

        String texto = editText.getText().toString();

        if (UtilsString.stringVazia(texto)){
            UtilsGUI.avisoErro(contexto, idMensagemErro);
            editText.setText(null);
            editText.requestFocus();
            return null;
        }else{
            return texto.trim();
        }
    }
}
