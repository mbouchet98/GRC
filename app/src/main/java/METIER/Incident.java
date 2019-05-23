package METIER;

public class Incident {
    private int id_incident;
    private USER id_user_incident;
    private Thematique id_theme_incident;
    private byte[] photo;
    private double latitude;
    private double longitude;
    private String description;
    private String date;
    private String etat;


    // mettre l'état par defaut en "en cour de traitement"

    public Incident(int id_incident, USER id_user_incident, Thematique id_theme_incident, byte[] photo,  double latitude,double longitude, String description,String date,String etat) {
        this.id_incident = id_incident;
        this.id_user_incident = id_user_incident;
        this.id_theme_incident = id_theme_incident;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.date = date;
        this.etat = etat;
    }

    public Incident(USER id_user_incident, Thematique id_theme_incident, byte[] photo, double latitude, double longitude, String description, String date, String etat) {
        this.id_incident = 0;
        this.id_user_incident = id_user_incident;
        this.id_theme_incident = id_theme_incident;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.date = date;
        this.etat = etat;
    }

    public int getId_incident() {
        return id_incident;
    }

    public USER getId_user_incident() {
        return id_user_incident;
    }

    public void setId_user_incident(USER id_user_incident) {
        this.id_user_incident = id_user_incident;
    }

    public Thematique getId_theme_incident() {
        return id_theme_incident;
    }

    public void setId_theme_incident(Thematique id_theme_incident) {
        this.id_theme_incident = id_theme_incident;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
