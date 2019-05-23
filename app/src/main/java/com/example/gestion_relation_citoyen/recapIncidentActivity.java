package com.example.gestion_relation_citoyen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import DAO.IncidentDAO;
import util.preferenceUtils;

public class recapIncidentActivity extends AppCompatActivity {

    private TextView textViewNumeroIncident;
    private TextView textViewTheme;
    private TextView textViewEtat;
    private TextView textViewDate;
    private TextView textViewUser;
    private TextView textViewLat;
    private TextView textViewLong;
    private TextView textViewDesc;
    private TextView textViewImg;
    private ImageView imgForm;
    private TextView textViewAdresse;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapincident);

        objet();
        recupData();
    }

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

    public void objet(){
        textViewNumeroIncident = (TextView)findViewById(R.id.textViewNumIncident);
        textViewTheme = (TextView)findViewById(R.id.textViewTheme);
        textViewEtat =(TextView)findViewById(R.id.textViewEtat);
        textViewDate =(TextView)findViewById(R.id.textViewDate);
        textViewUser=(TextView)findViewById(R.id.textViewUser);
        textViewLat = (TextView)findViewById(R.id.textViewLat);
        textViewLong =(TextView)findViewById(R.id.textViewLong);
        textViewDesc =(TextView)findViewById(R.id.textViewDesc);
        textViewImg=(TextView)findViewById(R.id.textViewImg);
        imgForm=(ImageView)findViewById(R.id.imgForm);
        textViewAdresse = (TextView)findViewById(R.id.textViewAdresse);
    }

    public void recupData() {

        /* Recuppération des données */
        String paramNumIncident = this.getIntent().getStringExtra("paramNumIncident");
        String paramTheme = this.getIntent().getStringExtra("paramTheme");
        String paramEtat = this.getIntent().getStringExtra("paramEtat");
        String paramDate = this.getIntent().getStringExtra("paramDate");
        String paramUser = this.getIntent().getStringExtra("paramUser");
        String paramLat = this.getIntent().getStringExtra("paramLat");
        String paramLon = this.getIntent().getStringExtra("paramLon");
        String paramDesc = this.getIntent().getStringExtra("paramDesc");

        /* Convertion long et lat en Adresse Complète */

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(Double.valueOf(paramLat),Double.valueOf(paramLon), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0);
            textViewAdresse.setText("Adresse : "+ address);

        } catch (IOException e) {
            e.printStackTrace();
        }


        /* Recup image et convertion de celle-ci */

        byte[] incidentImage = null;
        final IncidentDAO unIncidentDAO = new IncidentDAO(this);
        unIncidentDAO.open();
        incidentImage = unIncidentDAO.readImage(Integer.valueOf(paramNumIncident));
        unIncidentDAO.close();

        if (incidentImage != null) {
            InputStream is = new ByteArrayInputStream(incidentImage);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            // Set the ImageView image from drawable
            imgForm.setImageDrawable(drawable);
        }
        else {
            textViewImg.setText("Vous n'avais pas inserer d'image ");
            imgForm.setVisibility(View.INVISIBLE);
        }


        /* Affichage */

        textViewNumeroIncident.setText("N°Incident : "+paramNumIncident);
        textViewTheme.setText("Thematique : "+paramTheme);
        textViewEtat.setText("Etat : "+paramEtat);
        textViewDate.setText("Date : "+paramDate);
        textViewUser.setText("Utilisateur : "+paramUser);
        textViewLat.setText("Latitude : "+paramLat);
        textViewLong.setText("Longitude : "+paramLon);
        textViewDesc.setText("Description : "+paramDesc);
    }
}
