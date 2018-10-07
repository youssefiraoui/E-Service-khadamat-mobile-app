package com.iraoui.khadamatv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by IRAOUI on 04/05/2018.
 */


public class Acceuil extends AppCompatActivity {
    ProgressBar progressBar;
    EditText emailtxt,pwdtxt;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("hello");
        setContentView(R.layout.activity_accueil);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("doha");
        System.out.println(myRef.getKey().toString()+"---"+myRef.getParent().toString());

        myRef.setValue("Hello, World!");
        System.out.println("dooooooooooohhhhééééééééé");

        emailtxt=(EditText)findViewById(R.id.editTextLogin);
        pwdtxt=(EditText)findViewById(R.id.editTextPassword);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);

        //findViewById(R.id.btnSignUp).setOnClickListener(this);
    }
   private void registerUser(){



   }

    public void login(View view) {
        System.out.println("Login");

        String email =emailtxt.getText().toString().trim();
        String pwd =pwdtxt.getText().toString().trim();
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

        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                   Intent intent=new Intent(Acceuil.this,ProfileUser.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }
            }

        });

    }

    public void SignUp(View view) {

       startActivity(new Intent(this,Login.class));
    }
   /*
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUp :
                System.out.println("click");
                registerUser();
                break;
        }
    }*/


}
