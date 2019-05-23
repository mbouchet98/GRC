package com.example.gestion_relation_citoyen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import METIER.Incident;

public class Suivie_Adapter_Activity extends ArrayAdapter<Incident> {

    private int resourceLayout;
    private Context mContext;

    public Suivie_Adapter_Activity(Context context, int resource, List<Incident> uneList) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_adapteur_suivie,parent, false);
        }

        MatiereViewHolder viewHolder = (MatiereViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MatiereViewHolder();
            viewHolder.numIncident = (TextView) convertView.findViewById(R.id.textViewNumincident);
            viewHolder.etatIncident = (TextView) convertView.findViewById(R.id.textViewEtatIncident);
            viewHolder.descIncident = (TextView) convertView.findViewById(R.id.textViewDescriptionIncident);
            convertView.setTag(viewHolder);
        }


        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Incident incident = getItem(position);
        Log.v("incident", incident.getId_incident()+ incident.getDescription());

        //il ne reste plus qu'à remplir notre vue
        viewHolder.numIncident.setText(String.valueOf(incident.getId_incident()));
        viewHolder.etatIncident.setText(incident.getDescription());
        viewHolder.descIncident.setText(incident.getEtat());

        //nous renvoyons notre vue à l'adapter, afin qu'il l'affiche
        //et qu'il puisse la mettre à recycler lorsqu'elle sera sortie de l'écran
        return convertView;
    }

    class MatiereViewHolder {
        public TextView numIncident;
        public TextView etatIncident;
        public TextView descIncident;
    }
}
