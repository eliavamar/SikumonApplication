package com.example.myapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class ForgetPassActivity extends AppCompatActivity {
    EditText email;
    Button reset;
    DB dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email=findViewById(R.id.EmailForget);
        reset=findViewById(R.id.ResetBtn);
        dataBase=new DB();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(ForgetPassActivity.this, "You must enter an Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                dataBase.forgetPassword(email.getText().toString(),ForgetPassActivity.this);
            }
        });
    }
}