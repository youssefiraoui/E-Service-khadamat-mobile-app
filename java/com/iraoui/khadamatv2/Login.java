package com.iraoui.khadamatv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iraoui.khadamatv2.entities.User;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Login extends AppCompatActivity {
    private DatabaseReference mDatabase;
    EditText nom,prenom,date,tel;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Spinner spinner,spinner1;
    EditText emailtxt,pwdtxt;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nom = findViewById(R.id.idnom);
        prenom = findViewById(R.id.idprenom);
        tel = findViewById(R.id.idtel);
        date = findViewById(R.id.idDate);
        spinner = findViewById(R.id.spinner);
        spinner1 = findViewById(R.id.spinner1);


        emailtxt=(EditText)findViewById(R.id.idemail);
        pwdtxt=(EditText)findViewById(R.id.idpwd);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference fDatabaseRoot = database.getReference();
        /*
        pour le spinner de services
         */
        final List<String> areas = new ArrayList<>();
        areas.add("Masculin");
        areas.add("Feminin");


        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(Login.this, android.R.layout.simple_spinner_item, areas);
        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(areasAdapter);
/*
pour le spinner de villes
 */
        fDatabaseRoot.child("villes").addValueEventListener(new ValueEventListener() {
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

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(Login.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

private void saveInformations(){
/*
pour authetifcation
 */
    System.out.println("register");

    final String email =emailtxt.getText().toString();
    final String pwd =pwdtxt.getText().toString().trim();
    System.out.println(emailtxt.getText().toString()+"--------"+pwdtxt.getText().toString());
    if(email.isEmpty()){
        emailtxt.setError("Email is required");
        emailtxt.requestFocus();
        return;
    }

    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        emailtxt.setError("please enter a valid email");
        emailtxt.requestFocus();
        return;
    }

    if(pwd.isEmpty()){
        pwdtxt.setError("Email is required");
        pwdtxt.requestFocus();
        return;
    }
    if(pwd.length()<6){
        pwdtxt.setError("minimum legth of password should be 6 ");
        pwdtxt.requestFocus();
        return;
    }
    progressBar.setVisibility(View.VISIBLE);
    mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        public void onComplete(@NonNull Task<AuthResult> task) {
            progressBar.setVisibility(View.GONE);

            if(task.isSuccessful()){
                Toast.makeText(getApplicationContext(),"user registred succesful",Toast.LENGTH_SHORT).show();
                String nomUser = nom.getText().toString().trim();
                if(nomUser.isEmpty()){
                    nom.setError("nom vide");
                    nom.requestFocus();
                    return;
                }
                String prenomUser = prenom.getText().toString().trim();
                if(prenomUser.isEmpty()){
                    prenom.setError("prenom vide");
                    prenom.requestFocus();
                    return;
                }
                String sexe = spinner.getSelectedItem().toString();
                String ville =spinner1.getSelectedItem().toString();
                String telUser = tel.getText().toString().trim();
                if(telUser.isEmpty()){
                    tel.setError("tel vide");
                    tel.requestFocus();
                    return;
                }
                if(telUser.length()!=10){
                    tel.setError("telephone invalide ");
                    tel.requestFocus();
                    return;
                }

                Date dateU = null;
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    System.out.println("*************"+format.parse(date.getText().toString()));
                    dateU = format.parse(date.getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                User user1 =new User(nomUser,prenomUser,telUser,dateU.toString(),sexe,ville,email,pwd);
                System.out.println("**********before1***********");
                user = FirebaseAuth.getInstance().getCurrentUser();
                mDatabase.child("Users").child(user.getUid()).setValue(user1);
                Intent intent=new Intent(Login.this,Acceuil.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);




            }else {
                if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    Toast.makeText(getApplicationContext(),"You are Already registred",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();


            }
        }

    });




}
    private void writeNewUser(String userId, String name, String email) {
       // User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }

    public void save(View view) {
    System.out.println("**********Saving***********");
    saveInformations();
    System.out.println("**********finish Saving***********");

    }
}
