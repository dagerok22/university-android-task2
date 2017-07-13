package com.noveogroup.university_android_task2;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.RadioGroup;

import com.noveogroup.university_android_task2.PersonRecyclerView.RecyclerViewAdapter;
import com.noveogroup.university_android_task2.PersonRecyclerView.SimpleItemTouchHelper;
import com.noveogroup.university_android_task2.data.PersonProvider;
import com.noveogroup.university_android_task2.data.model.Person;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Person> mDataset;
    private RadioGroup mAgeGenderRadioGroup;
    private RadioGroup mIsAscendingSortRadioGroup;
    private boolean mIsSortAscending;
    private SortKey mSortKey;

    public enum SortKey {
        AGE,
        GENDER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsSortAscending = true;
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mAgeGenderRadioGroup = (RadioGroup) findViewById(R.id.ageGenderRadioGroup);
        mIsAscendingSortRadioGroup = (RadioGroup) findViewById(R.id.isAscendingRadioGroup);

        mDataset = new ArrayList<>();
        final PersonProvider personProvider = PersonProvider.getInstance();

//        mDataset.addAll(personProvider.getPersonsList(20));
        mRecyclerView = (RecyclerView) findViewById(R.id.persons_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerViewAdapter(mDataset);
        mAdapter.setHasStableIds(true);
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
                if (checkedId == R.id.ageRadioButton) {
                    mSortKey = mSortKey.AGE;
                } else {
                    mSortKey = mSortKey.GENDER;
                }
                sortAdapterDataAndUpdate();
            }
        });

        mIsAscendingSortRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.ascendingRadioButton) {
                    mIsSortAscending = true;
                } else {
                    mIsSortAscending = false;
                }
                sortAdapterDataAndUpdate();
            }
        });
    }

    private void sortAdapterDataAndUpdate() {
        mAdapter.dataset.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if (mSortKey == SortKey.GENDER) {
                    if (o1.getGender().equals(o2.getGender())) {
                        return mIsSortAscending ? o1.getName().compareTo(o2.getName()) :
                                o1.getName().compareTo(o2.getName()) * -1;
                    } else {
                        return mIsSortAscending ? o1.getGender().compareTo(o2.getGender()) :
                                o1.getGender().compareTo(o2.getGender()) * -1;
                    }
                } else {
                    if (o1.getAge() == o2.getAge()) {
                        return mIsSortAscending ? o1.getName().compareTo(o2.getName()) :
                                o1.getName().compareTo(o2.getName()) * -1;
                    } else {
                        return o1.getAge() > o2.getAge() ?
                                mIsSortAscending ? 1 : -1 : mIsSortAscending ? -1 : 1;
                    }
                }
            }
        });
//        mAdapter.notifyDataSetChanged();
    }


}
