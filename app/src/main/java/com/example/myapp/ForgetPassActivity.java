package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ForgetPassActivity extends AppCompatActivity {
    EditText email;
    Button reset;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email=findViewById(R.id.EmailForget);
        reset=findViewById(R.id.ResetBtn);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(ForgetPassActivity.this, "You must enter an Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                String pass="";
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        System.out.println(document.get("Email").toString());
                                        if(Objects.equals(document.get("Email").toString(), email.getText().toString())){
                                            pass= Objects.requireNonNull(document.get("Password")).toString();
                                            break;
                                        }
                                    }
                                    Toast.makeText(ForgetPassActivity.this,"Your Password is:"+pass, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ForgetPassActivity.this,"No Such Email Exists!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}