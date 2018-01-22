package com.example.ge.skywalker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static com.example.ge.skywalker.Ac_Chapters.ChapterID;
import static com.example.ge.skywalker.Ac_Chapters.ChapterName;
import static com.example.ge.skywalker.Ac_Chapters.ChapterNumber;
import static com.example.ge.skywalker.Ac_Chapters.CourseIDForHeading;
import static com.example.ge.skywalker.Ac_Courses.CourseTypeIDForChapter;
import static com.example.ge.skywalker.R.id.listChapter;



public class Ac_Headings extends AppCompatActivity {
Toolbar toolbar;
    private Handler handler = new Handler();
    public static final String HeadingID = "headingId";
    public static final String HeadingName = "headingName";
    public static String globalChapter;
    Intent intent;
    int progressStatus = 0;
    List<vl_heading> valueHeading;
    ListView listHeading;
    FloatingActionButton fabAddHeading;
    private   Uri imageUri;
    DatabaseReference refHeading, refRoot, refChapter, refCourse;
    public static final int GALLERY_REQUEST = 1;
    public static String ChapterIDForHeading;
    public static String courseIDForAddheading;
    EditText etheadingname;
    public static String sentCourseID;
 private   StorageReference mStorage;
   private ProgressDialog pgd;
     ImageButton imgHeadingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac__headings);
        toolbar = (Toolbar)findViewById(R.id.toolbar2);
        intent = getIntent();
        fabAddHeading =(FloatingActionButton)findViewById(R.id.floatingActionButton);
        listHeading = (ListView)findViewById(listChapter);
        valueHeading = new ArrayList<>();
        pgd = new ProgressDialog(this);
       setSupportActionBar(toolbar);
       toolbar.setTitleTextColor(Color.WHITE);
     toolbar.setSubtitleTextColor(Color.WHITE);
        final String cid = intent.getStringExtra(CourseIDForHeading.toString());
        globalChapter = intent.getStringExtra(ChapterID);
       getSupportActionBar().setTitle("ບົດທີ: " + intent.getStringExtra(ChapterNumber.toString()) + " " + intent.getStringExtra(ChapterName.toString()));
        mStorage = FirebaseStorage.getInstance().getReference();
        refHeading = FirebaseDatabase.getInstance().getReference("Headings").child(CourseTypeIDForChapter).child(CourseIDForHeading).child(intent.getStringExtra(ChapterID.toString()));
       // refChapter= FirebaseDatabase.getInstance().getReference("Headings").child(CourseTypeIDForChapter).child(CourseIDForHeading).child(vlChapter.getChapterId());
        fabAddHeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // startActivity(new Intent(Ac_Headings.this,add_heading_activity.class));
               // Intent intent = new Intent(Ac_Headings.this,add_heading_activity.class);
               // intent.putExtra(sentCourseID, cid);
               // startActivity(intent);
               addDialog();
            //    Toast.makeText(Ac_Headings.this, globalCourseType +"\n"+ globalCourse+"\n" + globalChapter, Toast.LENGTH_SHORT).show();
            }
        });
        listHeading.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                vl_heading vlHeading = valueHeading.get(position);
                updateDialog(vlHeading.getHeadingId(), vlHeading.getHeadingName());
                return true;
            }
        });
        listHeading.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vl_heading vlHeading = valueHeading.get(position);
                Intent intent2 = new Intent(getApplicationContext(),Ac_Descriptions.class);
                intent2.putExtra(HeadingID, vlHeading.getHeadingId());
                intent2.putExtra(HeadingName,vlHeading.getHeadingName());
                startActivity(intent2);

            }
        });
    }
     ImageButton imageHeadingButton;
    private void updateDialog(final String headingId, final String headingName) {
        AlertDialog.Builder dialogBuilder  = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_heading_dialog,null);
        dialogBuilder.setView(dialogView);

    //  imageHeadingButton = (ImageButton)dialogView.findViewById(R.id.imageButton2);
        final EditText etheadingname = (EditText)dialogView.findViewById(R.id.etheadingname);
        final Button btupdateheading =(Button)dialogView.findViewById(R.id.btupdateheading);
        final Button btdeleteheading = (Button)dialogView.findViewById(R.id.btdeleteheading);

      //  imageHeadingButton.setImageURI(headingImageUrl);
        etheadingname.setText(headingName);
        dialogBuilder.setTitle("ແກ້ໄຂຂໍ້ມູນ");
        dialogBuilder.setIcon(R.drawable.database);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btupdateheading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgd.setMessage("ກໍາລັງແກ້ໄຂ...");
                pgd.show();
                String headingNameValue = etheadingname.getText().toString();
                final String imgUrl="";
                if(!TextUtils.isEmpty(headingNameValue)){
                  /*  StorageReference filepath = mStorage.child("HeadingImage").child(imageUri.getLastPathSegment());
                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")   Uri downloadUrl  =taskSnapshot.getDownloadUrl();
                          //  imgUrl= downloadUrl.toString();

                        }
                    });*/
                   // updateHeading(headingId, headingName, imgUrl);
                  //  DatabaseReference   refRoot1 = FirebaseDatabase.getInstance().getReference("Chapters").child(CourseTypeIDForChapter);
                    //DatabaseReference    refChapter1 = refRoot1.child(intent.getStringExtra(CourseID.toString()));
                  //  DatabaseReference ch = refChapter1.child(chapterId);


                 //   vl_chapter vll = new vl_chapter(chapterId, chapterName, chapterNumber);
                  //  Toast.makeText(this, chapterName, Toast.LENGTH_SHORT).show();
                  //  ch.setValue(vll);
                    vl_heading vll = new vl_heading(headingId, headingNameValue);
                    refHeading.child(headingId).setValue(vll).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Ac_Headings.this, "Save", Toast.LENGTH_SHORT).show();
                            pgd.dismiss();
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        });
        btdeleteheading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHeading(headingId);
                alertDialog.dismiss();
            }
        });


    }

    private void deleteHeading(String headingId) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("ກໍາລັງລົບ...");
        progressDialog.show();
        refHeading.child(headingId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        refHeading.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueHeading.clear();
                for(DataSnapshot chapterSnapshot: dataSnapshot.getChildren()){
                    vl_heading tess = chapterSnapshot.getValue(vl_heading.class);
                    valueHeading.add(tess);
                }
                List_Headings headingList = new List_Headings(Ac_Headings.this,valueHeading);
                listHeading.setAdapter(headingList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_heading_dialog,null);
        dialogBuilder.setView(dialogView);

     etheadingname = (EditText)dialogView.findViewById(R.id.etheadingname);
        // imgHeadingButton  = (ImageButton) dialogView.findViewById(R.id.imageButton);
        final Button btaddheading =(Button)dialogView.findViewById(R.id.btaddheading);
        final Button btcancel = (Button)dialogView.findViewById(R.id.btcancel);

        dialogBuilder.setTitle("ເພີ້ມຫົວຂໍ້");
        dialogBuilder.setIcon(R.drawable.database);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

      /* imgHeadingButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
               startActivityForResult(galleryIntent,GALLERY_REQUEST);
           }
       });*/
        btaddheading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgd.setMessage("ກໍາລັງບັນທຶກ...");
                pgd.setIcon(R.mipmap.ic_file_upload_white_24dp);
                pgd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pgd.setMax(100);
                pgd.setCancelable(false);
                progressStatus = 0;
                pgd.show();


                final String headingNameValue = etheadingname.getText().toString();
                String imageURLValue;
                final String id = refHeading.push().getKey();
                if(!TextUtils.isEmpty(headingNameValue)){
                   /* StorageReference filepath = mStorage.child("HeadingImage").child(imageUri.getLastPathSegment());
                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while(progressStatus < pgd.getMax()){
                                        // If user's click the cancel button from progress dialog

                                        // Update the progress status
                                        progressStatus +=1;

                                        // Try to sleep the thread for 200 milliseconds
                                        try{
                                            Thread.sleep(10);
                                        }catch(InterruptedException e){
                                            e.printStackTrace();
                                        }

                                        // Update the progress bar
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Update the progress status
                                                pgd.setProgress(progressStatus);
                                                //   tv.setText(progressStatus+"");
                                                // If task execution completed
                                                if(progressStatus == pgd.getMax()){
                                                    // Dismiss/hide the progress dialog
                                                    @SuppressWarnings("VisibleForTests")   Uri downloadUrl  =taskSnapshot.getDownloadUrl();



                                                    //tv.setText("Operation completed.");
                                                }
                                            }
                                        });
                                    }

                                }
                            }).start();
                            // pgd.dismiss();

                        }

                    });*/
                    vl_heading vlHeading = new vl_heading(id, headingNameValue);
                    refHeading.child(id).setValue(vlHeading).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pgd.dismiss();
                            Toast.makeText(Ac_Headings.this, "ບັນທຶກສໍາເລັດ", Toast.LENGTH_SHORT).show();
                            progressStatus = 0;
                            alertDialog.dismiss();
                        }
                    });

                }
                else{
                    Toast.makeText(Ac_Headings.this, "ກະລຸນາປ້ອນຂໍ້ມູນ", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    private void addHeading() {
        pgd.setMessage("ກໍາລັງບັນທຶກ...");
        pgd.setIcon(R.mipmap.ic_file_upload_white_24dp);
        pgd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        pgd.show();


        String headingNameValue = etheadingname.getText().toString();
        String imageURLValue;
        String id = refHeading.push().getKey();
        if(!TextUtils.isEmpty(headingNameValue)){
            StorageReference filepath = mStorage.child("HeadingImage").child(imageUri.getLastPathSegment());
            filepath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    pgd.dismiss();
                    Toast.makeText(Ac_Headings.this, "ບັນທຶກສໍາເລັດ", Toast.LENGTH_SHORT).show();
                  //  alertDialog.dismiss();
                }
            });

        }
        else{
            Toast.makeText(Ac_Headings.this, "ກະລຸນາປ້ອນຂໍ້ມູນ", Toast.LENGTH_SHORT).show();
        }
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==GALLERY_REQUEST && resultCode == RESULT_OK){
             imageUri  = data.getData();
          imgHeadingButton.setImageURI(imageUri);
            imageHeadingButton.setImageURI(imageUri);

        }
    }*/
}
