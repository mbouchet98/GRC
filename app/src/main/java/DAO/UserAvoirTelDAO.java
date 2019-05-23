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

public class UserAvoirTelDAO extends DAO<USER> {

    private SQLiteGRC dbGRC;

    private static final String TABLE_USER_AVOIR_TEL = "AVOIR";
    private static final String COL_ID_TEL= "ID_TEL";
    private static final String COL_ID_USER = "ID_USER";

    private SQLiteDatabase db;
    private Context moncontext;

    public UserAvoirTelDAO(Context context){
        dbGRC = new SQLiteGRC(context);
        moncontext = context;
    }

    public void open(){
        db = dbGRC.getWritableDatabase();
    }

    public void close() {
        dbGRC.close();

    }

        @Override
    public void insert(USER obj) {
        // insertion de la matière dans la base
         /*   TelDAO telDAO = new TelDAO(moncontext);
            try{
                Log.v("ok","ok");
                ContentValues valeurIns = new ContentValues();
                List<Tel> listTel = obj.getMesTel();
                for (Tel tel:listTel) {
                    // recup le numéro de téléphone


                }
                telDAO.open();
                Log.v("readTelId",String.valueOf(obj.getMesTel()));
                Log.v("readTelUSER",String.valueOf(obj.getId_user()));
                //valeurIns.put(COL_ID_TEL,monTel.getId_Tel());
                //valeurIns.put(COL_ID_USER,obj.getId_user());

                long x = db.insert(TABLE_USER_AVOIR_TEL, null, valeurIns);
                Log.v("indentifiant",String.valueOf(x));

            }
            catch (Exception e){
                Log.v("SQLError", e.toString());

            }*/
    }


    public void insert(USER user, Tel tel){
        try {
            Log.v("ok","ok");
            ContentValues valeurIns = new ContentValues();
            valeurIns.put(COL_ID_TEL,tel.getId_Tel());
            valeurIns.put(COL_ID_USER,user.getId_user());
            long x = db.insert(TABLE_USER_AVOIR_TEL, null, valeurIns);
            Log.v("AVOIR_USER",String.valueOf(x));
        }catch (Exception e){
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

    public List<Integer> recupTelByIdUser(int user){

        List<Integer> LesTels = new ArrayList<>();
        Tel unTel;
        Cursor cursor;

        int idTel;

       // lesTelsUSER = new HashMap<>();
        //requete
        cursor = db.query(TABLE_USER_AVOIR_TEL, new String[]{COL_ID_TEL},COL_ID_USER+"="+user,null,null, null,null);
        // fetch
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            idTel = cursor.getInt(0);

            LesTels.add(idTel);
            cursor.moveToNext();
        }
        cursor.close();
        return LesTels;

    }



}
