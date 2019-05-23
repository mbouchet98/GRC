package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import METIER.Thematique;

public class ThematiqueDAO extends DAO<Thematique>{

    private SQLiteGRC dbGRC;

    private static final String TABLE_THEME = "THEMATIQUE";
    private static final String COL_ID_THEME  = "IDTHEME ";
    private static final String COL_LIB_THEME  = "LIBTHEME";
    private static final String COL_DESC_THEME = "DESCTHEME ";

    private SQLiteDatabase db;

    public ThematiqueDAO(Context context){
        dbGRC = new SQLiteGRC(context);
    }

    public void open(){
        db = dbGRC.getWritableDatabase();
    }

    public void close(){
        dbGRC.close();

    }


    @Override
    public void insert(Thematique obj) {

    }

    @Override
    public boolean update(Thematique obj) {
        return false;
    }

    @Override
    public boolean delete(Thematique obj) {
        return false;
    }

    public List<Thematique> read(){
        // retourne la liste de tout les matieres enregistrées dans la base.
        List<Thematique> liste = null;
        //declaratio variable et cursor
        Thematique unTheme;
        Cursor cursor;

        int IdTheme;
        String LibTheme;
        String descTheme;

        liste = new ArrayList<Thematique>();
        //requete
        cursor = db.query(TABLE_THEME, null,null,null,null, null,null);
        // fetch
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            IdTheme = cursor.getInt(0);
            LibTheme = cursor.getString(1);
            descTheme = cursor.getString(2);

            unTheme = new Thematique(IdTheme,LibTheme,descTheme);
            liste.add(unTheme);

            cursor.moveToNext();

        }

        cursor.close();


        return liste;
    }

    public Thematique readPostion(int position){
        // retourne la liste de tout les matieres enregistrées dans la base.

        //declaratio variable et cursor
        Thematique uneThematique;
        Cursor cursor;
        int i;

        int IdTheme;
        String LibTheme;
        String DescTheme;
        Log.v("position",String.valueOf(position));
        //requete
        cursor = db.query(TABLE_THEME, null,null,null,null, null,null);
        // fetch
        cursor.moveToFirst();
        for (i=0;i<position;i++) {
            cursor.moveToNext();
            Log.v("Positioni",String.valueOf(i));
        }

        IdTheme = cursor.getInt(0);
        LibTheme = cursor.getString(1);
        DescTheme = cursor.getString(2);

        uneThematique = new Thematique(IdTheme,LibTheme,DescTheme);
        Log.v("coefRecup",String.valueOf(uneThematique.getDesc_theme()));

        cursor.close();


        return uneThematique;
    }

    public Thematique read(String titre) {
        // recherche le numéro de matiere dans la base et la retourne
        Thematique uneThematique = null;

        Cursor cursor;

        int IdTheme;
        String LibTheme;
        String descTheme;


        cursor = db.query(TABLE_THEME, null, COL_LIB_THEME + "='" + titre+"'", null,null, null, null, null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            IdTheme = cursor.getInt(0);
            LibTheme = cursor.getString(1);
            descTheme = cursor.getString(2);

            uneThematique = new Thematique(IdTheme, LibTheme, descTheme);
        }

        return uneThematique;
    }

    public Thematique read(int idtheme) {
        // recherche le numéro de matiere dans la base et la retourne
        Thematique uneThematique = null;

        Cursor cursor;

        int IdTheme;
        String LibTheme;
        String descTheme;


        cursor = db.query(TABLE_THEME, null, COL_ID_THEME + "="+idtheme, null,null, null, null, null);


        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            IdTheme = cursor.getInt(0);
            LibTheme = cursor.getString(1);
            descTheme = cursor.getString(2);

            uneThematique = new Thematique(IdTheme, LibTheme, descTheme);
        }

        return uneThematique;
    }
}
