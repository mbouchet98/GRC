package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteGRC extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 28;
    private static final String DATABASE_NAME = "GRC";
    private Context context = null;

    public SQLiteGRC(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            Log.v("test","connection");

            db.execSQL("DROP TABLE IF EXISTS INCIDENT");
            db.execSQL("DROP TABLE IF EXISTS USER");
            db.execSQL("CREATE TABLE USER (IDUSER INTEGER PRIMARY KEY AUTOINCREMENT, NOMUSER VARCHAR(20), PRENOMUSER VARCHAR(20), EMAILUSER VARCHAR(25), MDPUSER VARCHAR(25))");
            db.execSQL("INSERT INTO USER (NOMUSER, PRENOMUSER,EMAILUSER,MDPUSER) VALUES ('BOUCHET','MAELYS','BOUCHETMAE@CC-PARTHENAY-GATINE.FR','MDPapp123@')");
            db.execSQL("INSERT INTO USER (NOMUSER, PRENOMUSER,EMAILUSER,MDPUSER) VALUES('MARLANT','OLIVIER','MARLANTOLI@CC-PARTHENAY-GATINE.FR','MDPappOLI123@')");
            db.execSQL("INSERT INTO USER (NOMUSER, PRENOMUSER,EMAILUSER,MDPUSER) VALUES ('JENNIFER','JOURDAIN','JOURDAINJENNY@CC-PARTHENAY-GATINE.FR','MDPappJENNY123@')");
            Cursor c = db.query("USER",new String[]{"NOMUSER","PRENOMUSER","EMAILUSER","MDPUSER"},null,null,null,null,null);
            c.moveToFirst();
            Log.v("user",c.getString(0));
            db.execSQL("DROP TABLE IF EXISTS THEMATIQUE");
            db.execSQL("CREATE TABLE THEMATIQUE (IDTHEME INTEGER PRIMARY KEY AUTOINCREMENT, LIBTHEME VARCHAR(50), DESCTHEME VARCHAR(350))");
            db.execSQL("INSERT INTO THEMATIQUE (LIBTHEME, DESCTHEME) VALUES ('ANIMAUX ABANDONER','Signaler un animal hérant, ou abandonnée')");
            db.execSQL("INSERT INTO THEMATIQUE (LIBTHEME, DESCTHEME) VALUES ('DECHET','Signaler un vole de poubelle, des ordure non ramasser')");
            db.execSQL("INSERT INTO THEMATIQUE (LIBTHEME, DESCTHEME) VALUES ('RURAL','Signaler une empoule de lampadaire qui ne s allume plus, ou de graffiti.')");
            Cursor cursor = db.query("THEMATIQUE",new String[]{"LIBTHEME","DESCTHEME"},null,null,null,null,null);
            cursor.moveToFirst();
            Log.v("thematique",cursor.getString(0));

            db.execSQL("CREATE TABLE INCIDENT (ID_INCIDENT INTEGER PRIMARY KEY AUTOINCREMENT, ID_INCIDENT_USER INTEGER REFERENCES USER(IDUSER), ID_INCIDENT_THEME INTEGER REFERENCES THEMATIQUE(IDTHEME),PHOTO BLOB,LATITUDE DOUBLE,LONGITUDE DOUBLE,DESCRIPTION VARCHAR(250),DATE VARCHAR(10),ETAT VARCHAR(25))");
           // db.execSQL("INSERT INTO INCIDENT (ID_INCIDENT, ID_INCIDENT_USER, ID_INCIDENT_THEME,PHOTO,LATITUDE,LONGITUDE,DESCRIPTION,ETAT,ETAT)VALUES ()");
           // Cursor cur = db.query("INCIDENT",new String[]{"ID_INCIDENT_USER","ID_INCIDENT_USER","PHOTO","LATITUDE","LONGITUDE","DESCRIPTION","DATE","ETAT"},null,null,null,null,null);
           // cur.moveToFirst();
           // Log.v("incident",cur.getString(0));



        }catch (Exception e){
            Log.v("SQLError", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS AVOIR");
        db.execSQL("CREATE TABLE AVOIR (ID_TEL INTEGER REFERENCES TEL(ID_TEL), ID_USER INTEGER REFERENCES USER(IDUSER) , PRIMARY KEY(ID_TEL,ID_USER) )");
        /*db.execSQL("INSERT INTO AVOIR (ID_TEL,ID_USER) VALUES (1,1)");
        db.execSQL("INSERT INTO AVOIR (ID_TEL,ID_USER) VALUES(2,1)");
        db.execSQL("INSERT INTO AVOIR (ID_TEL,ID_USER) VALUES (1,3)");*/
        Cursor cus = db.query("AVOIR",new String[]{"ID_TEL","ID_USER"},null,null,null,null,null);
        cus.moveToFirst();
        //Log.v("avoir",cus.getString(0));

        db.execSQL("DROP TABLE IF EXISTS TEL");
        db.execSQL("CREATE TABLE TEL (ID_TEL INTEGER PRIMARY KEY AUTOINCREMENT, NUMERO_TEL VARCHAR(10))");
           /* db.execSQL("INSERT INTO TEL (NUMERO_TEL) VALUES ('0769239408')");
            db.execSQL("INSERT INTO TEL (NUMERO_TEL) VALUES('0760269540')");
            db.execSQL("INSERT INTO TEL (NUMERO_TEL) VALUES ('0758639462')");*/
        Cursor cu = db.query("TEL",new String[]{"NUMERO_TEL"},null,null,null,null,null);
        cu.moveToFirst();
        //Log.v("tel",cu.getString(0));


    }
}
