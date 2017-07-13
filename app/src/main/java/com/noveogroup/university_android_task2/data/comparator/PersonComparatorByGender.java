package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.data.model.Person;

import java.util.Comparator;

public class PersonComparatorByGender implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        if (o1.getGender() == o2.getGender()) {
            return o1.getName().compareTo(o2.getName());
        } else {
            return o1.getGender().compareTo(o2.getGender());
        }
    }

}
