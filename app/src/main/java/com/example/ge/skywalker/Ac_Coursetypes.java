package com.example.ge.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class Ac_Coursetypes extends AppCompatActivity {
    FloatingActionButton fabAddCourstType;
    DatabaseReference refCourseType;
    ListView listCourseType;
    List<vl_coursetype> valueCourseType;
    public static final String CourseTypeID = "courseTypeId";
    public static final String CourseTypeName = "courseTypeName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_coursetypes);
     //   FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        refCourseType = FirebaseDatabase.getInstance().getReference("CourseTypes");
        refCourseType.keepSynced(true);
        listCourseType = (ListView)findViewById(R.id.listCourseType);
        fabAddCourstType = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        valueCourseType = new ArrayList<>();
        fabAddCourstType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addDialog();
            }
        });
        listCourseType.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                vl_coursetype tests = valueCourseType.get(position);
                updateDialog(tests.getCoursetypeid(), tests.getCoursetypename());
                return true;
            }
        });
        listCourseType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vl_coursetype tess = valueCourseType.get(position);
                Intent intent = new Intent(getApplicationContext(), Ac_Courses.class);
                intent.putExtra(CourseTypeID, tess.getCoursetypeid());
                intent.putExtra(CourseTypeName, tess.getCoursetypename());
                startActivity(intent);
            }
        });
    }
    public  void init(){

    }

    @Override
    protected void onStart() {
        super.onStart();
        refCourseType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueCourseType.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    ///  TestVal tests = postSnapshot.getValue(TestVal.class);
                    vl_coursetype tess = postSnapshot.getValue(vl_coursetype.class);

                    valueCourseType.add(tess);
                }
                List_CourseTypes testList = new List_CourseTypes(Ac_Coursetypes.this, valueCourseType);
                listCourseType.setAdapter(testList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addDialog(){

        AlertDialog.Builder dialogBuilder  = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_coursetype_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText etcoursetypename = (EditText)dialogView.findViewById(R.id.etcoursetypename);
        final Button btaddcoursetype = (Button)dialogView.findViewById(R.id.btaddcoursetype);

        dialogBuilder.setTitle("ເພີ່ມປະເພດຫຼັກສູດໃໝ່");
        dialogBuilder.setIcon(R.drawable.database);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btaddcoursetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coursetypenamevalue = etcoursetypename.getText().toString();
                if(!TextUtils.isEmpty(coursetypenamevalue))
                {

                   // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tests");

                    String id = refCourseType.push().getKey();
                    vl_coursetype test = new vl_coursetype(coursetypenamevalue,id);
                    refCourseType.child(id).setValue(test);
                    etcoursetypename.setText("");
                    etcoursetypename.requestFocus();
                    Toast.makeText(Ac_Coursetypes.this, "ບັນທຶກສໍາເລັດ", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

                }else{
                    Toast.makeText(Ac_Coursetypes.this, "ກະລຸນາປ້ອນຂໍ້ມູນ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void updateDialog(final String courseTypeId, final String courseTypeName){
        AlertDialog.Builder dialogBuilder  = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_coursetype_dialog,null);
        dialogBuilder.setView(dialogView);

       // final TextView txtcoursetypename = (TextView)dialogView.findViewById(R.id.editText4);
        final EditText txtcoursetype = (EditText) dialogView.findViewById(R.id.editText4);
        final Button btupdatecoursetype = (Button)dialogView.findViewById(R.id.bteditcoursetype);
        final Button btdeletecoursetype =(Button)dialogView.findViewById(R.id.btdeletecoursetype);
       // txtname.setText(name);
        txtcoursetype.setText(courseTypeName);
        dialogBuilder.setTitle("ແກ້ໄຂຂໍ້ມູນ");
        dialogBuilder.setIcon(R.drawable.database);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btupdatecoursetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coursetype = txtcoursetype.getText().toString();
                if(!TextUtils.isEmpty(coursetype)){
                    updateCourseType(courseTypeId,coursetype );
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(Ac_Coursetypes.this, "ກະລຸນາປ້ອນຂໍ້ມຸນ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btdeletecoursetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coursetype = txtcoursetype.getText().toString();
                if(!TextUtils.isEmpty(coursetype)){
                    deleteCourseType(courseTypeId);
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(Ac_Coursetypes.this, "ກະລຸນາປ້ອນຂໍ້ມຸນ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean updateCourseType(String courseTypeId, String CourseTypeName){
        DatabaseReference  databaseReference = FirebaseDatabase.getInstance().getReference("CourseTypes").child(courseTypeId);
        vl_coursetype vlCoursetype = new vl_coursetype(CourseTypeName,courseTypeId);
        databaseReference.setValue(vlCoursetype);
        Toast.makeText(this, "ແກ້ໄຂສໍາເລັດ!", Toast.LENGTH_SHORT).show();
        return true;
    }
    private boolean deleteCourseType(String courseTypeId){
        DatabaseReference refCourseType = FirebaseDatabase.getInstance().getReference("CourseTypes").child(courseTypeId);
        DatabaseReference refCourse = FirebaseDatabase.getInstance().getReference("Courses").child(courseTypeId);

        refCourseType.removeValue();
        refCourse.removeValue();
        Toast.makeText(this, "ລົບສໍາເລັດ", Toast.LENGTH_SHORT).show();
        return true;
    }
}
