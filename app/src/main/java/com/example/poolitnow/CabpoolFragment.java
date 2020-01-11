package com.example.newtestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CabpoolFragment extends Fragment {

    private View cabpoolView;
    private FloatingActionButton addCabpoolFloatingButton;
    private RecyclerView recyclerView;
    DatabaseReference databaseReference;

    ArrayList<Cabpools> cabpools;
    FirebaseRecyclerOptions<Cabpools> firebaseRecyclerOptions;
    CabpoolAdapter adapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        cabpoolView = inflater.inflate(R.layout.cabpool_fragment, container, false);
        addCabpoolFloatingButton = cabpoolView.findViewById(R.id.AddButton_Create);
        recyclerView = cabpoolView.findViewById(R.id.recyclerView_cabpool);

        addCabpoolFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CreateCabpool.class));
            }
        });

        return cabpoolView;
    }

    @Override
    public void onStart() {
        super.onStart();
        cabpools = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cabpools");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Cabpools cabpool = dataSnapshot.getValue(Cabpools.class);
                    cabpools.add(cabpool);
                }
                adapter = new CabpoolAdapter(getContext(),cabpools);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"idk",Toast.LENGTH_SHORT).show();
            }
        });



    }
}
