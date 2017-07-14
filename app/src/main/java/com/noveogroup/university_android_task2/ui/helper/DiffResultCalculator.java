package com.noveogroup.university_android_task2.ui.helper;

import android.support.v7.util.DiffUtil;

import com.noveogroup.university_android_task2.data.model.Person;

import java.util.List;

public class DiffResultCalculator {

    private DiffUtil.DiffResult diffResult;

    public void calculate(List<Person> oldItems, List<Person> newItems, Callback callback){
        diffResult = DiffUtil.calculateDiff(new DiffUtilCallback(oldItems, newItems));
        callback.showResult(diffResult);
    }

    public interface Callback{
        void showResult(DiffUtil.DiffResult result);
    }
}
