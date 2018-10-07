package com.iraoui.khadamatv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iraoui.khadamatv2.entities.Service;
import com.iraoui.khadamatv2.entities.User;


import java.util.ArrayList;
import java.util.List;

public class ProfileUser extends AppCompatActivity {
TextView nom,prenom,tel,dateN,sexe,ville,mail,pwd;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    DatabaseReference databaseUsers,dbuser;




    ListView serviceView;
    List<Service> serviceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        nom = findViewById(R.id.nom);
        pwd = findViewById(R.id.pwd);
        prenom = findViewById(R.id.prenom);
        ville = findViewById(R.id.ville);
        sexe = findViewById(R.id.sexe);
        dateN = findViewById(R.id.dateN);
        mail = findViewById(R.id.mail);
        tel = findViewById(R.id.tel);
        serviceList= new ArrayList<>();
        serviceView =findViewById(R.id.idList);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fDatabaseRoot = database.getReference();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
/*
Remplir list
 */
        dbuser = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("services");
        dbuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                serviceList.clear();
                for(DataSnapshot serviceSnapshot : dataSnapshot.getChildren()){

                    //Service service = serviceSnapshot.getValue(Service.class);
                    String titre = serviceSnapshot.child("service").getValue(String.class);
                    String description = serviceSnapshot.child("description").getValue(String.class);
                    String prix = serviceSnapshot.child("prix").getValue(String.class);
                    Service service1= new Service(titre,description,prix);

                    if(service1!=null){
                        serviceList.add(service1);
                    }
                 //  serviceList.add(service);

                }
                ServiceItem adapter = new ServiceItem(ProfileUser.this, serviceList);
                serviceView.setAdapter(adapter);

            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





/*

 */

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {

                    startActivity(new Intent(ProfileUser.this, Login.class));



                } else {

                }
            }
        };


        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  for(DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                User utilisateur = dataSnapshot.getValue(User.class);
                if (utilisateur!=null){
                    System.out.println(utilisateur.nom);
                    System.out.println(utilisateur.email);
                    nom.setText(String.valueOf(utilisateur.nom));
                    prenom.setText(String.valueOf(utilisateur.prenom));
                    pwd.setText(String.valueOf(utilisateur.password));
                    mail.setText(String.valueOf(utilisateur.email));
                    sexe.setText(String.valueOf(utilisateur.sexe));
                    ville.setText(String.valueOf(utilisateur.ville));
                    tel.setText(String.valueOf(utilisateur.tel));
                    dateN.setText(String.valueOf(utilisateur.dateNaissance));
                }
                System.out.println("khaaaaaaaaaaaaaawi");





                //  }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void ajouter(View view) {
        System.out.print("***********Ajouter*********************+++++++++++++--------***************");
        Intent intent=new Intent(ProfileUser.this,ajouterService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void search(View view) {
        System.out.print("***********Search*********************+++++++++++++--------***************");

        Intent intent=new Intent(ProfileUser.this,search.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);}
}
