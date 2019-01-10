package com.yelatpv.ClasesOBJ;

import java.io.Serializable;


public class Usuario implements Serializable {
    Integer idusuario;
    String mail;
    String nombreusuario;
    String password;
    String pinacceso;
    Boolean esowner;

    public Usuario(Integer idusuario, String mail, String nombreusuario, String password, String pinacceso, Boolean esowner) {
        this.idusuario = idusuario;
        this.mail = mail;
        this.nombreusuario = nombreusuario;
        this.password = password;
        this.pinacceso = pinacceso;
        this.esowner = esowner;
    }

    public Usuario() {
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idusuario=" + idusuario +
                ", mail='" + mail + '\'' +
                ", nombreusuario='" + nombreusuario + '\'' +
                ", password='" + password + '\'' +
                ", pinacceso='" + pinacceso + '\'' +
                ", esOwner=" + esowner +
                '}';
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPinacceso() {
        return pinacceso;
    }

    public void setPinacceso(String pinacceso) {
        this.pinacceso = pinacceso;
    }

    public Boolean getEsowner() {
        return esowner;
    }

    public void setEsOwner(Boolean esowner) {
        this.esowner = esowner;
    }
}
