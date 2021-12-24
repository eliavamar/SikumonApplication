package com.example.myapp;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DB   {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseStorage storage;
    public DB(){
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
    }
    public void signIn(String email, String password, MainActivity activity){
         mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(activity, "Sign-In success.",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent (activity, PortalActivity.class);
                    activity.startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(activity, "Sign-In failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void forgetPassword(String email,AppCompatActivity activity){
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                String pass="";
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(Objects.equals(document.get("Email").toString(), email)){
                            pass= email;
                            break;
                        }
                    }
                    if(pass.equals("")) {
                        Toast.makeText(activity, "No Such Email Exists!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(activity,"Your Password is: "+pass, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity,"No Such Email Exists!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void register(User user,AppCompatActivity activity){
        mAuth.createUserWithEmailAndPassword(user.getEmail().getEmail(), user.getPass())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            Toast.makeText(activity, "Sign Up success.", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(activity, "Sign Up failed.", Toast.LENGTH_SHORT).show();
                        }
                        // Create a new user with a first and last name
                        Map<String, Object> map = new HashMap<>();
                        map.put("Email", user.getEmail().getEmail());
                        map.put("Password",user.getPass());
                        map.put("Department",user.getDepartment());
                        map.put("Orientation",user.getOrientation());

                        // Add a new document with a generated ID
                        db.collection("users")
                                .add(map)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(activity, "Sign Up failed.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        Intent intent = new Intent (activity, MainActivity.class);
                        activity.startActivity(intent);
                    }
                });
    }
    public void searchFile(PDFFile pdfFile, AppCompatActivity activity){
        databaseReference= FirebaseDatabase.getInstance().getReference(pdfFile.getPath());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()==0){
                    return;
                }
                HashMap<String,String> map=new HashMap<>();
                for (DataSnapshot data:snapshot.getChildren()) {
                    map.put(data.getKey(),data.getValue(String.class));
                }
                pdfFile.setURL(map.get("URL"));
                if(pdfFile.getURL()==null){
                    Toast.makeText(activity,"Error: The file does not Exists",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(activity, activity_view.class);
                intent.putExtra("URL",pdfFile.getURL());
                activity.startActivity(intent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void upLoadFile(AppCompatActivity activity,String []path,HashMap<String,String>fileProperties,Uri pdfUri){
        ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(activity);
        progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        StorageReference storageReference=storage.getReference();//return root path
        storageReference.child("UploadPDF"+System.currentTimeMillis()+".pdf").putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            //store the url in realtime database
            @Override
            public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                if(taskSnapshot.getMetadata()==null||taskSnapshot.getMetadata().getReference()==null) {
                    Toast.makeText(activity, "Invalid URL", Toast.LENGTH_SHORT).show();
                }
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri= uriTask.getResult();
                fileProperties.put("URL"," "+uri.toString());
                DatabaseReference reference=database.getReference();//return the path to root
                fileProperties.put("Email",mAuth.getCurrentUser().getEmail());
                for (int i = 0; i <path.length ; i++) {
                    reference=reference.child(path[i]);
                }
                reference.setValue(fileProperties).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(activity,"File successfully uploaded",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, PortalActivity.class);
                            activity.startActivity(intent);
                        }
                        else
                            Toast.makeText(activity,"File not successfully uploaded",Toast.LENGTH_SHORT).show();
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity,"File not successfully uploaded (Failure)",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                int currentProgress=(int)(100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploading file..."+currentProgress+"%");

            }
        });

    }
    public void studyGroupView(ListView listView,ArrayList<String>items,AppCompatActivity activity){
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
                            listView.setAdapter(new ArrayAdapter<String>(activity.getApplicationContext(),android.R.layout.simple_list_item_1,items){
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
                            Toast.makeText(activity, "Error getting documents: ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void myFileView(List<String> PDF,   List<String> PDFName,ListView listView,AppCompatActivity activity){
        databaseReference=FirebaseDatabase.getInstance().getReference("PDF");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()==0)return;
                for (DataSnapshot dep:snapshot.getChildren()) {
                    for (DataSnapshot courses:dep.getChildren()) {
                        for (DataSnapshot file_data:courses.getChildren()) {
                            if(file_data.child("Email").getValue(String.class).equals(mAuth.getCurrentUser().getEmail())){
                                PDFName.add(file_data.getKey());
                                PDF.add(file_data.child("URL").getValue(String.class));

                            }
                        }
                    }
                }
                ArrayAdapter arrayAdapter =new ArrayAdapter(activity.getApplicationContext(), android.R.layout.simple_list_item_1,PDFName)
                {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view=super.getView(position, convertView, parent);
                        TextView textView=view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(20);
                        return  view;
                    }
                };
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });
    }
    public void setStudyGroup(Map<String,Object> studyGroupData,AppCompatActivity activity){
        studyGroupData.put("Email",mAuth.getCurrentUser().getEmail());
        db.collection("studyGroups")
                .add(studyGroupData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(activity,"Created a study group!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(activity, PortalActivity.class);
                        activity.startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity,"Failed creating a study group!",Toast.LENGTH_LONG).show();
                    }
                });
    }
}
