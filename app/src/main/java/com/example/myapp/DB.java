package com.example.myapp;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DB   {
    private static FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static FirebaseFirestore db= FirebaseFirestore.getInstance();
    private static DatabaseReference databaseReference;
    private static FirebaseDatabase database=FirebaseDatabase.getInstance();
    private static FirebaseStorage storage= FirebaseStorage.getInstance();
    private static final int EDIT_DISTANCE=5;
    private static DB instance=null;
    private DB() { }

    public static DB getInstance(){
        if(instance==null) instance=new DB();
        return instance;
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

    public void searchFile(String filename,PDFFile pdfFile, AppCompatActivity activity){
        LinkedList<String> PDF=new LinkedList<>();
        LinkedList<String> PDFName=new LinkedList<>();
        LinkedList<Boolean> isOwner=new LinkedList<>();
        LinkedList<String> path=new LinkedList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference(pdfFile.getPath());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()==0){
                    return;
                }
                for (DataSnapshot data:snapshot.getChildren()) {
                    if(Algorithms.editDistance(filename,data.getKey())<EDIT_DISTANCE) {
                        path.add(pdfFile.path+"/"+data.getKey());
                        for (DataSnapshot childInfo : data.getChildren()) {
                            if (childInfo.getKey().equals("URL")) {
                                PDF.add(childInfo.getValue(String.class));
                                PDFName.add(data.getKey());
                            }
                            if (childInfo.getKey().equals("Email")) {
                                if (childInfo.getValue(String.class).equals(mAuth.getCurrentUser().getEmail()))
                                    isOwner.add(true);
                                else
                                    isOwner.add(false);
                            }
                        }
                    }
                }
                if(PDF.size()==0) {
                    Toast.makeText(activity, "Error: No file with that name exists!", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(activity, SearchFileDisplayActivity.class);
                intent.putExtra("PDFName",PDFName);
                intent.putExtra("PDF",PDF);
                intent.putExtra("isOwner",isOwner);
                intent.putExtra("path",path);
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
                fileProperties.put("Name",taskSnapshot.getMetadata().getName());
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
                            listView.setAdapter(new ArrayAdapter<String>(activity.getApplicationContext(),R.layout.out_item_list_1,items){
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    TextView mainTV;
                                    Button mainBTN;
                                        LayoutInflater inflater=LayoutInflater.from(getContext());
                                        convertView=inflater.inflate(R.layout.out_item_list_1,parent,false);
                                        TextView textView = (TextView) convertView.findViewById(R.id.textView);
                                        textView.setText(items.get(position));
                                        Button delBtn = (Button) convertView.findViewById(R.id.btnDelete);
                                        delBtn.setText("Delete");
                                        delBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String key=items.get(position).substring(5,items.get(position).indexOf("\n"));
                                                db.collection("studyGroups").document(key).delete();
                                                activity.recreate();
                                            }
                                        });
                                    return  convertView;
                                }
                            });
                        } else {
                            Toast.makeText(activity, "Error getting documents: ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void setListViewAdapter(List<String> PDFName,ListView listView,AppCompatActivity activity,List<Boolean> isOwner, List<String> path){
        ArrayAdapter arrayAdapter =new ArrayAdapter(activity.getApplicationContext(), R.layout.out_item_list_1,PDFName)
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView mainTV;
                Button mainBTN;
                LayoutInflater inflater=LayoutInflater.from(getContext());
                convertView=inflater.inflate(R.layout.out_item_list_1,parent,false);
                TextView textView = (TextView) convertView.findViewById(R.id.textView);
                textView.setText(PDFName.get(position));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, activity_view.class);
                        Toast.makeText(activity, PDFName.get(position), Toast.LENGTH_SHORT).show();
                        database.getReference(path.get(position)+"/URL").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                intent.putExtra("URL",snapshot.getValue(String.class)); //Put your id to your next Intent
                                activity.startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });
                Button delBtn = (Button) convertView.findViewById(R.id.btnDelete);
                delBtn.setText("Delete");
                if (position<isOwner.size() && !isOwner.get(position)) delBtn.setVisibility(View.GONE);
                delBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        database.getReference(path.get(position)+"/Name").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.getValue(String.class) !=null)
                                    storage.getReference(snapshot.getValue(String.class)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(@NonNull Void unused) {
                                            database.getReference().child(path.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(@NonNull Void unused) {
                                                    Toast.makeText(activity,"Removed successfully!",Toast.LENGTH_LONG).show();
                                                    activity.recreate();
                                                }
                                            });
                                        }
                                    });

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                return  convertView;
            }
        };
        listView.setAdapter(arrayAdapter);
    }

    public void myFileView(List<String> PDF, List<String> PDFName,ListView listView,AppCompatActivity activity){
        databaseReference=FirebaseDatabase.getInstance().getReference("PDF");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()==0)return;
                LinkedList<Boolean> isOwner=new LinkedList<>();
                LinkedList<String> path=new LinkedList<>();
                for (DataSnapshot dep:snapshot.getChildren()) {
                    for (DataSnapshot courses:dep.getChildren()) {
                        for (DataSnapshot file_data:courses.getChildren()) {
                            if(file_data.child("Email").getValue(String.class).equals(mAuth.getCurrentUser().getEmail())){
                                PDFName.add(file_data.getKey());
                                PDF.add(file_data.child("URL").getValue(String.class));
                                isOwner.add(true);
                                path.add("PDF/"+dep.getKey()+"/"+courses.getKey()+"/"+file_data.getKey());
                            }
                        }
                    }
                }
                setListViewAdapter(PDFName,listView,activity,isOwner,path);
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
    public void CheckGroupKey(String department,String course,String Key,int SearchOption,AppCompatActivity activity) {
        Intent intent = new Intent(activity, ViewStudyGroupsActivity.class);
        intent.putExtra("Department",department);
        intent.putExtra("Course",course);
        intent.putExtra("ID",Key);
        intent.putExtra("SearchOption",SearchOption);
        activity.startActivity(intent);
    }
        public void GroupViewByKey(ListView listView,String department,String course,String GroupKey,ArrayList<String>items,AppCompatActivity activity) {
        db.collection("studyGroups")
                .whereEqualTo("Department",department)
                .whereEqualTo("Course",course)
                .whereEqualTo(FieldPath.documentId(),GroupKey)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HashMap<String, String> map = new HashMap<String, String>();
                                String item = "";
                                item += "key: " + document.getId() + "\n";
                                item += "Permissions: " + document.get("Permission", String.class) + "\n";
                                item += "State: " + document.get("State", String.class) + "\n";
                                item += "Comment: " + document.get("Comment", String.class) + "\n";
                                if (!document.get("State", String.class).equals("Front"))
                                    item += "Link: " + document.get("Link", String.class) + "\n";
                                item += "Department: " + document.get("Department", String.class) + "\n";
                                item += "Course: " + document.get("Course", String.class) + "\n";
                                items.add(item);
                            }
                            listView.setAdapter(new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_list_item_1, items) {
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
    public void CheckGroupEmail(String department,String course,String Email,int SearchOption,AppCompatActivity activity) {
        Intent intent = new Intent(activity, ViewStudyGroupsActivity.class);
        intent.putExtra("Department",department);
        intent.putExtra("Course",course);
        intent.putExtra("ID",Email);
        intent.putExtra("SearchOption",SearchOption);

        activity.startActivity(intent);
    }
    public void GroupViewByEmail(ListView listView,String department,String course,String email,ArrayList<String>items,AppCompatActivity activity){
        db.collection("studyGroups")
                .whereEqualTo("Department",department)
                .whereEqualTo("Course",course)
                .whereEqualTo("Email", email)
                //.whereEqualTo("Permission","Public")
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
    public void CheckGroupAll(String department, String course, String Empty, int SearchOption, AppCompatActivity activity) {
        Intent intent = new Intent(activity, ViewStudyGroupsActivity.class);
        intent.putExtra("Department",department);
        intent.putExtra("Course",course);
        intent.putExtra("ID", Empty);
        intent.putExtra("SearchOption",SearchOption);

        activity.startActivity(intent);
    }
    public void AllGroupView(ListView listView,String department,String course,ArrayList<String>items,AppCompatActivity activity){
        db.collection("studyGroups")
                .whereEqualTo("Department",department)
                .whereEqualTo("Course",course)
                //.whereEqualTo("Permission","Public")
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
}
