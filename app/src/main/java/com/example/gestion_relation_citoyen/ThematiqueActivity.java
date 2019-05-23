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

import java.util.List;

import DAO.ThematiqueDAO;
import DAO.UserDAO;
import METIER.Thematique;
import util.preferenceUtils;

public class ThematiqueActivity extends AppCompatActivity {

    private UserDAO userdao;
    private TextView txtName;
    private ListView listThematique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thematique);
        objet();



        Intent intent = getIntent();
        if (intent.hasExtra("EMAIL")){
            String nameFromInstent = getIntent().getStringExtra("EMAIL");
            txtName.setText("Welcome " + nameFromInstent);
        }else {
            String email = preferenceUtils.getEmail(this);
            txtName.setText("Welcome "+ email);
        }

        List();

    }

    protected void onStart() {
        super.onStart();

        List();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.thematique_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_suivie:
                Intent intent2 = new Intent(this,SuivieActivity.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.id_quit:
                // mais reste connecté.

                finish();
                return true;
            case R.id.id_deco:
                preferenceUtils.savePassword(null,this);
                preferenceUtils.saveEmail(null,this);
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void objet(){
        txtName = (TextView)findViewById(R.id.textViewName);
        listThematique = (ListView)findViewById(R.id.islisteThematique);
    }

    private  void List(){
        List<Thematique> uneList;

        final ThematiqueDAO uneThematique = new ThematiqueDAO(this);

        uneThematique.open();

        uneList = uneThematique.read();

        uneThematique.close();

        // adapter de la liste thématique.
        Thematique_Adapter_Activity adapter = new Thematique_Adapter_Activity(this, R.layout.activity_thematique, uneList);
        listThematique.setAdapter(adapter);
        listThematique.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2;
                Thematique laThematique;
                uneThematique.open();
                laThematique = uneThematique.readPostion(position);
                uneThematique.close();

                intent2 = new Intent(getApplicationContext(), IncidentActivity.class);
                if (intent2.hasExtra("EMAIL")){
                    String nameFromInstent = getIntent().getStringExtra("EMAIL");
                    intent2.putExtra("EMAIL",nameFromInstent);
                }else {
                    String email = preferenceUtils.getEmail(getApplicationContext());
                    intent2.putExtra("EMAIL",email);
                }

                /*Intent intent3 = new Intent(getApplicationContext(), SuivieActivity.class);
                if (intent3.hasExtra("EMAIL")){
                    String nameFromInstent = getIntent().getStringExtra("EMAIL");
                    intent3.putExtra("EMAIL",nameFromInstent);
                }else {
                    String email = preferenceUtils.getEmail(getApplicationContext());
                    intent3.putExtra("EMAIL",email);
                }*/

                intent2.putExtra("paramLib", laThematique.getLib_theme());
                intent2.putExtra("paramDesc", laThematique.getDesc_theme());
                //intent.putExtra("paramCoef",laMatiere.getCoefMetier());
                intent2.putExtra("paramId", String.valueOf(laThematique.getId_theme()));
                startActivity(intent2);
                finish();
            }

        });
    }
}
