package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import METIER.Incident;
import METIER.Thematique;
import METIER.USER;

import static java.lang.String.valueOf;

public class IncidentDAO extends DAO<Incident> {

    private SQLiteGRC dbGRC;

    private static final String TABLE_INCIDENT = "INCIDENT";
    private static final String COL_ID_INCIDENT = "ID_INCIDENT";
    private static final String COL_ID_INCIDENT_USER = "ID_INCIDENT_USER";
    private static final String COL_ID_INCIDENT_THEME = "ID_INCIDENT_THEME";
    private static final String COL_PHOTO = "PHOTO";
    private static final String COL_LATITUDE = "LATITUDE";
    private static final String COL_LONGITUDE = "LONGITUDE";
    private static final String COL_DESCRIPTION = "DESCRIPTION";
    private static final String COL_DATE = "DATE";
    private static final String COL_ETAT = "ETAT";

    private Context moncontext;
    private SQLiteDatabase db;
    private int id;

    public IncidentDAO(Context context){
        dbGRC = new SQLiteGRC(context);
        moncontext =context;
    }

    public void open(){
        db = dbGRC.getWritableDatabase();
    }

    public void close(){
        dbGRC.close();

    }



    @Override
    public void insert(Incident obj) {
        ContentValues valeursSQL = new ContentValues();
        valeursSQL.put(COL_ID_INCIDENT_THEME,obj.getId_theme_incident().getId_theme());
        valeursSQL.put(COL_ID_INCIDENT_USER,obj.getId_user_incident().getId_user());
        valeursSQL.put(COL_PHOTO, obj.getPhoto());
        valeursSQL.put(COL_LATITUDE,obj.getLatitude());
        valeursSQL.put(COL_LONGITUDE,obj.getLongitude());
        valeursSQL.put(COL_DESCRIPTION, obj.getDescription());
        valeursSQL.put(COL_DATE, obj.getDate());
        valeursSQL.put(COL_ETAT, obj.getEtat());

        Log.v("insertTheme",valueOf(obj.getId_theme_incident().getId_theme()));

        db.insert(TABLE_INCIDENT, null, valeursSQL);
    }

    @Override
    public boolean update(Incident obj) {
        return false;
    }

    @Override
    public boolean delete(Incident obj) {
        return false;
    }

    public Incident read(int IdIncident){

        // recherche le numéro de matiere dans la base et la retourne
        Incident unIncident = null;
        Cursor cursor;
        int id_incident;
        USER id_user_incident;
        Thematique id_theme_incident;
        byte[] photo;
        double longitude;
        double latitude;
        String description;
        String date;
        String etat;


        cursor = db.query(TABLE_INCIDENT, null, COL_ID_INCIDENT + "=" + IdIncident, null, null, null, null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            id_incident = cursor.getInt(0);

            // faire un read user

            UserDAO userDAO = new UserDAO(moncontext);
            userDAO.open();
            id_user_incident = userDAO.read(cursor.getInt(1));
            userDAO.close();
            // faire un read theme
            ThematiqueDAO thematiqueDAO = new ThematiqueDAO(moncontext);
            thematiqueDAO.open();
            id_theme_incident = thematiqueDAO.read(cursor.getInt(2));

            thematiqueDAO.close();
            Log.v("reccupTheme",valueOf(cursor.getInt(2)));
            photo = cursor.getBlob(3);
            latitude = cursor.getDouble(4);
            longitude = cursor.getDouble(5);
            description = cursor.getString(6);
            date = cursor.getString(7);
            etat = cursor.getString(8);

            unIncident = new Incident(id_incident,id_user_incident,id_theme_incident,photo,latitude,longitude,description,date,etat);
        }

        return unIncident;
    }

    public Incident readPosition(int position) {
        // retourne l' incident enregistrées dans la base lorsque l'on a cliquer dessus dans la liste.

        //declaration variable et cursor
        Incident unIncident = null;
        Cursor cursor;
        int i;
        int id_incident;
        USER id_user_incident;
        Thematique id_theme_incident;
        byte[] photo;
        double longitude;
        double latitude;
        String description;
        String date;
        String etat;


        Log.v("position", valueOf(position));
        //requete
        cursor = db.query(TABLE_INCIDENT, null, null, null, null, null, null);
        // fetch
        cursor.moveToFirst();
        for (i = 0; i < position; i++) {
            cursor.moveToNext();
            Log.v("Positioni", valueOf(i));
        }


        /*IncidentDAO incidentDAO = new IncidentDAO(moncontext);
        incidentDAO.open();*/
        id_incident = cursor.getInt(0);
        /*Incident test = incidentDAO.read(cursor.getInt(0));
        incidentDAO.close();*/
        Log.v("GetIdIncident", valueOf(id_incident));
        // faire un read user

        UserDAO userDAO = new UserDAO(moncontext);
        userDAO.open();
        id_user_incident = userDAO.read(cursor.getInt(1));
        userDAO.close();
        // faire un read theme

        ThematiqueDAO thematiqueDAO = new ThematiqueDAO(moncontext);
        thematiqueDAO.open();
        id_theme_incident = thematiqueDAO.read(cursor.getInt(2));
        thematiqueDAO.close();

        Log.v("UserRecup", valueOf(cursor.getInt(2)));

        photo = cursor.getBlob(3);
        longitude = cursor.getDouble(4);
        latitude = cursor.getDouble(5);
        description = cursor.getString(6);
        date = cursor.getString(7);
        etat = cursor.getString(8);

        unIncident = new Incident(id_incident,id_user_incident,id_theme_incident,photo,longitude,latitude,description,date,etat);
        //Log.v("ClasseRecup", String.valueOf(cursor.getInt(2)));

        cursor.close();


        return unIncident;
    }

    public List<Incident> readList(int id) {
        // retourne la liste de tout les matieres enregistrées dans la base.
        List<Incident> liste = null;
        //declaratio variable et cursor
        Incident unIncident = null;
        Cursor cursor;
        int id_incident;
        USER id_user_incident;
        Thematique id_theme_incident;
        byte[] photo;
        double longitude;
        double latitude;
        String description;
        String date;
        String etat;

        liste = new ArrayList<Incident>();
        //requete
        //cursor = db.query(TABLE_INCIDENT, null, null, null, null, null, null);
        cursor = db.query(TABLE_INCIDENT, null, COL_ID_INCIDENT_USER + " = " + id, null, null, null, null);
        // fetch
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            id_incident = cursor.getInt(0);
            // faire un read user
            UserDAO userDAO = new UserDAO(moncontext);
            userDAO.open();
            id_user_incident = userDAO.read(cursor.getInt(1));
            userDAO.close();
            // faire un read theme
            ThematiqueDAO thematiqueDAO = new ThematiqueDAO(moncontext);
            thematiqueDAO.open();
            id_theme_incident = thematiqueDAO.read(cursor.getInt(2));
            thematiqueDAO.close();

            photo = cursor.getBlob(3);
            latitude = cursor.getDouble(4);
            longitude = cursor.getDouble(5);
            description = cursor.getString(6);
            date = cursor.getString(7);
            etat = cursor.getString(8);

            unIncident = new Incident(id_incident,id_user_incident,id_theme_incident,photo,latitude,longitude,description,date,etat);

            liste.add(unIncident);

            cursor.moveToNext();

        }

        cursor.close();


        return liste;
    }

    public byte[] readImage(int numIncident){

        // recherche le numéro de matiere dans la base et la retourne
        Incident unIncident = null;
        Cursor cursor;
        int id_incident;
        USER id_user_incident;
        Thematique id_theme_incident;
        byte[] photo;
        byte[] photo2;
        double longitude;
        double latitude;
        String description;
        String date;
        String etat;


        cursor =  db.query(TABLE_INCIDENT,null, COL_ID_INCIDENT + "=" + numIncident, null, null, null, null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            id_incident = cursor.getInt(0);

            // faire un read user

            UserDAO userDAO = new UserDAO(moncontext);
            userDAO.open();
            id_user_incident = userDAO.read(cursor.getInt(1));
            userDAO.close();
            // faire un read theme
            ThematiqueDAO thematiqueDAO = new ThematiqueDAO(moncontext);
            thematiqueDAO.open();
            id_theme_incident = thematiqueDAO.read(cursor.getInt(2));

            thematiqueDAO.close();
            Log.v("reccupTheme",valueOf(cursor.getInt(2)));
            photo = cursor.getBlob(3);
            latitude = cursor.getDouble(4);
            longitude = cursor.getDouble(5);
            description = cursor.getString(6);
            date = cursor.getString(7);
            etat = cursor.getString(8);

            unIncident = new Incident(id_incident,id_user_incident,id_theme_incident,photo,latitude,longitude,description,date,etat);
        }

        photo2 = unIncident.getPhoto();

        return photo2;

    }
}
