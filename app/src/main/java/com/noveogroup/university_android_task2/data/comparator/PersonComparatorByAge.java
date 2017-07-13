package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.data.model.Person;

import java.util.Comparator;

/**
 * Created by admin on 13.07.2017.
 */

class PersonComparatorByAge implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        if (o1.getAge() == o2.getAge()) {
            return o1.getName().compareTo(o2.getName());
        } else {
            return o1.getAge() > o2.getAge() ? 1 : -1;
        }
    }
}
