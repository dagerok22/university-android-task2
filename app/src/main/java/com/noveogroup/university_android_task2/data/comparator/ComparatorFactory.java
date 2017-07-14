package com.noveogroup.university_android_task2.data.comparator;

import com.noveogroup.university_android_task2.data.model.SortKey;

import java.util.Comparator;

public class ComparatorFactory {
    public static Comparator getComparator(SortKey sortKey, boolean isAscending) {
        if (sortKey == SortKey.AGE) {
            return new PersonComparatorByAge(isAscending);
        } else {
            return new PersonComparatorByGender(isAscending);
        }
    }
}
