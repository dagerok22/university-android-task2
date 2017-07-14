package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.data.model.Person;

import java.util.Comparator;

class PersonComparatorByGender implements Comparator<Person> {
    private boolean isAscending;

    PersonComparatorByGender(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Person o1, Person o2) {
        if (o1.getGender() == o2.getGender()) {
            return o1.getName().compareTo(o2.getName());
        } else {
            return isAscending ? o1.getGender().compareTo(o2.getGender()):
                    o2.getGender().compareTo(o1.getGender());
        }
    }

}
