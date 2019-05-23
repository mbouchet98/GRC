package METIER;

public class Thematique {
    private int id_user;
    private String lib_theme;
    private String desc_theme;


    public Thematique(int id_user, String lib_theme, String desc_theme) {
        this.id_user = id_user;
        this.lib_theme = lib_theme;
        this.desc_theme = desc_theme;

    }
    public Thematique(String lib_theme, String desc_theme) {

        this.id_user = 0;
        this.lib_theme = lib_theme;
        this.desc_theme = desc_theme;
    }


    public int getId_theme(){
        return this.id_user;
    }

    public String getLib_theme(){
        return this.lib_theme;
    }
    public void setLib_theme(String lib_theme){
        this.lib_theme = lib_theme;
    }

    public String getDesc_theme(){
        return this.desc_theme;
    }
    public void setDesc_theme(String desc_theme){
        this.desc_theme = desc_theme;
    }

}
