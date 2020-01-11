package com.example.newtestapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CabpoolAdapter extends RecyclerView.Adapter<CabpoolAdapter.CabpoolViewHolder> {

    Context context;
    ArrayList<Cabpools> cabpools;

    public CabpoolAdapter(Context context, ArrayList<Cabpools> cabpools) {
        this.context = context;
        this.cabpools = cabpools;
    }

    @NonNull
    @Override
    public CabpoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CabpoolViewHolder(LayoutInflater.from(context).inflate(R.layout.cabpool_recycler_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CabpoolViewHolder holder, int position) {
        holder.from.setText(cabpools.get(position).getFrom());
        holder.to.setText(cabpools.get(position).getTo());
        holder.date.setText(cabpools.get(position).getDate());
        holder.time.setText(cabpools.get(position).getTime());


    }

    @Override
    public int getItemCount() {
        return cabpools.size();
    }

            public class CabpoolViewHolder extends RecyclerView.ViewHolder{

                TextView from, to, date, time;

                public CabpoolViewHolder(@NonNull View itemView) {
                    super(itemView);

                    from = itemView.findViewById(R.id.from_field_recyclerView);
                    to = itemView.findViewById(R.id.to_field_recyclerView);
                    date = itemView.findViewById(R.id.date_field_recyclerView);
                    time = itemView.findViewById(R.id.time_field_recyclerView);

                }
            }
}
