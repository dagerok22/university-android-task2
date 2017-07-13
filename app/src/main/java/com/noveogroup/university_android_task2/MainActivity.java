package com.noveogroup.university_android_task2;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RadioGroup;

import com.noveogroup.university_android_task2.PersonRecyclerView.RecyclerViewAdapter;
import com.noveogroup.university_android_task2.PersonRecyclerView.SimpleItemTouchHelper;
import com.noveogroup.university_android_task2.data.PersonProvider;
import com.noveogroup.university_android_task2.data.comparator.ComparatorController;
import com.noveogroup.university_android_task2.data.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter mAdapter;
    private ArrayList<Person> mDataset;
    private boolean mIsSortAscending;
    private SortKey mSortKey;

    private static final String ADAPTER_LIST_RESTORE_KEY = "adapter_list";

    public enum SortKey {
        AGE,
        GENDER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsSortAscending = true;
        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.fab);

        RadioGroup mAgeGenderRadioGroup = (RadioGroup) findViewById(R.id.age_gender_radio_group);
        RadioGroup mIsAscendingSortRadioGroup = (RadioGroup) findViewById(R.id.is_ascending_radio_group);

        mDataset = new ArrayList<>();
        final PersonProvider personProvider = PersonProvider.getInstance();

        mDataset.addAll(personProvider.getPersonsList(20));
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.persons_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewAdapter(mDataset);
//        mAdapter.setHasStableIds(true);
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

        mAgeGenderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.age_radio_button) {
                    mSortKey = SortKey.AGE;
                } else {
                    mSortKey = SortKey.GENDER;
                }
                sortAdapterDataAndUpdate();
            }
        });

        mIsAscendingSortRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mIsSortAscending = checkedId == R.id.ascending_radio_button;
                sortAdapterDataAndUpdate();
            }
        });
    }

    private void sortAdapterDataAndUpdate() {
        final ArrayList<Person> oldItems = mAdapter.getItems();
        final ArrayList<Person> newItems = new ArrayList<>(mAdapter.getItems());
        Comparator comparator = ComparatorController.getComparator(mSortKey, mIsSortAscending);
        Collections.sort(newItems, comparator);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            public int getOldListSize() {
                return oldItems.size();
            }

            public int getNewListSize() {
                return newItems.size();
            }

            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
            }

            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Person personOld = oldItems.get(oldItemPosition);
                Person personNew = newItems.get(newItemPosition);
                return personNew.getAge() == personOld.getAge() &&
                        personNew.getGender().equals(personOld.getGender()) &&
                        personNew.getName().equals(personOld.getName());
            }
        });
        mAdapter.setItems(newItems);
        diffResult.dispatchUpdatesTo(mAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ADAPTER_LIST_RESTORE_KEY, mAdapter.getItems());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.setItems(savedInstanceState.<Person>getParcelableArrayList(ADAPTER_LIST_RESTORE_KEY));
    }
}
