package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.data.model.Person;

import java.util.Comparator;

class PersonComparartorByAgeReversed implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {

        if (o2.getAge() == o1.getAge()) {
            return o2.getName().compareTo(o1.getName());
        } else {
            return o2.getAge() > o1.getAge() ? 1 : -1;
        }
    }
}
