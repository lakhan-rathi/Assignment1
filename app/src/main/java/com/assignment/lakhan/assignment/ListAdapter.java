package com.assignment.lakhan.assignment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyHolder> {


//    TextView name,age;
    List<Name> namesLists;

    public ListAdapter(List<Name> names){

        this.namesLists = names;

    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleitemview,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        Name name = namesLists.get(i);
        myHolder.name.setText(name.getName());
        myHolder.age.setText(""+name.getAge());


    }

    @Override
    public int getItemCount() {
        return namesLists.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {


        public TextView name,age;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.nametextView);
            age = (TextView)itemView.findViewById(R.id.agetextView);


        }
    }
}
