package com.iraoui.khadamatv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iraoui.khadamatv2.entities.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ajouterService extends AppCompatActivity {
EditText desc,prix;
Spinner spServ;
Button enrg;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_service);
        desc = findViewById(R.id.desc);
        spServ=findViewById(R.id.spinnerServ);
        prix = findViewById(R.id.prix);
        enrg = findViewById(R.id.enregistrer);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        System.out.print("/*************"+"********7777777777777777777777777777777777777777777777777");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fDatabaseRoot = database.getReference();
        fDatabaseRoot.child("Services").addValueEventListener(new ValueEventListener() {
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

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(ajouterService.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spServ.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//                             Services
//        fDatabaseRoot.child("Services").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Is better to use a List, because you don't know the size
//                // of the iterator returned by dataSnapshot.getChildren() to
//                // initialize the array
//                final List<String> areas = new ArrayList<>();
//
//                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
//                    String areaName = areaSnapshot.child("nom").getValue(String.class);
//                    System.out.print("/*************"+areaName);
//                    areas.add(areaName);
//                }
//                System.out.print("/*************"+areas);
//
//                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(
//                        ajouterService.this,
//                        android.R.layout.simple_spinner_item,
//                        areas);
//                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spServ.setAdapter(areasAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public void enregistrer(View view) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        Map<String, Object> childUpdates = new HashMap<>();
        String prixx= prix.getText().toString();
        if(prixx.isEmpty()){
            prix.setError("estimation du prix");
            prix.requestFocus();
            return;
        }
        String description = desc.getText().toString();
        if(description.isEmpty()){
            desc.setError("description obligatoire ");
            desc.requestFocus();
            return;
        }
        String spinn=spServ.getSelectedItem().toString();

        String key = mDatabase.child("Users").child(user.getUid()).child("services").push().getKey();
        Service service =new Service(spinn,description,prixx);
        childUpdates.put("/Users/"+user.getUid()+"/services/" + key, service);
        //childUpdates.put("/users-services/"+user.getUid()+"/services/" + key, service);

        mDatabase.updateChildren(childUpdates);
        /*
        mDatabase.child("Users").child(user.getUid()).child("services").setValue(service);
         */
        Toast.makeText(getApplicationContext(),"service bien enregistrer",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(ajouterService.this,ProfileUser.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
