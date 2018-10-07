package com.iraoui.khadamatv2.entities;

/**
 * Created by IRAOUI on 03/06/2018.
 */

public class Service {
    public String service;
    public String prix;
    public String description;

    public Service(String service, String description,String prix) {
        this.service = service;
        this.prix = prix;
        this.description = description;
    }

    public Service(){

    }
}
