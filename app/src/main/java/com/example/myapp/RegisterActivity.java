package com.example.myapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText email, pass,passRepeat;
    Button RegisterBtn;
    RadioGroup radio;
    Spinner department;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        RegisterBtn=findViewById(R.id.RegisterBtn);
        email=findViewById(R.id.Email);
        pass=findViewById(R.id.Password);
        radio=findViewById(R.id.radio);
        passRepeat=findViewById(R.id.PasswordRepeat);
        department=findViewById(R.id.department);
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Password.isValid(pass.getText().toString())){
                    if(radio.getCheckedRadioButtonId()==-1){
                        String error="Must choose between Student/Teacher!";
                        Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(department.getSelectedItem().toString().equals("Choose Department")){
                        String error="Must choose Department!";
                        Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!pass.getText().toString().equals(passRepeat.getText().toString())){
                        String error="Passwords don't match!";
                        Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!Email.isValid(email.getText().toString())){
                        String error="Invalid Email address!";
                        Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
                        return;
                    }
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign up success, update UI with the signed-in user's information
                                        Toast.makeText(RegisterActivity.this, "Sign Up success.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign up fails, display a message to the user.
                                        Toast.makeText(RegisterActivity.this, "Sign Up failed.", Toast.LENGTH_SHORT).show();
                                    }
                                    // Create a new user with a first and last name
                                    RadioButton OrientationBt = (RadioButton) findViewById(radio.getCheckedRadioButtonId());
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("Email", email.getText().toString());
                                    user.put("Password", pass.getText().toString());
                                    user.put("Department", department.getSelectedItem().toString());
                                    user.put("Orientation",OrientationBt.getText().toString());

                                    // Add a new document with a generated ID
                                    db.collection("users")
                                            .add(user)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });
                                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                    finish();
                                }
                            });
                }else{
                    String error="Password must be consisted of 8-20 chars using\n at least 1 capital letter and special character!";
                    Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}