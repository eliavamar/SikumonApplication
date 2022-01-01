package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewStudyGroupsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> items;
    DB dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_study_groups);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        String department = (String)i.getSerializableExtra("Department");
        Intent j = getIntent();
        String course = (String)j.getSerializableExtra("Course");
        Intent z = getIntent();
        String ID = (String)z.getSerializableExtra("ID");
        Intent x = getIntent();
        int SearchOption = (int)x.getSerializableExtra("SearchOption");
        listView=findViewById(R.id.list_view);
        items=new ArrayList<>();
        dataBase=DB.getInstance();
        if(SearchOption==1){
            dataBase.GroupViewByKey(listView, department, course, ID ,items, ViewStudyGroupsActivity.this);
        }
        else if(SearchOption==2) {
            dataBase.GroupViewByEmail(listView, department, course, ID, items,ViewStudyGroupsActivity.this);
        }
        else{
            dataBase.AllGroupView(listView, department, course, items,ViewStudyGroupsActivity.this);
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
                startActivity(new Intent(ViewStudyGroupsActivity.this,UploadActivity.class));
                return true;
            case R.id.myFiles:
                startActivity(new Intent(ViewStudyGroupsActivity.this,MyFilesActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(ViewStudyGroupsActivity.this,SearchActivity.class));
                return true;
            case R.id.profile:
                startActivity(new Intent(ViewStudyGroupsActivity.this,ProfileActivity.class));
                return true;
            case R.id.myStudyGroups:
                startActivity(new Intent(ViewStudyGroupsActivity.this,MyStudyGroups.class));
                return true;
            case R.id.logout:
                DB.logout(this);
                return true;
            case R.id.myFavorite:
                startActivity(new Intent(ViewStudyGroupsActivity.this,FavoriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}