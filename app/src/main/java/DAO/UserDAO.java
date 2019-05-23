package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import METIER.Tel;
import METIER.USER;

public class UserDAO extends DAO<USER> {
    private SQLiteGRC dbGRC;

    private static final String TABLE_USER = "USER";
    private static final String COL_ID_USER = "IDUSER";
    private static final String COL_NONUSER = "NOMUSER";
    private static final String COL_PRENOMUSER = "PRENOMUSER";
    private static final String COL_EMAILUSER = "EMAILUSER";
    private static final String COL_MDPUSER = "MDPUSER";


    private SQLiteDatabase db;
    private Context moncontext;

    public UserDAO(Context context){
        dbGRC = new SQLiteGRC(context);
        moncontext = context;
    }

    public void open(){
        db = dbGRC.getWritableDatabase();
    }

    public void close(){
        dbGRC.close();

    }

    @Override
    public void insert(USER obj) {
        // insertion de la matière dans la base
        try{
            Log.v("ok","ok");
            ContentValues valeurIns = new ContentValues();
            valeurIns.put(COL_NONUSER,obj.getNom_user());
            valeurIns.put(COL_PRENOMUSER,obj.getPrenom_user());
            valeurIns.put(COL_EMAILUSER,obj.getEmail_user());
            valeurIns.put(COL_MDPUSER,obj.getMdp_user());
            // insert tel et Avoir en même temps.

            long x = db.insert(TABLE_USER, null, valeurIns);
            Log.v("indentifiant",String.valueOf(x));

        }
        catch (Exception e){
            Log.v("SQLError", e.toString());

        }
    }

    @Override
    public boolean update(USER obj) {
        return false;
    }

    @Override
    public boolean delete(USER obj) {
        return false;
    }

    // cheking if email exists and password too;
    public List<USER> read ()
    {
        // retourne la liste de tout les matieres enregistrées dans la base.
        List<USER> liste = null;
        //declaratio variable et cursor
        USER unUser;
        TelDAO telDAO = new TelDAO(moncontext);
        UserAvoirTelDAO userAvoirTelDAO;
        Cursor cursor;

        int IdUser;
        List<Integer> tel;
        String nomUser;
        String prenomUser;
        String telUser;
        String emailUser;
        String mdpUser;
        ArrayList<Tel> ListTel;

        liste = new ArrayList<USER>();
        //requete
        cursor = db.query(TABLE_USER, null,null,null,null, null,null);
        // fetch
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            IdUser = cursor.getInt(0);
            nomUser = cursor.getString(1);
            prenomUser = cursor.getString(2);
            emailUser = cursor.getString(3);
            mdpUser = cursor.getString(4);
            userAvoirTelDAO = new UserAvoirTelDAO(moncontext);
            userAvoirTelDAO.open();
            tel = userAvoirTelDAO.recupTelByIdUser(IdUser);
            userAvoirTelDAO.close();
            ListTel = new ArrayList<Tel>();
            for (Integer monTel:tel) {
                Tel leTel = null;
                telDAO.open();
                leTel = telDAO.read(monTel);
                telDAO.close();
                ListTel.add(leTel);
            }
            unUser = new USER(IdUser,nomUser,prenomUser,emailUser,mdpUser,ListTel);
            liste.add(unUser);
            cursor.moveToNext();
        }
        cursor.close();
        return liste;
    }

    public USER readByEmail(String email) {
        // recherche le numéro de matiere dans la base et la retourne
        Log.v("usertet",String.valueOf(email));
        USER unUser = null;
        TelDAO telDAO = new TelDAO(moncontext);
        UserAvoirTelDAO userAvoirTelDAO;
        Cursor cursor;
        int IdUser;
        String nomUser;
        String prenomUser;
        String telUser;
        String emailUser;
        String mdpUser;
        ArrayList<Tel> ListTel;
        List<Integer> tel;


        cursor = db.query(TABLE_USER, null, COL_EMAILUSER + "='"+email+"'", null, null, null, null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            IdUser = cursor.getInt(0);
            nomUser = cursor.getString(1);
            prenomUser = cursor.getString(2);
            emailUser = cursor.getString(3);
            mdpUser = cursor.getString(4);
           /* userAvoirTelDAO = new UserAvoirTelDAO(moncontext);
            userAvoirTelDAO.open();
            tel = userAvoirTelDAO.recupTelByIdUser(IdUser);
            userAvoirTelDAO.close();
            ListTel = new ArrayList<Tel>();
            for (Integer monTel:tel) {
                Tel leTel = null;
                telDAO.open();
                leTel = telDAO.read(monTel);
                telDAO.close();
                ListTel.add(leTel);
            }*/
            unUser = new USER(IdUser,nomUser,prenomUser,emailUser,mdpUser);
        }

        return unUser;
    }

    public USER readByEmail2(String email) {
        // recherche le numéro de matiere dans la base et la retourne
        Log.v("usertet",String.valueOf(email));
        USER unUser = null;
        TelDAO telDAO = new TelDAO(moncontext);
        UserAvoirTelDAO userAvoirTelDAO;
        Cursor cursor;
        int IdUser;
        String nomUser;
        String prenomUser;
        String telUser;
        String emailUser;
        String mdpUser;
        ArrayList<Tel> ListTel;
        List<Integer> tel;


        cursor = db.query(TABLE_USER, null, COL_EMAILUSER + "='"+email+"'", null, null, null, null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            IdUser = cursor.getInt(0);
            nomUser = cursor.getString(1);
            prenomUser = cursor.getString(2);
            emailUser = cursor.getString(3);
            mdpUser = cursor.getString(4);
           /* userAvoirTelDAO = new UserAvoirTelDAO(moncontext);
            userAvoirTelDAO.open();
            tel = userAvoirTelDAO.recupTelByIdUser(IdUser);
            userAvoirTelDAO.close();
            ListTel = new ArrayList<Tel>();
            for (Integer monTel:tel) {
                Tel leTel = null;
                telDAO.open();
                leTel = telDAO.read(monTel);
                telDAO.close();
                ListTel.add(leTel);
            }*/
            unUser = new USER(IdUser,nomUser,prenomUser,emailUser,mdpUser);
        }

        return unUser;
    }

    public USER read(int idUser) {
        // recherche le numéro de matiere dans la base et la retourne
        USER unUser = null;
        Cursor cursor;
        int IdUser;
        String nomUser;
        String prenomUser;
        String telUser;
        String emailUser;
        String mdpUser;
        ArrayList<Tel> ListTel;
        List<Integer> tel;
        TelDAO telDAO = new TelDAO(moncontext);
        UserAvoirTelDAO userAvoirTelDAO;


        cursor = db.query(TABLE_USER, null, COL_ID_USER + "="+idUser, null, null, null, null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            IdUser = cursor.getInt(0);
            nomUser = cursor.getString(1);
            prenomUser = cursor.getString(2);
            emailUser = cursor.getString(3);
            mdpUser = cursor.getString(4);
            userAvoirTelDAO = new UserAvoirTelDAO(moncontext);
            userAvoirTelDAO.open();
            tel = userAvoirTelDAO.recupTelByIdUser(IdUser);
            userAvoirTelDAO.close();
            ListTel = new ArrayList<Tel>();
            for (Integer monTel:tel) {
                Tel leTel = null;
                telDAO.open();
                leTel = telDAO.read(monTel);
                telDAO.close();
                ListTel.add(leTel);
            }

            unUser = new USER(IdUser, nomUser, prenomUser, emailUser,mdpUser,ListTel);
        }

        return unUser;
    }

}
