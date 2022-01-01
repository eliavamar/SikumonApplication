package com.example.myapp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView RegisterLink,ForgetPass;
    EditText PasswordField,EmailField;
    Button LoginBtn;
    DB dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase=DB.getInstance();
        ForgetPass=findViewById(R.id.ForgetPass);
        RegisterLink=findViewById(R.id.RegisterLink);
        PasswordField=findViewById(R.id.PasswordField);
        EmailField=findViewById(R.id.EmailField);
        LoginBtn=findViewById(R.id.LoginBtn);
        ForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=PasswordField.getText().toString();
                String email=EmailField.getText().toString();
                if(validEmail(email) && validPassword(pass)) {
                    dataBase.signIn(email, pass, MainActivity.this);
                    //startActivity(new Intent(MainActivity.this, PortalActivity.class));
                }
                else{
                    Toast.makeText(MainActivity.this, "Sign-In failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        RegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
    }
    boolean validEmail(String email){
        if(TextUtils.isEmpty(EmailField.getText().toString())){
            return false;
        }
        return true;
    }
    boolean validPassword(String pass){
        if(TextUtils.isEmpty(PasswordField.getText().toString())){
            return  false;
        }
        return true;
    }
    void showCustomDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.custom_dialog);

        //Initializing the views of the dialog.
        final EditText email = dialog.findViewById(R.id.name_et);
        TextView password = dialog.findViewById(R.id.pass);
        final CheckBox termsCb = dialog.findViewById(R.id.terms_cb);
        Button getPassBth = dialog.findViewById(R.id.get_password);
        Button finishDialog=dialog.findViewById(R.id.finishDialog);
        final int[] c = {1};
        termsCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                c[0]++;
            }
        });
        getPassBth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c[0]%2==1){
                    Toast.makeText(MainActivity.this,"Must Agree Terms!",Toast.LENGTH_SHORT).show();
                    return;

                }
                if (email.toString().equals("")){
                    Toast.makeText(MainActivity.this,"Must enter an email!",Toast.LENGTH_SHORT).show();
                    return;
                }
                DB db=DB.getInstance();
                db.forgetPassword(email.getText().toString(),password,MainActivity.this);
                Toast.makeText(MainActivity.this,"Reset link has been sent to the given email!",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        finishDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }




}