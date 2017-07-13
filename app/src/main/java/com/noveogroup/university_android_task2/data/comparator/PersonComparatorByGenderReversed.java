package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.data.model.Person;

import java.util.Comparator;

/**
 * Created by admin on 13.07.2017.
 */

public class PersonComparatorByGenderReversed implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        if (o2.getGender().equals(o1.getGender())) {
            return o2.getName().compareTo(o1.getName());
        } else {
            return o2.getGender().compareTo(o1.getGender());
        }
    }
}
