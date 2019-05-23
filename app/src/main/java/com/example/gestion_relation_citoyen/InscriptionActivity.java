package com.example.gestion_relation_citoyen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DAO.TelDAO;
import DAO.UserAvoirTelDAO;
import DAO.UserDAO;
import METIER.Tel;
import METIER.USER;

public class InscriptionActivity extends AppCompatActivity {
    private UserDAO userdao;

    private EditText nom;
    private EditText prenom;
    private EditText tel;
    private EditText tel2;
    private EditText email;
    private EditText mdp;
    private EditText cmdp;
    private TextView CGU;
    private Switch boolCGU;
    private Switch boolML;
    private TextView ML;
    private Button btnRegister;
    private Button btnCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        objet();

        CGU.setMovementMethod(LinkMovementMethod.getInstance());
        ML.setMovementMethod(LinkMovementMethod.getInstance());
        btnRegister.setOnClickListener(clickBtnRegister);
        btnCon.setOnClickListener(clickBtnCon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_quit:
                // touver le moyen de tout fermer simultanément.
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void objet(){
        nom = (EditText) findViewById(R.id.txtboxNom);
        prenom = (EditText) findViewById(R.id.txtboxPrenom);
        tel = (EditText) findViewById(R.id.txtboxTel);
        tel2 = (EditText) findViewById(R.id.txtboxTel2);
        email = (EditText) findViewById(R.id.txtboxEmail);
        mdp = (EditText) findViewById(R.id.txtboxMDP);
        cmdp = (EditText) findViewById(R.id.txtboxCMDP);
        CGU =(TextView)findViewById(R.id.txtCGU);
        boolCGU = (Switch)findViewById(R.id.switchConditionL);
        boolML = (Switch)findViewById(R.id.switchMentionL);
        ML = (TextView)findViewById(R.id.txtMentionL) ;
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnCon = (Button) findViewById(R.id.btnConnexion);

        ML.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private View.OnClickListener clickBtnRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addUser();
        }
    };

    private void addUser() {

        /* question de l'utilisation des données ??? */
        /* Les règle de droit a utilisée et comment ?? */
        /* prévenir l'utilisateur */
        /* création d'une condition pour répondre a cette demande*/


        USER UserAdd = null;
        final UserDAO unUser = new UserDAO(this);
        List<USER> uneList;
        USER userRecupDB =null;
        Tel leTel = null;
        TelDAO telDAO = new TelDAO(this);
        ArrayList<Tel> listTel = new ArrayList<Tel>();


        UserAvoirTelDAO userAvoirTelDAO = new UserAvoirTelDAO(this);


        unUser.open();
        telDAO.open();
        userAvoirTelDAO.open();

        uneList = unUser.read();



        String nomU = String.valueOf(nom.getText());
        String prenomU = String.valueOf(prenom.getText());
        String telU = String.valueOf(tel.getText());
        String telU2 = String.valueOf(tel2.getText());
        String emailU = String.valueOf(email.getText());
        String mdpU = String.valueOf(mdp.getText());
        String cmdpU = String.valueOf(cmdp.getText());

        int idR = 0;
        Boolean nomR = false;
        Boolean prenomR = false;
        Boolean telR = false;
        Boolean emailR = false;
        Boolean mdpR = false;
        Boolean cmdpR = false;
        Boolean CGUR = false;
        Boolean MLR = false;


        if (nomU.isEmpty()) {
            nom.setError("veuillez saissir votre Nom.");
        } else {
            if (nomU.matches("[a-zA-Z]*")) {
                nomR = true;
            } else {
                nom.setError("veuillez saissir correctement avec que des lettres");
            }
        }

        if (prenomU.isEmpty()) {
            prenom.setError("veuillez saissir votre Prenom.");
        } else {
            if (prenomU.matches("[a-zA-Z]*")) {
                prenomR = true;
            } else {
                prenom.setError("veuillez saissir correctement avec que des lettres");
            }
        }

        //ajouter plusieur numéro de tel.
        if (telU.isEmpty() && telU2.isEmpty()) {
            tel.setError("veuillez saissir votre Numéro de Téléphone.");
        } else {
            if (telU.matches("[0-9]{10,10}") && telU2.matches("[0-9]{10,10}")) {
                telR = true;

            } else {
                tel.setError("veuillez saissir correctement avec que des Nombres de 0 à 9");
            }
        }

        if (emailU.isEmpty()) {
            email.setError("veuillez saissir votre Adresse mail.");
        } else {
            if (emailU.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$")) {
                for (USER user : uneList){
                    if (user.getEmail_user().equals(emailU)){
                        idR = user.getId_user();
                    }
                }
                if(idR != 0){
                    email.setError("Email deja utiliser");
                    emailR = false;
                }else {
                    emailR = true;
                }



            } else {
                email.setError("veuillez saissir correctement ex: exemple@gmail.com");
            }
        }

        if (mdpU.isEmpty()) {
            mdp.setError("veuillez saissir votre Mot de Passe");
        } else {
            if (mdpU.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[-+!*$@%_])([-+!*$@%_\\w]{8,})$")) {
                mdpR = true;
            } else {
                mdp.setError("veuillez saissir correctement \n" +
                        " ex: M0tDeP@sse159 \n -Minimun une Majuscule. \n -Minimun une minuscule. \n -Minimun un chiffre. \n -Minimun un caractère spécial.\n" +
                        "-Minimun 8 caractère.");
            }
        }
        if (cmdpU.isEmpty()) {
            cmdp.setError("veuillez saissir votre Mot de Passe");
        } else {
            if (cmdpU.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[-+!*$@%_])([-+!*$@%_\\w]{8,})$")) {
                if (mdpU.equals(cmdpU)){
                    cmdpR = true;
                }else {
                    cmdp.setError("Les Mots de passe ne correspond pas");
                }
            } else {
                cmdp.setError("veuillez saissir correctement \n" +
                        " ex: M0tDeP@sse159 \n -Minimun une Majuscule. \n -Minimun une minuscule. \n -Minimun un chiffre. \n -Minimun un caractère spécial.\n" +
                        "-Minimun 8 caractère.");
            }
        }

        if (boolCGU.isChecked()){
            CGUR = true;
        }else {
            Toast.makeText(this,"Veulliez accepter les condition générale d'utilisation",Toast.LENGTH_LONG).show();
        }

            if (boolML.isChecked()){
                MLR = true;
            }else {
                Toast.makeText(this,"Veulliez accepter les Mention Légales",Toast.LENGTH_LONG).show();
            }



            /* Pour être executer il faut que tout les condition au préalable soit validée. */
            if (nomR == true && prenomR == true && telR == true && emailR == true && mdpR ==true && cmdpR == true && CGUR == true && MLR == true){


                Tel tel = new Tel(telU);
                Tel tel2 = new Tel(telU2);

                //insert Tel

                telDAO.insert(tel);
                telDAO.insert(tel2);




                // insert user
                listTel.add(telDAO.readByNum(telU));
                listTel.add(telDAO.readByNum(telU2));

                UserAdd = new USER(nomU,prenomU,emailU,mdpU,listTel);
                unUser.insert(UserAdd);
                userRecupDB = unUser.readByEmail2(UserAdd.getEmail_user());
                unUser.close();
                //insert Avoir
                for (Tel montel:listTel) {
                    leTel = telDAO.readByNum(montel.getNumeroTel());
                    userAvoirTelDAO.insert(userRecupDB,leTel);
                }
                telDAO.close();
                userAvoirTelDAO.close();

                // une fois inseret dans la base on doit être rediriger vers une autre page.
                Intent intent;
                intent = new Intent(this, ThematiqueActivity.class);
                startActivity(intent);
                finish();
        }else {
            Toast.makeText(this,"Erreur de saisie quelque part",Toast.LENGTH_LONG).show();
        }

    }

    private View.OnClickListener clickBtnCon = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
