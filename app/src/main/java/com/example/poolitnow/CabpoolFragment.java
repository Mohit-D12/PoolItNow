package com.example.poolitnow;

import android.app.ProgressDialog;
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
import java.util.List;

public class CabpoolFragment extends Fragment {

    private View cabpoolView;
    private FloatingActionButton addCabpoolFloatingButton;
    private RecyclerView recyclerView;
    DatabaseReference databaseReference;

    List<Cabpools> cabpools = new ArrayList<>();
    List<String> mDataKey = new ArrayList<>();

    ProgressDialog progress;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.cabpoolView = view;
        init();
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Syncing");
        progress.setCancelable(false);
        loadData();
    }

    private void loadData() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cabpools");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cabpools.clear();;
                mDataKey.clear();
                for(DataSnapshot single:dataSnapshot.getChildren()){
                    cabpools.add(single.getValue(Cabpools.class));
                    mDataKey.add(single.getKey().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        recyclerView = cabpoolView.findViewById(R.id.recyclerView_cabpool);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CabpoolAdapter(cabpools,mDataKey,getActivity(),"e1Form");
        recyclerView.setAdapter(adapter);
    }


}
