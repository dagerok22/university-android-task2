package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.data.model.SortKey;

import java.util.Comparator;

public class ComparatorController {
    public static Comparator getComparator(SortKey k, boolean isAscending) {
        if (k == SortKey.AGE) {
            return new PersonComparatorByAge(isAscending);
        } else {
            return new PersonComparatorByGender(isAscending);
        }
    }
}
