package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewActivity extends AppCompatActivity {
    public PDFView pdfView;
    public static final int HTTP_OK=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        String dene = (String)i.getSerializableExtra("URL");
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
                if(urlConnection.getResponseCode()==HTTP_OK){
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
                Toast.makeText(ViewActivity.this,"null",Toast.LENGTH_LONG).show();
            else
                temp.load();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.upload:
                startActivity(new Intent(ViewActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(ViewActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(ViewActivity.this,SearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(ViewActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(ViewActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(ViewActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}