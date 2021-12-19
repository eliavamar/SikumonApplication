package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class activity_view extends AppCompatActivity {
    public PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent i = getIntent();
        String dene = (String)i.getSerializableExtra("URL");
        Toast.makeText(activity_view.this, dene,Toast.LENGTH_LONG);
        pdfView=(PDFView)findViewById(R.id.pdfView);
        new RetrievePdfStream().execute(dene);
    }
        class  RetrievePdfStream extends AsyncTask<String,Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream= null;
            try {
                URL uRl=new URL(strings[0]);
                HttpURLConnection urlConnection= (HttpURLConnection) uRl.openConnection();
                if(urlConnection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());

                }
            }
            catch (IOException E){
                return  null;
            }
            return inputStream;
        }
        protected void onPostExecute(InputStream inputStream){
            PDFView.Configurator temp =pdfView.fromStream(inputStream);
            if(temp==null)
                Toast.makeText(activity_view.this,"null",Toast.LENGTH_LONG).show();
            else
                temp.load();
        }
    }
}