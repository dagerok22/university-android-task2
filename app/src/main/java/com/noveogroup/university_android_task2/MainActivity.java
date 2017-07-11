package com.noveogroup.university_android_task2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.noveogroup.university_android_task2.PersonRecyclerView.RecyclerViewAdapter;
import com.noveogroup.university_android_task2.PersonRecyclerView.SimpleItemTouchHelper;
import com.noveogroup.university_android_task2.data.PersonProvider;
import com.noveogroup.university_android_task2.data.model.Person;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Person> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mDataset = new ArrayList<>();
        final PersonProvider personProvider = PersonProvider.getInstance();

//        mDataset.addAll(personProvider.getPersonsList(20));
        mRecyclerView = (RecyclerView) findViewById(R.id.persons_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelper(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataset.add(0, personProvider.getPerson());
                mAdapter.notifyItemInserted(0);
            }
        });
    }

}
