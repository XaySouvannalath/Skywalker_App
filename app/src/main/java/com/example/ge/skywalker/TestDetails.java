package com.example.ge.skywalker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class TestDetails extends AppCompatActivity {
TextView txtdetail;
    DatabaseReference refTestDetails;
    ListView listTest;
    Button btaddd;
    EditText etproduct, qty;
    List<TestDetailVal> testDetailVals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_test_details);
     //   FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Intent intent = getIntent();
        txtdetail = (TextView)findViewById(R.id.textView3);
        listTest = (ListView)findViewById(R.id.listTest);
        etproduct = (EditText)findViewById(R.id.etproduct);
        qty = (EditText)findViewById(R.id.qty);
        btaddd= (Button)findViewById(R.id.btaddtestdetails) ;

        testDetailVals = new ArrayList<>();

        txtdetail.setText(intent.getStringExtra(Tests.Test_ID).toString() + "\n " + intent.getStringExtra(Tests.Name).toString() + "\n " + intent.getStringExtra(Tests.LastName).toString());
        refTestDetails = FirebaseDatabase.getInstance().getReference("TestDetails").child(intent.getStringExtra(Tests.Test_ID));
        btaddd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTestDetail();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        refTestDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                testDetailVals.clear();
                for(DataSnapshot testdetailsnapshot: dataSnapshot.getChildren()){
                    TestDetailVal testdetailvalue = testdetailsnapshot.getValue(TestDetailVal.class);
                    testDetailVals.add(testdetailvalue);
                }
                TestDetailList testdetailAdapter = new TestDetailList(TestDetails.this, testDetailVals);
                listTest.setAdapter(testdetailAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveTestDetail() {
        String idd = refTestDetails.push().getKey();
        String product = etproduct.getText().toString();
        Integer quantity = Integer.parseInt(qty.getText().toString());

        TestDetailVal testDetailVal = new TestDetailVal(idd, product,quantity);

        refTestDetails.child(idd).setValue(testDetailVal);
        Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
        etproduct.setText("");
        qty.setText("");
    }
}
