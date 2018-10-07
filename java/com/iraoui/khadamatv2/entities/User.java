package com.iraoui.khadamatv2.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IRAOUI on 06/05/2018.
 */

public class User implements Serializable {

    public String nom;
    public String prenom;
    public String tel;
    public String dateNaissance;
    public String sexe;
    public String ville;
    public String email;
    public String password;
   // public HashMap<String,Object> services = new HashMap<>();


    public User() {
    }

    public User(String nom, String prenom, String tel,String dateNaissance,String sexe,String ville,String email,String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.dateNaissance = dateNaissance;
        this.ville = ville;
        this.sexe = sexe;
        this.email=email;
        this.password=password;
    }




}
