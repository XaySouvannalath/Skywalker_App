package com.example.ge.skywalker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Home extends Fragment {


Toolbar toolbar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView coursetypeList;
    List<vl_coursetype> valueCourseType;
    private OnFragmentInteractionListener mListener;
    DatabaseReference refCourseType;
    public Fragment_Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Home newInstance(String param1, String param2) {
        Fragment_Home fragment = new Fragment_Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        refCourseType = FirebaseDatabase.getInstance().getReference("CourseTypes");
        valueCourseType = new ArrayList<>();



     //   coursetypeList.setHasFixedSize(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment__home, container, false);
        coursetypeList = (ListView) view.findViewById(R.id.coursetypeList);

      //  ((AppCompatActivity) getActivity()).getSupportActionBar();

        coursetypeList.setDividerHeight(0);
        coursetypeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vl_coursetype vlCourseType = valueCourseType.get(position);
                Toast.makeText(getActivity(), vlCourseType.getCoursetypename().toString(), Toast.LENGTH_SHORT).show();
              //  FragmentManager fragmentManager = getSupportFragmentManager();
             //   FragmentTransaction transaction = fragmentManager.beginTransaction();
              //  transaction.replace(R.id.content, new Fragment_Home()).commit();
                //ffragment = new Fragment();
             // FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
             // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
             // fragmentTransaction.replace(R.id.content, new Fragment_Course()).commit();

//              fragmentTransaction.commit();
                Fragment someFragment = new Fragment_Course();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        refCourseType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueCourseType.clear();
                Integer i = 1;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    ///  TestVal tests = postSnapshot.getValue(TestVal.class);
                    vl_coursetype tess = postSnapshot.getValue(vl_coursetype.class);

                    valueCourseType.add(tess);
                }
                List_CourseType_For_Client testList = new List_CourseType_For_Client(getActivity(), valueCourseType);
                coursetypeList.setAdapter(testList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
        @Override
        public void onStart() {
            super.onStart();

            FirebaseRecyclerAdapter<vl_coursetype,CourseTypeHolder> firebaseRecyclerAdapter  = new FirebaseRecyclerAdapter<vl_coursetype, CourseTypeHolder>(
                    vl_coursetype.class,
                    R.layout.layout_coursetype_client,
                    CourseTypeHolder.class,
                    refCourseType
            ) {
                @Override
                protected void populateViewHolder(CourseTypeHolder viewHolder, vl_coursetype model, int position) {
                    viewHolder.setCourseType(model.getCoursetypename());
                }
            };
            coursetypeList.setAdapter(firebaseRecyclerAdapter);
        }
        public static class CourseTypeHolder extends  RecyclerView.ViewHolder{
            View ctView;
            public CourseTypeHolder(View itemView) {
                super(itemView);
                ctView = itemView;
            }
            public void setCourseType(String courseTypeName){
                TextView txtctname = (TextView)ctView.findViewById(R.id.txtcoursetypenamefor);
                txtctname.setText(courseTypeName);

            }
        }
    */
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
           // Toast.makeText(context, "Home Fragment", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
