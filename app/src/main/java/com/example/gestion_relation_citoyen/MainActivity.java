package com.example.gestion_relation_citoyen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import DAO.UserDAO;
import METIER.USER;
import util.preferenceUtils;

public class MainActivity extends AppCompatActivity {
    private UserDAO userdao;

    private EditText email;
    private EditText mdp;
    private Button btnConnection;
    private Button btnInscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objet();

        btnConnection.setOnClickListener(clickBtnConnection);
        btnInscription.setOnClickListener(clickBtnInscription);
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


    private void objet(){

        email = (EditText) findViewById(R.id.txtboxLogEmail);
        mdp = (EditText) findViewById(R.id.txtboxLogMdp);
        btnConnection = (Button) findViewById(R.id.btnConnection);
        btnInscription = (Button) findViewById(R.id.btnInscription);


        preferenceUtils utils = new preferenceUtils();
        if (preferenceUtils.getEmail(this) != null){
            Intent intent = new Intent(this, ThematiqueActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private View.OnClickListener clickBtnConnection = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            readCompareUser();

            //fait un lecture de la base a distance.
            USER UserAdd = null;
            final UserDAO unUser = new UserDAO(getApplicationContext());
            List<USER> uneList;
            unUser.open();

            uneList = unUser.read();

            unUser.close();
        }
    };

    private void readCompareUser(){

        List<USER> uneList;

        USER mUser = null;

        final UserDAO unUser = new UserDAO(this);

        unUser.open();

        uneList = unUser.read();

        unUser.close();

        String emailU = String.valueOf(email.getText());
        String mdpU = String.valueOf(mdp.getText());

        boolean emailR = false;
        boolean mdpR = false;
        boolean test = false;


        if (emailU.isEmpty() ){
            email.setError("Vueillez saisir votre adresse mail.");
        }
        if ( mdpU.isEmpty()){
            mdp.setError("Vueillez saisir votre mot de passe.");
        }


        // tester si l'adresse mail exist et le mdp aussi.
        // peut etre optimiser (mieux code).
        for (USER user : uneList){
            if (user.getEmail_user().equals(emailU)) {
                if (user.getMdp_user().equals(mdpU)){
                    test = true;
                    mdpR = true;
                }
                emailR = true;
            }
        }
        // faire la lecture à distance




        if(test == true && emailR == true && mdpR == true){

            preferenceUtils.saveEmail(emailU,this);
            preferenceUtils.savePassword(mdpU,this);

            Toast.makeText(this,"success",Toast.LENGTH_LONG).show();

           /* Intent intent1;
            intent1 = new Intent(this, SuivieActivity.class);
            intent1.putExtra("EMAIL",email.getText().toString().trim());
            emptyInputEditText();*/

            Intent intent;
            intent = new Intent(this, ThematiqueActivity.class);
            intent.putExtra("EMAIL",email.getText().toString().trim());
            emptyInputEditText();
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this,"Adresse mail ou mot de passe incorrecte",Toast.LENGTH_LONG).show();
        }
    }

    private View.OnClickListener clickBtnInscription = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goInscription();
        }
    };

    private void goInscription(){
        Intent intent;
        intent = new Intent(this, InscriptionActivity.class);
        startActivity(intent);
        finish();
    }

    private void emptyInputEditText(){
        email.setText(null);
        mdp.setText(null);
    }
}
