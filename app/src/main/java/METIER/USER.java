package METIER;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class USER {
    private int id_user;
    private String nom_user;
    private String prenom_user;
    private String email_user;
    private String mdp_user;
    private List<Tel> mesTel;


    public USER(int id_user, String nom_user, String prenom_user, String email_user, String mdp_user, ArrayList<Tel> mesTel) {
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email_user = email_user;
        this.mdp_user = mdp_user;
        this.mesTel = mesTel;
    }
    public USER(String nom_user, String prenom_user, String email_user, String mdp_user, ArrayList<Tel> mesTel) {

        this.id_user = 0;
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email_user = email_user;
        this.mdp_user = mdp_user;
        this.mesTel = mesTel;

    }


    public USER(int id_user,String nom_user, String prenom_user, String email_user, String mdp_user) {

        this.id_user = id_user;
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email_user = email_user;
        this.mdp_user = mdp_user;
        this.mesTel = null;

    }

    public USER(int idUser, String nomUser, String prenomUser, String emailUser, String mdpUser, List<Tel> unTel) {
        this.id_user = 0;
        this.nom_user = "";
        this.prenom_user = "";
        this.email_user = "";
        this.mdp_user = "";
        this.mesTel = new ArrayList<Tel>();
    }


    public int getId_user(){
        return this.id_user;
    }

    public String getNom_user(){
        return this.nom_user;
    }
    public void setNom_user(String nom_user){
        this.nom_user = nom_user;
    }

    public String getPrenom_user(){
        return this.prenom_user;
    }
    public void setPrenom_user(String prenom_user){
        this.prenom_user = prenom_user;
    }

    public String getEmail_user(){
        return this.email_user;
    }
    public void setEmail_user(String email_user){
        this.email_user = email_user;
    }

    public List<Tel> getMesTel() {
        return mesTel;
    }

    public void setMesTel(List<Tel> mesTel) {
        this.mesTel = mesTel;
    }

    public String getMdp_user(){
        return this.mdp_user;
    }
    public void setMdp_user(String mdp_user){
        this.mdp_user = mdp_user;
    }
}
