package com.assignment.lakhan.assignment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {


    Button addButton;
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    List<Name> nameList = new ArrayList<>();
    RelativeLayout adduserLayout;
    TextView nameText,ageText;
    Button add;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    boolean exit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adduserLayout = (RelativeLayout)findViewById(R.id.addUser);


        recyclerView  = (RecyclerView)findViewById(R.id.reclycleView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        nameList.add(new Name("Lakhan","455"));
        nameList.add(new Name("Lakhan","4558"));
        listAdapter = new ListAdapter(nameList);
        recyclerView.setAdapter(listAdapter);



//        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference().child("USER");

        myRef1.keepSynced(true);
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String name = (String) messageSnapshot.child("name").getValue();
                    String age = (String) messageSnapshot.child("age").getValue() ;
                    nameList.add(new Name(name,age));
                    listAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addButton =  (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adduserLayout.setVisibility(View.VISIBLE);

                recyclerView.setVisibility(View.GONE);
                addButton.setVisibility(View.GONE);

            }
        });

        nameText = (TextView)findViewById(R.id.nameText);
        ageText = (TextView)findViewById(R.id.ageText);

        add = (Button)findViewById(R.id.addButtonFinal);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameText.getText().toString();
                String age = ageText.getText().toString();

                if (name.length()<0 || age.length()<0 || name.isEmpty() || age.isEmpty()){

//                    Toast.makeText(this,"Please enter name or age",Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Please enter name or age",Toast.LENGTH_LONG).show();
                }else {

                    final Name name1 = new Name(name,age);
                    String key = myRef.child("USER").push().getKey();

                    myRef.child("USER").child(key).setValue(name1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                nameList.add(name1);
                                listAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),"added",Toast.LENGTH_LONG).show();


                            }else {

                                Toast.makeText(getApplicationContext(),"something went wrong please check your internet connection",Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                }


            }
        });



    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (!exit)
            exit = true;
        else
            super.onBackPressed();
        recyclerView.setVisibility(View.VISIBLE);
        addButton.setVisibility(View.VISIBLE);

        adduserLayout.setVisibility(View.GONE);

    }
}
