package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MyStudyGroups extends AppCompatActivity {
    ListView listView;
    ArrayList<String> items;
    ArrayAdapter<String> arrayAdapter;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_study_groups);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView=findViewById(R.id.list_view);
        items=new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        db.collection("studyGroups")
                .whereEqualTo("Email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HashMap<String,String> map=new HashMap<String,String>();
                                String item="";
                                item+="key: "+document.getId()+"\n";
                                item+="Permissions: "+document.get("Permission",String.class)+"\n";
                                item+="State: "+document.get("State",String.class)+"\n";
                                item+="Comment: "+document.get("Comment",String.class)+"\n";
                                if(!document.get("State",String.class).equals("Front"))
                                    item+="Link: "+document.get("Link",String.class)+"\n";
                                item+="Department: "+document.get("Department",String.class)+"\n";
                                item+="Course: "+document.get("Course",String.class)+"\n";
                                items.add(item);
                            }
//                            arrayAdapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1,items);
//                            int log = 0;
                            listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,items){
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View view = super.getView(position, convertView, parent);
                                    TextView textView = ((TextView) view.findViewById(android.R.id.text1));
                                    textView.setMinHeight(0); // Min Height
                                    textView.setMinimumHeight(0); // Min Height
                                    textView.setHeight(420); // Height
                                    return view;
                                }
                            });
                        } else {
                            Toast.makeText(MyStudyGroups.this, "Error getting documents: ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}