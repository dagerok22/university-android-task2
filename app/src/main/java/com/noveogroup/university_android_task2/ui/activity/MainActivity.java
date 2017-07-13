package com.noveogroup.university_android_task2.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RadioGroup;

import com.noveogroup.university_android_task2.ui.adapter.RecyclerViewAdapter;
import com.noveogroup.university_android_task2.R;
import com.noveogroup.university_android_task2.data.PersonProvider;
import com.noveogroup.university_android_task2.data.comparator.ComparatorController;
import com.noveogroup.university_android_task2.data.model.Person;
import com.noveogroup.university_android_task2.ui.adapter.SimpleItemTouchHelper;
import com.noveogroup.university_android_task2.ui.adapter.helper.DiffUtilCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter adapter;
    private ArrayList<Person> dataSet;
    private boolean isSortAscending;
    private SortKey sortKey;
    private RecyclerView recyclerView;

    private static final String ADAPTER_LIST_RESTORE_KEY = "adapter_list";

    public enum SortKey {
        AGE,
        GENDER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isSortAscending = true;

        dataSet = new ArrayList<>();
        final PersonProvider personProvider = PersonProvider.getInstance();

        dataSet.addAll(personProvider.getPersonsList(20));

        InitializeRecyclerView();

        InitializeAndSetUpAdapter();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSet.add(0, personProvider.getPerson());
                adapter.notifyItemInserted(0);
            }
        });

        ((RadioGroup) findViewById(R.id.age_gender_radio_group)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.age_radio_button) {
                    sortKey = SortKey.AGE;
                } else {
                    sortKey = SortKey.GENDER;
                }
                sortAdapterDataAndUpdate();
            }
        });

        ((RadioGroup) findViewById(R.id.is_ascending_radio_group)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                isSortAscending = checkedId == R.id.ascending_radio_button;
                sortAdapterDataAndUpdate();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ADAPTER_LIST_RESTORE_KEY, adapter.getItems());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.setItems(savedInstanceState.<Person>getParcelableArrayList(ADAPTER_LIST_RESTORE_KEY));
    }

    private void InitializeRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.persons_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void InitializeAndSetUpAdapter() {
        adapter = new RecyclerViewAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelper(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void sortAdapterDataAndUpdate() {
        final ArrayList<Person> oldItems = adapter.getItems();
        final ArrayList<Person> newItems = new ArrayList<>(adapter.getItems());
        Comparator comparator = ComparatorController.getComparator(sortKey, isSortAscending);
        Collections.sort(newItems, comparator);

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilCallback(oldItems, newItems));
        adapter.setItems(newItems);
        diffResult.dispatchUpdatesTo(adapter);
    }
}
