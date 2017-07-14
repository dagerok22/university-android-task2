package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.data.model.Person;

import java.util.Comparator;

class PersonComparatorByGenderReversed implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        if (o2.getGender().equals(o1.getGender())) {
            return o1.getName().compareTo(o2.getName());
        } else {
            return o2.getGender().compareTo(o1.getGender());
        }
    }
}
