package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.ui.activity.MainActivity;

import java.util.Comparator;

public class ComparatorController {
    public static Comparator getComparator(MainActivity.SortKey k, boolean isAscending) {
        if (k == MainActivity.SortKey.AGE) {
            return isAscending ? new PersonComparatorByAge() : new PersonComparatorByAgeReversed();
        } else {
            return isAscending ? new PersonComparatorByGender() : new PersonComparatorByGenderReversed();
        }
    }
}
