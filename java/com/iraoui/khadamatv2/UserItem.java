package com.iraoui.khadamatv2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iraoui.khadamatv2.entities.Service;
import com.iraoui.khadamatv2.entities.User;

import java.util.List;

public class UserItem extends ArrayAdapter<User> {
    User user ;
    private Activity context;
    List<User> users;

    public UserItem(Activity context, List<User> users) {
        super(context, R.layout.activity_user_item, users);
        this.context = context;
        this.users = users;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_user_item, null, true);


        TextView NomPrenom = (TextView) listViewItem.findViewById(R.id.AffichNom);
        TextView sexe = (TextView) listViewItem.findViewById(R.id.AffichSexe);
        TextView age = (TextView) listViewItem.findViewById(R.id.AfficheAge);
        TextView prix = (TextView) listViewItem.findViewById(R.id.AffichePrix);


         user = users.get(position);
        NomPrenom.setText(user.nom+" "+user.prenom);
        sexe.setText(user.sexe);
        age.setText("27");
        prix.setText("100"+" DH/H");

        return listViewItem;


    }

    public void call(View view) {

    }

    public void addFavoris(View view) {
    }


}
