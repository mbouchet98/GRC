package com.example.gestion_relation_citoyen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import METIER.Thematique;

public class Thematique_Adapter_Activity  extends ArrayAdapter<Thematique> {
    private int resourceLayout;
    private Context mContext;
    public Thematique_Adapter_Activity(Context context, int resource, List<Thematique> uneList) {
        super(context, resource, uneList);

        this.resourceLayout = resource;
        this.mContext = context;

    }

    //convertView est notre vue recyclée
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Android nous fournit un convertView null lorsqu'il nous demande de la créer
        //dans le cas contraire, cela veux dire qu'il nous fournit une vue recyclée
        if(convertView == null){
            //Nous récupérons notre row_tweet via un LayoutInflater,
            //qui va charger un layout xml dans un objet View
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapteur_thematique,parent, false);
        }

        MatiereViewHolder viewHolder = (MatiereViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MatiereViewHolder();
            viewHolder.LibTheme = (TextView) convertView.findViewById(R.id.textViewLibelle);
            viewHolder.DescTheme = (TextView) convertView.findViewById(R.id.textViewDescription);
            convertView.setTag(viewHolder);
        }


        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Thematique thematique = getItem(position);
        Log.v("matiere", thematique.getLib_theme()+ thematique.getDesc_theme());

        //il ne reste plus qu'à remplir notre vue
        viewHolder.LibTheme.setText(thematique.getLib_theme());
        viewHolder.DescTheme.setText(thematique.getDesc_theme());

        //nous renvoyons notre vue à l'adapter, afin qu'il l'affiche
        //et qu'il puisse la mettre à recycler lorsqu'elle sera sortie de l'écran
        return convertView;
    }

    class MatiereViewHolder {
        public TextView LibTheme;
        public TextView DescTheme;
    }
}
