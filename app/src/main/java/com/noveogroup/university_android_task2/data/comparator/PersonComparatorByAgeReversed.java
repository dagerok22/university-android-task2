package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.data.model.Person;

import java.util.Comparator;

class PersonComparatorByAgeReversed implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        if (o2.getAge() == o1.getAge()) {
            return o1.getName().compareTo(o2.getName());
        } else {
            return Integer.compare(o2.getAge(), o1.getAge());
        }
    }
}
