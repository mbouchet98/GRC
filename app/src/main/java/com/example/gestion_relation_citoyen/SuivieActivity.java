package com.example.gestion_relation_citoyen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DAO.IncidentDAO;
import DAO.UserDAO;
import METIER.Incident;
import METIER.USER;
import util.preferenceUtils;

public class SuivieActivity extends AppCompatActivity {

    private ListView listeInscident;
    private TextView textViewTitreSuivis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suivie);

        objet();

        List();
    }

    // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.incident_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_quit:
                finish();
                return true;
            case R.id.id_deco:
                preferenceUtils.savePassword(null, this);
                preferenceUtils.saveEmail(null, this);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.id_theme:
                Intent intent1 = new Intent(this, ThematiqueActivity.class);
                startActivity(intent1);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void objet(){
        listeInscident = (ListView)findViewById(R.id.listeInscident);
        textViewTitreSuivis = (TextView)findViewById(R.id.textViewTitreSuivis);
    }

    public void List(){
        List<Incident> uneList = null;
        USER monUser = null;

        final IncidentDAO unIncident = new IncidentDAO(this);
        final UserDAO unUserDAO = new UserDAO(this);
        uneList = new ArrayList<Incident>();

        /*Intent intent = getIntent();
        if (intent.hasExtra("EMAIL")){
            String user = getIntent().getStringExtra("EMAIL");
            Log.v("testUser",String.valueOf(user));
            unUserDAO.open();
            monUser = unUserDAO.readByEmail(user);
            iduser = monUser.getId_user();
            unUserDAO.close();
        }*/

        Intent intent = getIntent();
        if (intent.hasExtra("EMAIL")){
            String nameFromInstent = getIntent().getStringExtra("EMAIL");
            unUserDAO.open();
            monUser = unUserDAO.readByEmail(nameFromInstent);
            //textViewTitreSuivis.setText("Welcome " + monUser.getId_user());
            unUserDAO.close();
        }else {
            String email = preferenceUtils.getEmail(this);
            unUserDAO.open();
            monUser = unUserDAO.readByEmail(email);
            //textViewTitreSuivis.setText("Welcome "+ monUser.getId_user());
            unUserDAO.close();
        }
        unIncident.open();

        uneList = unIncident.readList(monUser.getId_user());

        unIncident.close();

        // adapter de la liste thématique.
        Suivie_Adapter_Activity adapter = new Suivie_Adapter_Activity(this, R.layout.activity_suivie, uneList);
        listeInscident.setAdapter(adapter);

        // quand on click sur un élément de la list.

        final List<Incident> finalUneList = uneList;
        listeInscident.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Incident lIncident;

                unIncident.open();
                lIncident = unIncident.read(finalUneList.get(position).getId_incident());
                unIncident.close();

                Intent intent2 = new Intent(getApplicationContext(), recapIncidentActivity.class);
                intent2.putExtra("paramNumIncident", String.valueOf(lIncident.getId_incident()));
                intent2.putExtra("paramTheme", String.valueOf(lIncident.getId_theme_incident().getLib_theme()));
                intent2.putExtra("paramEtat", lIncident.getEtat());
                intent2.putExtra("paramDate",lIncident.getDate());
                intent2.putExtra("paramUser", lIncident.getId_user_incident().getEmail_user());
                intent2.putExtra("paramLat",  String.valueOf(lIncident.getLatitude()));
                intent2.putExtra("paramLon", String.valueOf(lIncident.getLongitude()));
                intent2.putExtra("paramDesc", lIncident.getDescription());

                startActivity(intent2);
                finish();
            }

        });
    }
}
