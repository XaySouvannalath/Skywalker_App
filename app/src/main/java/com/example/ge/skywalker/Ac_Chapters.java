package com.example.ge.skywalker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.ge.skywalker.Ac_Courses.CourseID;
import static com.example.ge.skywalker.Ac_Courses.CourseName;
import static com.example.ge.skywalker.Ac_Courses.CourseTypeIDForChapter;

public class Ac_Chapters extends AppCompatActivity {
Toolbar toolbar;
    Intent intent;
    List<vl_chapter> valueChapter;
    ListView listChapter;
    FloatingActionButton fabAddChapter;
    DatabaseReference refChapter;
    DatabaseReference refRoot;
    DatabaseReference refCourse;
    public static String CourseIDForHeading;
    public static String globalCourse;
    public static final String ChapterID = "chapterId";
    public static final String ChapterName = "chapterNameValue";
    public static final String ChapterNumber = "chapterNumberValue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac__chapters);

        toolbar = (Toolbar)findViewById(R.id.toolbar2);
        fabAddChapter = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        listChapter = (ListView)findViewById(R.id.listChapter);
        valueChapter = new ArrayList<>();
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra(CourseName.toString()));
        CourseIDForHeading = intent.getStringExtra(CourseID);
        globalCourse = intent.getStringExtra(CourseID);
        refRoot = FirebaseDatabase.getInstance().getReference("Chapters").child(CourseTypeIDForChapter);
        refChapter = refRoot.child(intent.getStringExtra(CourseID.toString()));

      //  refChapter = FirebaseDatabase.getInstance().getReference("Chapters").child();
        fabAddChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });
        listChapter.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                vl_chapter vlChapter = valueChapter.get(position);
                updateDialog(vlChapter.getChapterId(), vlChapter.getChapterNameValue(), vlChapter.getChapterNumberValue());
                return true;
            }
        });
        listChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vl_chapter vlChapter = valueChapter.get(position);
                Intent intent1 = new Intent(getApplicationContext(), Ac_Headings.class);
                intent1.putExtra(ChapterID, vlChapter.getChapterId());
                intent1.putExtra(ChapterName, vlChapter.getChapterNameValue());
                intent1.putExtra(ChapterNumber, vlChapter.getChapterNumberValue());
             //   intent1.putExtra(CourseIDForHeading,intent.getStringExtra(CourseID) );
                //  intent.putExtra(CourseTypeID, )
                startActivity(intent1);
            }
        });


    }

    private void updateDialog(final String chapterId, final String chapterName, final String chapterNumber) {
        AlertDialog.Builder dialogBuilder  = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_chapter_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText etchapternumber = (EditText)dialogView.findViewById(R.id.etchapternumber1);
        final EditText etchaptername = (EditText)dialogView.findViewById(R.id.etchaptername1);
        final Button btupdatechapter =(Button)dialogView.findViewById(R.id.btupdatechapter);
        final Button btdeletechapter = (Button)dialogView.findViewById(R.id.btdeletechapter);

        etchapternumber.setText(chapterNumber);
        etchaptername.setText(chapterName);

        dialogBuilder.setTitle("ແກ້ໄຂຂໍ້ມູນ");
        dialogBuilder.setIcon(R.drawable.database);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btupdatechapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chapterNameValue = etchaptername.getText().toString();
                String chapterNumberValue = etchapternumber.getText().toString();

                if(!TextUtils.isEmpty(chapterNameValue)){

                    updateChapter(chapterId, chapterNameValue, chapterNumberValue);
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(Ac_Chapters.this, "ກະລຸນາປ້ອນຂໍ້ມູນ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btdeletechapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChapter(chapterId);
                alertDialog.dismiss();
            }
        });
    }

    private boolean deleteChapter(String chapterId) {
        DatabaseReference   refRoot1 = FirebaseDatabase.getInstance().getReference("Chapters").child(CourseTypeIDForChapter);
        DatabaseReference    refChapter1 = refRoot1.child(intent.getStringExtra(CourseID.toString()));
        DatabaseReference ch = refChapter1.child(chapterId);
      //  vl_chapter vlChapter = new vl_chapter(chapterId, chapterName, chapterNumber);
        // refChapter1.child(chapterId).setValue(vlChapter);
        ch.removeValue();
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        refChapter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueChapter.clear();
                for(DataSnapshot chapterSnapshot: dataSnapshot.getChildren()){
                    vl_chapter tess = chapterSnapshot.getValue(vl_chapter.class);
                    valueChapter.add(tess);
                }
                List_Chapters chapterList = new List_Chapters(Ac_Chapters.this,valueChapter);
                listChapter.setAdapter(chapterList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean updateChapter(String chapterId, String chapterName, String chapterNumber) {

        DatabaseReference   refRoot1 = FirebaseDatabase.getInstance().getReference("Chapters").child(CourseTypeIDForChapter);
        DatabaseReference    refChapter1 = refRoot1.child(intent.getStringExtra(CourseID.toString()));
        DatabaseReference ch = refChapter1.child(chapterId);
        vl_chapter vll = new vl_chapter(chapterId, chapterName, chapterNumber);
        Toast.makeText(this, chapterName, Toast.LENGTH_SHORT).show();
        ch.setValue(vll);
        return true;
      //  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chapters").child(intent.getStringExtra(Ac_Courses.CourseID));
      //  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chapters").child(CourseTypeIDForChapter);
     //   DatabaseReference refChapter = databaseReference.child(intent.getStringExtra(CourseID.toString()));

       // refRoot = FirebaseDatabase.getInstance().getReference("Chapters").child(CourseTypeIDForChapter);
      //  refChapter = refRoot.child(intent.getStringExtra(CourseID.toString()));
       // DatabaseReference refCourse = databaseReference.child(CourseTypeIDForChapter);
      //  DatabaseReference refChapter = refCourse.child(chapterId);

       // DatabaseReference ch = FirebaseDatabase.getInstance().getReference("Chapters");
       // DatabaseReference ch1 = ch.child("-L1xIAuSpdrXHXEBlES8");
       // DatabaseReference ch3 = ch1.child("-L1xICvliMS-HdBbnaEz");

        //.child("-L1xAD542wtGJ46K5yEk");

       // refChapter1.child(chapterId).setValue(vlChapter);
      //  Toast.makeText(this, vlChapter.toString(), Toast.LENGTH_LONG).show();
       // ch3.child(chapterId).setValue("hello");

       // ch.setValue(vlChapter).
      //  Toast.makeText(this, chapterId + " " + chapterName + " " + chapterNumber, Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this, CourseTypeIDForChapter, Toast.LENGTH_LONG).show();
      //  Toast.makeText(this, intent.getStringExtra(CourseID.toString()), Toast.LENGTH_LONG).show();
      //  Toast.makeText(this, chapterId, Toast.LENGTH_LONG).show();

    }

    private void addDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_chapter_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText etchapternumber = (EditText)dialogView.findViewById(R.id.etchapternumber);
        final EditText etchaptername = (EditText)dialogView.findViewById(R.id.etchaptername);
        final Button btaddchapter =(Button)dialogView.findViewById(R.id.btaddchapter);
        final Button btcancel = (Button)dialogView.findViewById(R.id.btcancel);

        dialogBuilder.setTitle("ເພີ້ມບົດຮຽນ");
        dialogBuilder.setIcon(R.drawable.database);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btaddchapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chapterNameValue = etchaptername.getText().toString();
                String chapterNumberValue = etchapternumber.getText().toString();

                if(!TextUtils.isEmpty(chapterNameValue)){
                    String id = refChapter.push().getKey();
                    vl_chapter test = new vl_chapter(id, chapterNameValue, chapterNumberValue);
                    refChapter.child(id).setValue(test);
                    Toast.makeText(Ac_Chapters.this, chapterNameValue + chapterNumberValue, Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(Ac_Chapters.this, "ກະລຸນາປ້ອນຂໍ້ມູນ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
