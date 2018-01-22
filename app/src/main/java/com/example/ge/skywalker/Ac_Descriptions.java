package com.example.ge.skywalker;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ge.skywalker.Heading.vl_description;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.ge.skywalker.Ac_Chapters.globalCourse;
import static com.example.ge.skywalker.Ac_Courses.globalCourseType;
import static com.example.ge.skywalker.Ac_Headings.HeadingID;
import static com.example.ge.skywalker.Ac_Headings.HeadingName;
import static com.example.ge.skywalker.Ac_Headings.globalChapter;

public class Ac_Descriptions extends AppCompatActivity {
    Toolbar toolbar;
    public static final String DescriptionID = "descriptionID";
    public static final String Description = "description";
    Intent intent;
    List<vl_description> valueDescription;
    ListView listDescription;
    FloatingActionButton fabAddDescription;
    DatabaseReference refDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac__descriptions);
        toolbar = (Toolbar)findViewById(R.id.toolbar2);
        intent = getIntent();

        fabAddDescription = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        listDescription = (ListView)findViewById(R.id.listDescription);
        valueDescription = new ArrayList<>();
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle(intent.getStringExtra(HeadingName.toString()));
        refDescription = FirebaseDatabase.getInstance().getReference("Descriptions").child(globalCourseType).child(globalCourse).child(globalChapter).child(intent.getStringExtra(HeadingID.toString()));
        fabAddDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });
        listDescription.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                vl_description vlDescription = valueDescription.get(position);
                updateDialog(vlDescription.getDescriptionId(), vlDescription.getDescription());
                return true;
            }
        });
    }

    private void updateDialog(final String descriptionId, String description) {
        AlertDialog.Builder dialogBuilder  = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_description_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText etdescription = (EditText)dialogView.findViewById(R.id.etdescription);
        final Button btdeletedescription =(Button)dialogView.findViewById(R.id.btdeletedescription);
        final Button btupdatedescription = (Button)dialogView.findViewById(R.id.btupdatedescription);

      final  ProgressDialog pgd = new ProgressDialog(this);

        etdescription.setText(description);
        dialogBuilder.setTitle("ແກ້ໄຂຂໍ້ມູນ");
        dialogBuilder.setIcon(R.drawable.database);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btupdatedescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pgd.setMessage("ກໍາລັງແກ້ໄຂ...");
                pgd.show();
                String descriptionValueForUpdate = etdescription.getText().toString();
                if(!TextUtils.isEmpty(descriptionValueForUpdate)){
                    vl_description vlll = new vl_description(descriptionId, descriptionValueForUpdate);
                    refDescription.child(descriptionId).setValue(vlll).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pgd.dismiss();
                            alertDialog.dismiss();
                            Toast.makeText(Ac_Descriptions.this, "ແກ້ໃຊສໍາເລັດ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else
                {
                    Toast.makeText(Ac_Descriptions.this, "ກະລຸນາປ້ອນຂໍ້ມຸນ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btdeletedescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDescription(descriptionId);
                alertDialog.dismiss();
            }
        });


    }

    private void deleteDescription(String descriptionId) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("ກໍາລັງລົບ...");
        progressDialog.show();
        refDescription.child(descriptionId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();

            }
        });
    }

    private void addDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_description_dialog,null);
        dialogBuilder.setView(dialogView);
        final TextView txtdescription =(TextView) dialogView.findViewById(R.id.txtdescription);
        Button btadddescription = (Button)dialogView.findViewById(R.id.btadddescription);
        Button btcancel = (Button)dialogView.findViewById(R.id.btcancel);

        dialogBuilder.setTitle("ເພີ້ມຄໍາອະທິບາຍ");
        dialogBuilder.setIcon(R.drawable.database);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        final ProgressDialog pgd = new ProgressDialog(this);
        pgd.setMessage("ກໍາລັງບັນທຶກ...");
        pgd.setIcon(R.mipmap.ic_file_upload_white_24dp);
        pgd.setCancelable(true);
        btadddescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pgd.show();
                String descriptionValue = txtdescription.getText().toString();
                String id = refDescription.push().getKey();
                if(!TextUtils.isEmpty(descriptionValue)){
                    vl_description vlDescription = new vl_description(id, descriptionValue);
                    refDescription.child(id).setValue(vlDescription).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pgd.dismiss();
                            alertDialog.dismiss();
                            Toast.makeText(Ac_Descriptions.this, "ບັນທຶກສໍາເລັດ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refDescription.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueDescription.clear();
                for(DataSnapshot descriptionSnapshot: dataSnapshot.getChildren()){
                    vl_description tess = descriptionSnapshot.getValue(vl_description.class);
                    valueDescription.add(tess);
                }
                List_Descriptions descriptionlist = new List_Descriptions(Ac_Descriptions.this,valueDescription);
                listDescription.setAdapter(descriptionlist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
