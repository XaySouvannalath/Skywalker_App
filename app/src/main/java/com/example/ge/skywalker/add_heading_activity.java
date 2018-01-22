package com.example.ge.skywalker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.ge.skywalker.Ac_Chapters.ChapterID;
import static com.example.ge.skywalker.Ac_Chapters.CourseIDForHeading;
import static com.example.ge.skywalker.Ac_Courses.CourseTypeIDForChapter;
import static com.example.ge.skywalker.Ac_Headings.sentCourseID;

public class add_heading_activity extends AppCompatActivity {
    public static final int GALLERY_REQUEST = 1;
     ImageButton imgHeadingButton;
     EditText etheadingname;
     Button btaddheading;
     Button btcancel;
    DatabaseReference refHeading;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_heading_activity);
        refHeading = FirebaseDatabase.getInstance().getReference("Headings").child(CourseTypeIDForChapter).child(CourseIDForHeading).child(intent.getStringExtra(ChapterID.toString()));
         etheadingname = (EditText)findViewById(R.id.etheadingname);
        intent = getIntent();
         //imgHeadingButton = (ImageButton) findViewById(R.id.imageButton);
        btaddheading     =(Button)findViewById(R.id.btaddheading);
        btcancel  = (Button)findViewById(R.id.btcancel);
        Toast.makeText(this, intent.getStringExtra(sentCourseID.toString()), Toast.LENGTH_SHORT).show();
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
                addHeading();
            }
        });
    }

    private void addHeading() {
        String headingNameValue = etheadingname.getText().toString();
        String headingImageURL;
      // String id = ref
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==GALLERY_REQUEST && resultCode == RESULT_OK){
            Uri imageUri  = data.getData();
              imgHeadingButton.setImageURI(imageUri);

        }
    }
}
