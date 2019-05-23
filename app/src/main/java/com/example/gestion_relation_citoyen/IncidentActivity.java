package com.example.gestion_relation_citoyen;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DAO.IncidentDAO;
import DAO.ThematiqueDAO;
import DAO.UserDAO;
import METIER.Incident;
import METIER.Thematique;
import METIER.USER;
import util.preferenceUtils;

public class IncidentActivity extends AppCompatActivity implements LocationListener {

    private TextView titreIncident;
    private LocationManager lm;
    private MapFragment mapFragment;
    private GoogleMap googleMap;
    private static final int PERMS_CALL_ID = 1234;

    private EditText inputAdresse;

    private ImageView imageForm;
    private Button bntRecupImage;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private  Button bntenvoisIncisent;
    private double latitude ;
    private double longitude ;
    private EditText desc;
    private String dateR;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);

        FragmentManager fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);



        objet();
        titre();


        bntRecupImage.setOnClickListener(clickBtnRecupImg);
        bntenvoisIncisent.setOnClickListener(clickBtnEnvoisIncident);
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


    // declaration de objet
    private void objet() {
        titreIncident = (TextView) findViewById(R.id.textViewRecupLibTheme);
        inputAdresse = (EditText)findViewById(R.id.input_search);
        imageForm = (ImageView)findViewById(R.id.ImgForm);
        bntRecupImage = (Button)findViewById(R.id.btnRecupImg);
        bntenvoisIncisent = (Button)findViewById(R.id.btnEvoisIncident);
        desc = (EditText)findViewById(R.id.txtboxDescIncident);
    }

    // titre
    private void titre() {
        String paramId = this.getIntent().getStringExtra("paramId");
        String paramLib = this.getIntent().getStringExtra("paramLib");
        String paramDesc = this.getIntent().getStringExtra("paramDesc");

        titreIncident.setText(paramLib);
    }


    // map

    private void init(){
        //Log.d(this, "init : initializing");

        inputAdresse.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == event.ACTION_DOWN || event.getAction() == event.KEYCODE_ENTER){
                    // execute la methode de recherche.
                    geoLocate();
                }

                return false;
            }
        });
    }

    private void geoLocate(){
        //Log.d(this, "geoLocate : geolocating");

        String searchString = inputAdresse.getText().toString();

        Geocoder geocoder = new Geocoder(this);

        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString,1);
        }catch (IOException e){
            // Log.v(,"geoLocate : IOException "+ e.getMessage());
            Toast.makeText(this,"geoLocate : IOException "+ e.getMessage(),Toast.LENGTH_LONG).show();
        }
        if (list.size()>0){
            Address address = list.get(0);
            //Log.d(this,"geolocate : found a location : "+address.toString());
            //Toast.makeText(this,"geolocate : found a location : "+address.toString(),Toast.LENGTH_LONG).show();


            //googleMap.addMarker(new MarkerOptions().position(new LatLng(46.64,-0.2)).title("ok").draggable(true));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(address.getLatitude(),address.getLongitude()));
            markerOptions.title(address.getAddressLine(0));
            // sauvegarde des coordonnés afin de pouvoire les stocker.
            latitude = address.getLatitude();
            longitude = address.getLongitude();



            googleMap.clear();

            googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude,longitude)));

            googleMap.addMarker(markerOptions);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMS_CALL_ID);
            return;
        }
        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // tout les heures vérifie les permissions.
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 	3600000, 0, this);
        }
        if (lm.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 	3600000, 0, this);
        }
        if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 	3600000, 0, this);
        }
        loadMap();
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMS_CALL_ID) {
            checkPermission();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (lm != null) {
            lm.removeUpdates(this);
        }
    }

    private void loadMap() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(final GoogleMap googleMap) {
                IncidentActivity.this.googleMap = googleMap;
                googleMap.moveCamera(CameraUpdateFactory.zoomBy(8));
                if (ActivityCompat.checkSelfPermission(IncidentActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(IncidentActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // Setting a click event handler for the map
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng latLng) {

                        Geocoder geocoder = new Geocoder(IncidentActivity.this);

                        List<Address> list = new ArrayList<>();

                        try {
                            list = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (list.size()>0){
                            Address address = list.get(0);


                            // Creating a marker
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Setting the position for the marker
                            markerOptions.position(latLng).title(address.getAddressLine(0));


                            latitude = address.getLatitude();
                            longitude = address.getLongitude();

                            // Setting the title for the marker.
                            // This will be displayed on taping the marker
                            //markerOptions.title(latLng.toString());
                            //googleMap.addMarker(new MarkerOptions().position(new LatLng(46.64,-0.2)).title("ok").draggable(true));

                            // Clears the previously touched position
                            googleMap.clear();

                            // Animating to the touched position
                            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                            // Placing a marker on the touched position
                            googleMap.addMarker(markerOptions);

                        }
                    }
                });
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();


        if (googleMap != null){
            LatLng googleLocation = new LatLng(latitude,longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation));
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String newStatus = "";
        switch (status){
            case LocationProvider
                    .OUT_OF_SERVICE:
                newStatus = "OUT_OF_SERVICE";
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                newStatus = "TEMPORARILY_UNAVAILABLE";
                break;
            case LocationProvider.AVAILABLE:
                newStatus = "AVAILABLE";
                break;
        }
        Log.i("LocationProject","onStatusChanged " + newStatus);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("ProviderEnabled", provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("ProviderDisabled", provider);
    }

    // input photo

    private View.OnClickListener clickBtnRecupImg = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recupImg();
        }
    };

    private void recupImg(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int resquestCode, int resultCode, Intent data){
        super.onActivityResult(resquestCode,resultCode,data);
        if (resultCode == RESULT_OK && resquestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageForm.setImageURI(imageUri);
        }
    }


    // envois incident.
    private View.OnClickListener clickBtnEnvoisIncident = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            envoiIncident();
        }
    };

    private  void envoiIncident(){
        byte[] photoRec=null;
        USER monUser = null;
        Thematique maThemetique = null;
        final UserDAO unUserDAO = new UserDAO(this);
        final ThematiqueDAO uneThematiqueDAO = new ThematiqueDAO(this);
        //recup le thematique
        String theme = String.valueOf(titreIncident.getText());
        uneThematiqueDAO.open();
        maThemetique = uneThematiqueDAO.read(theme);
        uneThematiqueDAO.close();

        Intent intent = getIntent();
        if (intent.hasExtra("EMAIL")){
            String user = getIntent().getStringExtra("EMAIL");
            unUserDAO.open();
            monUser = unUserDAO.readByEmail(user);
            unUserDAO.close();
        }

        double lat = latitude;
        double lon = longitude;

        String description = String.valueOf(desc.getText());

        // il faut recup l'image et pouvoir l'insert ?
        if(imageForm != null){
            Bitmap photo = ((BitmapDrawable) imageForm.getDrawable()).getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            photoRec = bArray;

        }


        LocalDate date = LocalDate.now(); // gets the current date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dateR = date.format(formatter);


        Incident incidentAdd = null;
        final IncidentDAO unIncident = new IncidentDAO(this);
        unIncident.open();


        // envois l'email de l'user sur une autre activity
        /*Intent intent2 = new Intent(getApplicationContext(), SuivieActivity.class);
        if (intent2.hasExtra("EMAIL")){
            String nameFromInstent = getIntent().getStringExtra("EMAIL");

            intent2.putExtra("EMAIL",nameFromInstent);
        }else {
            String email = preferenceUtils.getEmail(getApplicationContext());
            intent2.putExtra("EMAIL",email);
        }

        intent.putExtra("EMAIL",email.getText().toString().trim());
        emptyInputEditText();
        startActivity(intent);*/

        String etat = "en cour de traitement";

        //quand l'utilisateur déclare un incident, ce la doit créer automatiquement un suivi.
        incidentAdd = new Incident(monUser,maThemetique,photoRec,lat,lon,description,dateR,etat);
        unIncident.insert(incidentAdd);
        unIncident.close();




        // une fois inseret dans la base on doit être rediriger vers une autre page.
        Intent intent1;
        intent1 = new Intent(this, ThematiqueActivity.class);
        startActivity(intent1);
        finish();

    }
}
