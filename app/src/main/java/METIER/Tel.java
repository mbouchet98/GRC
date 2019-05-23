package METIER;

public class Tel {
    private int id_Tel;
    private String numeroTel;


    public Tel(int id_Tel, String numetoTel){
        this.id_Tel = id_Tel;
        this.numeroTel = numetoTel;
    }

    public Tel( String numetoTel){
        this.id_Tel = 0;
        this.numeroTel = numetoTel;
    }

    public Tel() {
        this.id_Tel = 0;
        this.numeroTel = "";
    }

    public int getId_Tel(){
        return this.id_Tel;
    }

    public String getNumeroTel(){
        return this.numeroTel;
    }

    public void setNumeroTel(String numeroTel){
        this.numeroTel = numeroTel;
    }

}
