package com.noveogroup.university_android_task2.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noveogroup.university_android_task2.R;
import com.noveogroup.university_android_task2.data.model.Gender;
import com.noveogroup.university_android_task2.data.model.Person;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<Person> dataSet;

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView name;
        TextView age;
        TextView gender;

        ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.person_view_name);
            age = (TextView) v.findViewById(R.id.person_view_age);
            gender = (TextView) v.findViewById(R.id.person_view_gender);
        }
    }

    public RecyclerViewAdapter(ArrayList<Person> data) {
        this.dataSet = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = dataSet.get(position);
        holder.name.setText(person.getName());
        holder.age.setText(String.valueOf(person.getAge()));
        holder.gender.setText(
                person.getGender() == Gender.MALE ? "man" : "woman"
        );
    }

    public ArrayList<Person> getItems() {
        return dataSet;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(final ArrayList<Person> newItems) {
        dataSet.clear();
        dataSet.addAll(newItems);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
