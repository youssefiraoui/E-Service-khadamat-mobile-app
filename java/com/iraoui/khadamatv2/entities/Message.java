package com.iraoui.khadamatv2.entities;

import java.util.Date;

/**
 * Created by IRAOUI on 07/05/2018.
 */

public class Message {

    public String iduser1;
    public String iduser2;
    public String message;
    public Date dateMessage;

    public Message(String iduser1, String iduser2, String message, Date dateMessage) {
        this.iduser1 = iduser1;
        this.iduser2 = iduser2;
        this.message = message;
        this.dateMessage = dateMessage;
    }
    public Message(){

    }

}
