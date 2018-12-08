package br.edu.ufam.icomp.catalogue.Entidades;

import br.edu.ufam.icomp.catalogue.Activity.CatalogoList;

public class Catalogacao {
    private String uID;
    private String cID;
    private String cName;
    private String cTipo;
    private String cObs;
    private String imagemCaminho;
    private String imagemUrl;
    private String status;

    public Catalogacao(){

    }

    public Catalogacao(String uID, String cID, String cName, String cTipo, String cObs, String imagemCaminho, String imagemUrl, String status){
        this.uID = uID;
        this.cID = cID;
        this.cName = cName;
        this.cTipo = cTipo;
        this.cObs = cObs;
        this.imagemCaminho = imagemCaminho;
        this.imagemUrl = imagemUrl;
        this.status = status;
    }

    public String getImagemCaminho() {
        return imagemCaminho;
    }

    public void setImagemCaminho(String imagemCaminho) {
        this.imagemCaminho = imagemCaminho;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcTipo() {
        return cTipo;
    }

    public void setcTipo(String cTipo) {
        this.cTipo = cTipo;
    }

    public String getcObs() {
        return cObs;
    }

    public void setcObs(String cObs) {
        this.cObs = cObs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
