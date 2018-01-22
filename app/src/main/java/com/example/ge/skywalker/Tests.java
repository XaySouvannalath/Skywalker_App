package com.example.ge.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tests extends AppCompatActivity {
    String testId;
    DatabaseReference refTest;
    DatabaseReference dbMain, exitTime;
    EditText etname;
    EditText etlastname;
    Button btadd, btgetcountchild ;
    String etId;
    List<TestVal> testval;
    ListView dataList;
    public static final String Test_ID= "testId";
    public static final String Name= "name";
    public static final String LastName= "lastname";
    FloatingActionButton fabadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);
       //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
       // etname = (EditText)findViewById(R.id.etname);
        dataList = (ListView)findViewById(R.id.listData);
        testval = new ArrayList<>();
      //  etlastname = (EditText)findViewById(R.id.etlastname);
      //  btgetcountchild = (Button)findViewById(R.id.btgetcountchild);

        dbMain = FirebaseDatabase.getInstance().getReference();
        fabadd = (FloatingActionButton)findViewById(R.id.fabadd);
      //  refTest = FirebaseDatabase.getInstance().getReference("Tests");
        refTest = dbMain.child("Tests");
        exitTime = dbMain.child("ExitTimes");

      // btadd=(Button)findViewById(R.id.btadd);
     //   btadd.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View v) {
               // SaveData();
      //      }
      //  });
      /*  btgetcountchild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   CountChild();
                showAddDialog();

            }
        });*/
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestVal tess = testval.get(position);
                Intent intent = new Intent(getApplicationContext(), TestDetails.class);

                intent.putExtra(Test_ID, tess.getTestId());
                intent.putExtra(Name, tess.getName());
                intent.putExtra(LastName, tess.getLastname());

                startActivity(intent);

            }
        });
        dataList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TestVal tests = testval.get(position);
                 showUpdateDialog(tests.getTestId(), tests.getName(),tests.getLastname());

                return false;
            }
        });
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    private void CountChild() {
        refTest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long cou = dataSnapshot.getChildrenCount();
                String count = Long.toString(cou);
                Toast.makeText(Tests.this,count, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void showUpdateDialog(final String testId, final String name, final String lastsname){
        AlertDialog.Builder dialogBuilder  = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);

        final TextView txtname = (TextView)dialogView.findViewById(R.id.textView4);
        final EditText etname = (EditText) dialogView.findViewById(R.id.etname);
        final Button btadd = (Button)dialogView.findViewById(R.id.btupdate);
        final Button btdelete =(Button)dialogView.findViewById(R.id.btdelete);
        txtname.setText(name);
        etname.setText(name);
        dialogBuilder.setTitle("Update Test");
        dialogBuilder.setIcon(R.drawable.database);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name = etname.getText().toString();
                if(!TextUtils.isEmpty(name)){

                    updateTest(testId, name, lastsname);
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(Tests.this, "Enter name", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    deleteTest(testId);
                alertDialog.dismiss();
            }
        });

    }
    public void showAddDialog(){
        AlertDialog.Builder dialogBuilder  = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText etlastname = (EditText)dialogView.findViewById(R.id.editText3);
        final EditText etname = (EditText) dialogView.findViewById(R.id.editText2);
        final Button btadd = (Button)dialogView.findViewById(R.id.btaddtest);
        final Button btclear =(Button)dialogView.findViewById(R.id.btclear);
        final Button btcancel =(Button)dialogView.findViewById(R.id.btcancel);
      // /./ txtname.setText(name);
     //   etname.setText(name);



        dialogBuilder.setTitle("Add New Test");
        dialogBuilder.setIcon(R.drawable.database);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  String name = etname.getText().toString();
                if(!TextUtils.isEmpty(etname.getText().toString())){
                    String namevalue = etname.getText().toString();
                    String lastnamevalue = etlastname.getText().toString();
                   // refTest = dbMain.child("Tests");
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tests");

                    String id = databaseReference.push().getKey();
                    TestVal test = new TestVal(id,namevalue ,lastnamevalue);
                    databaseReference.child(id).setValue(test);
                    etlastname.setText("");etname.setText("");
                    Toast.makeText(Tests.this, "Save", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                  //  updateTest(testId, name, lastsname);
                  //  addTest(testId, name, lastsname);
                  //  alertDialog.dismiss();
                }else{
                    Toast.makeText(Tests.this, "Enter name", Toast.LENGTH_SHORT).show();
                }

            }
        });
          etlastname.setOnKeyListener(new View.OnKeyListener() {
              @Override
              public boolean onKey(View v, int keyCode, KeyEvent event) {
                  if(keyCode == KeyEvent.KEYCODE_ENTER){
                     // Toast.makeText(Tests.this, "hello world", Toast.LENGTH_SHORT).show();
                      String namevalue = etname.getText().toString();
                      String lastnamevalue = etlastname.getText().toString();
                      // refTest = dbMain.child("Tests");
                      DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tests");

                      String id = databaseReference.push().getKey();
                      TestVal test = new TestVal(id,namevalue ,lastnamevalue);
                      databaseReference.child(id).setValue(test);
                      etlastname.setText("");etname.setText("");
                      Toast.makeText(Tests.this, "Save", Toast.LENGTH_SHORT).show();
                      alertDialog.dismiss();
                  }
                  return true;
              }
          });
        btclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etname.setText("");
                etlastname.setText("");
                etname.requestFocus();
            }
        });
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
    public boolean addTest(String testid, String name, String lastname){
        testId = refTest.push().getKey();
        String names = etname.getText().toString();
        String lastnames = etlastname.getText().toString();
        TestVal test = new TestVal(testId,names ,lastnames);
        refTest.child(testId).setValue(test);
        Toast.makeText(Tests.this, "Save", Toast.LENGTH_SHORT).show();
        etlastname.setText("");
        etname.setText("");
        return true;
    }
    public boolean deleteTest(String testid){
        DatabaseReference defTest = FirebaseDatabase.getInstance().getReference("Tests").child(testid);
        DatabaseReference defTestDetail = FirebaseDatabase.getInstance().getReference("TestDetails").child(testid);

        defTest.removeValue();
        defTestDetail.removeValue();
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        return true;
    }
    public boolean updateTest(String testid,String name, String lastsname){
       DatabaseReference  databaseReference = FirebaseDatabase.getInstance().getReference("Tests").child(testid);
        TestVal testVal = new TestVal(testid,name,lastsname);

        databaseReference.setValue(testVal);

        Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();


        return true;
    }

    public void SaveData(){
        testId = refTest.push().getKey();
        String names = etname.getText().toString();
        String lastnames = etlastname.getText().toString();
        TestVal test = new TestVal(testId,names ,lastnames);
        refTest.child(testId).setValue(test);
        Toast.makeText(Tests.this, "Save", Toast.LENGTH_SHORT).show();
        etlastname.setText("");
        etname.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        refTest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               testval.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                  ///  TestVal tests = postSnapshot.getValue(TestVal.class);
                    TestVal tess = postSnapshot.getValue(TestVal.class);

                    testval.add(tess);
                }
                TestList testList = new TestList(Tests.this, testval);
                dataList.setAdapter(testList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {

       super.onBackPressed();

       //  etId = exitTime.push().getKey();
      //  Long tsLong = System.currentTimeMillis()/1000;
      //  String ts = tsLong.toString();
      //  ExitTime et = new ExitTime(etId, ts);
      //  exitTime.child(etId).setValue(et);
      //  exitTime.child("-L1d1UjdvAed2rYP_1LL").removeValue(); // this is how to remove value
    }
}
