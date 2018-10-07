package com.iraoui.khadamatv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iraoui.khadamatv2.entities.Service;
import com.iraoui.khadamatv2.entities.User;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {

    Spinner spVille,spService;
    Button search;
    TextView txt;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    DatabaseReference databaseUsers,dbuser;
    ListView userView;
    List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
txt=findViewById(R.id.idid);

        userList= new ArrayList<>();
        userView =findViewById(R.id.listUsers);

        search= findViewById(R.id.search);
        spService = findViewById(R.id.spinner);
        spVille = findViewById(R.id.spinner1);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        /*
        pour le spinner de services
         */
        myRef.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areas = new ArrayList<>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("nom").getValue(String.class);
                    areas.add(areaName);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(search.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spService.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
/*
pour le spinner de villes
 */
        myRef.child("villes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areas = new ArrayList<>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("nom").getValue(String.class);
                    areas.add(areaName);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(search.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spVille.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void search(View view) {
        String ville = spVille.getSelectedItem().toString();
        final String service = spService.getSelectedItem().toString();
        System.out.println("LOlo katy");
        User utilisateur;
        userList.clear();
        final List<User> users = new ArrayList<>();
        final Query query = myRef.child("Users").orderByChild("ville").equalTo(ville);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("hhhhhhh111");
                for(final DataSnapshot us:dataSnapshot.getChildren())
                {
                    us.getKey();
                    dbuser = FirebaseDatabase.getInstance().getReference("Users").child(us.getKey()).child("services");
                    dbuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           if(dataSnapshot!=null){
                               for (DataSnapshot ser: dataSnapshot.getChildren()) {
                                if(ser!=null){

                                    String serv=ser.child("service").getValue(String.class);
                                    if(serv!=null){
                                        System.out.println("heeeeeeeeeeeeeeeerre"+serv);
                                        if(serv.compareTo(service)==0){
                                            System.out.println("dodod+++++++++++++++++"+serv);
                                            databaseUsers = FirebaseDatabase.getInstance().getReference("Users").child(us.getKey());
                                            databaseUsers.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    //  for(DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                                    User utilisateur = dataSnapshot.getValue(User.class);
                                                    if (utilisateur!=null){
                                                        System.out.println(utilisateur.nom);
                                                        System.out.println(utilisateur.email);
                                                        txt.setText(String.valueOf(utilisateur.email));
                                                        userList.add(utilisateur);

                             Toast.makeText(getApplicationContext(),String.valueOf(utilisateur.prenom),Toast.LENGTH_SHORT).show();

                                                    }
                                                    UserItem adapter = new UserItem(search.this, userList);
                                                    userView.setAdapter(adapter);
                                                    System.out.println("/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/__/_//__//_/_/_/_/_/_/_/_/_/_/_//_/_/_/_/_/__/"+users.toString());

                                                }


                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


                                        }
                                    }


}
                               }


                           }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    System.out.println("22222"+us.getKey());




                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
