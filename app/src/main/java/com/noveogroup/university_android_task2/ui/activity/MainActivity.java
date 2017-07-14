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

import com.noveogroup.university_android_task2.data.model.SortKey;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter adapter;
    private ArrayList<Person> dataSet;
    private boolean isSortAscending;
    private SortKey sortKey;
    private RecyclerView recyclerView;

    private static final String ADAPTER_LIST_RESTORE_KEY = "adapter_list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isSortAscending = true;

        dataSet = new ArrayList<>();
        final PersonProvider personProvider = PersonProvider.getInstance();

        dataSet.addAll(personProvider.getPersonsList(7));

        initializeRecyclerView();

        initializeAndSetUpAdapter();

        if (!dataSet.isEmpty()) {
            sortAdapterDataAndUpdate();
        }

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = personProvider.getPerson();
                int placeToInsert = findPlaceToInsert(adapter, person);
                adapter.getItems().add(placeToInsert, person);
                adapter.notifyItemInserted(placeToInsert);
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

    private void initializeRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.persons_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void initializeAndSetUpAdapter() {
        adapter = new RecyclerViewAdapter(dataSet);
//        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelper(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private int findPlaceToInsert(RecyclerViewAdapter adapter, Person itemToInsert) {
        List<Person> list = adapter.getItems();
        Comparator comparator = ComparatorController.getComparator(sortKey, isSortAscending);
        for (Person item :
                list) {
            if (comparator.compare(itemToInsert, item) == -1) {
                return list.indexOf(item);
            }
        }
        return list.size() - 1;
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
