package com.noveogroup.university_android_task2.data.diffutil;

import android.support.v7.util.DiffUtil;

import com.noveogroup.university_android_task2.data.model.Person;

import java.util.List;

class DiffUtilCallback extends DiffUtil.Callback {

    private List<Person> oldData;
    private List<Person> newData;

    DiffUtilCallback(List<Person> oldData, List<Person> newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    public int getOldListSize() {
        return oldData.size();
    }

    public int getNewListSize() {
        return newData.size();
    }

    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newData.get(newItemPosition).equals(oldData.get(oldItemPosition));
    }

    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Person personOld = oldData.get(oldItemPosition);
        Person personNew = newData.get(newItemPosition);
        return personNew.getAge() == personOld.getAge() &&
                personNew.getGender().equals(personOld.getGender()) &&
                personNew.getName().equals(personOld.getName());
    }
}
