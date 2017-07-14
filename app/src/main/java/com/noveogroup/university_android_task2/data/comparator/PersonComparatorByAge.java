package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.data.model.Person;

import java.util.Comparator;

class PersonComparatorByAge implements Comparator<Person> {
    private boolean isAscending;

    PersonComparatorByAge(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Person o1, Person o2) {
        if (o1.getAge() == o2.getAge()) {
            return o1.getName().compareTo(o2.getName());
        } else {
            return (isAscending ? 1 : -1) * Integer.compare(o1.getAge(), o2.getAge());
        }
    }
}
