package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import METIER.Tel;

public class TelDAO extends DAO<Tel> {

    private SQLiteGRC dbGRC;

    private static final String TABLE_TEL = "TEL";
    private static final String COL_ID_TEL = "ID_TEL";
    private static final String COL_NUM_TEL = "NUMERO_TEL";


    private SQLiteDatabase db;

    public TelDAO(Context context){
        dbGRC = new SQLiteGRC(context);
    }

    public void open(){
        db = dbGRC.getWritableDatabase();
    }

    public void close(){
        dbGRC.close();
    }

    @Override
    public void insert(Tel obj) {
        // insertion des TEL dans la base
       try{

        ContentValues valeurIns = new ContentValues();
        valeurIns.put(COL_NUM_TEL,obj.getNumeroTel());
        long x = db.insert(TABLE_TEL, null, valeurIns);
        Log.v("indentifiant",String.valueOf(x));

        }
        catch (Exception e){
            Log.v("SQLError", e.toString());

        }
    }

    @Override
    public boolean update(Tel obj) {
        return false;
    }

    @Override
    public boolean delete(Tel obj) {
        return false;
    }

    public Tel read(int idTel){
        Tel unTel = null;
        Cursor cursor;
        int IdTel;
        String numTel;

        cursor = db.query(TABLE_TEL, null,COL_ID_TEL+"="+idTel,null,null,null,null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            IdTel = cursor.getInt(0);
            numTel = cursor.getString(1);

            unTel = new Tel(IdTel,numTel);

            cursor.moveToNext();
        }

    return unTel;
    }

    public Tel read(){
        Tel unTel = null;
        Cursor cursor;
        int IdTel;
        String numTel;

        cursor = db.query(TABLE_TEL, null,null,null,null,null,null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            IdTel = cursor.getInt(0);
            numTel = cursor.getString(1);

            unTel = new Tel(IdTel,numTel);

            cursor.moveToNext();
        }

        return unTel;
    }


    public List<Tel> readAll(){
        List<Tel> lesTels = new ArrayList<Tel>();
        Tel unTel = null;
        Cursor cursor;
        int IdTel;
        String numTel;

        cursor = db.query(TABLE_TEL, null,null,null,null,null,null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            IdTel = cursor.getInt(0);
            numTel = cursor.getString(1);

            unTel = new Tel(IdTel,numTel);
            lesTels.add(unTel);

            cursor.moveToNext();
        }

        return lesTels;
    }

    public Tel readByNum(String num){
        Tel unTel = null;
        Cursor cursor;
        int IdTel;
        String numTel;

        cursor = db.query(TABLE_TEL, null,COL_NUM_TEL+ "='"+num+"'",null,null,null,null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            IdTel = cursor.getInt(0);
            numTel = cursor.getString(1);

            unTel = new Tel(IdTel,numTel);

            cursor.moveToNext();
        }

        return unTel;
    }
}
