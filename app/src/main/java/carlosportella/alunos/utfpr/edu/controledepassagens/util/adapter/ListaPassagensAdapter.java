package carlosportella.alunos.utfpr.edu.controledepassagens.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import carlosportella.alunos.utfpr.edu.controledepassagens.R;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.Passagem;
import carlosportella.alunos.utfpr.edu.controledepassagens.util.data.DataConverter;

public class ListaPassagensAdapter extends BaseAdapter {

    Context context;
    List<Passagem> passagens;

    private static class PassagensHolder{
        public TextView textViewNomePais;
        public TextView textViewNomeCidade;
        public TextView textViewDataIda;
        public TextView textViewDataVolta;
        public TextView textViewTipoPassagem;
        public TextView textViewBagagem;
    }

    public ListaPassagensAdapter(Context context, List<Passagem> passagens) {
        this.context = context;
        this.passagens = passagens;
    }

    @Override
    public int getCount() {
        return passagens.size();
    }

    @Override
    public Object getItem(int i) {
        return passagens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PassagensHolder holder;
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.passagens_adapter,
                    viewGroup,
                    false);

            holder = new PassagensHolder();

            holder.textViewNomePais = view.findViewById(R.id.textViewNomePaisLista);
            holder.textViewNomeCidade = view.findViewById(R.id.textViewNomeCidadeLista);
            holder.textViewDataIda = view.findViewById(R.id.textViewDataIdaLista);
            holder.textViewDataVolta = view.findViewById(R.id.textViewDataVoltaLista);
            holder.textViewTipoPassagem = view.findViewById(R.id.textViewTipoPassLista);
            holder.textViewBagagem = view.findViewById(R.id.textViewBagagemLista);


            view.setTag(holder);
        } else {
            holder = (PassagensHolder) view.getTag();
        }

        holder.textViewNomePais.setText(passagens.get(i).getPais().getNome());
        holder.textViewNomeCidade.setText(passagens.get(i).getCidade());
        holder.textViewDataIda.setText(DataConverter.converteDateToString(passagens.get(i).getDataIda()));
        holder.textViewDataVolta.setText(DataConverter.converteDateToString(passagens.get(i).getDataVolta()));
        holder.textViewTipoPassagem.setText(passagens.get(i).getTipoPassagem().toString());
        holder.textViewBagagem.setText(passagens.get(i).isBagagem().toString());


        return view;
    }
}
