package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.MainActivity;

import java.util.Comparator;

public class ComparatorController {
    public static Comparator getComparator(MainActivity.SortKey k, boolean isAscending) {
        if (k == MainActivity.SortKey.AGE) {
            return isAscending ? new PersonComparatorByAge() : new PersonComparartorByAgeReversed();
        } else {
            return isAscending ? new PersonComparatorByGender() : new PersonComparatorByGenderReversed();
        }
    }
}
