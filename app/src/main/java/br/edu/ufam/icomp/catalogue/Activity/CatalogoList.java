package br.edu.ufam.icomp.catalogue.Activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.edu.ufam.icomp.catalogue.Entidades.Catalogacao;
import br.edu.ufam.icomp.catalogue.R;

public class CatalogoList extends ArrayAdapter<Catalogacao> {
    private Activity context;
    List<Catalogacao> listCatalogacao;

    public CatalogoList(Activity context, List<Catalogacao> listCatalogacao){
        super(context, R.layout.layout_cat_list, listCatalogacao);
        this.context = context;
        this.listCatalogacao = listCatalogacao;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_cat_list, null, true);

        ImageView imgNome = (ImageView) listViewItem.findViewById(R.id.imageView_imagem_cat);
        TextView tvNome = (TextView) listViewItem.findViewById(R.id.textView_nomeCatalogo);
        TextView tvStatus = (TextView) listViewItem.findViewById(R.id.textView_status_catalogo);

        Catalogacao ctl = listCatalogacao.get(position);
        Glide.with(context).load(ctl.getImagemUrl()).into(imgNome);
        tvNome.setText(ctl.getcName());
        tvStatus.setText(ctl.getStatus());

        return listViewItem;
    }

}
