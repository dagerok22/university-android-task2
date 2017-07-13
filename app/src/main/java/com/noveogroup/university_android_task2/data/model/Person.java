package com.noveogroup.university_android_task2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private final String name;
    private final int age;
    private final Gender gender;

    public Person(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    private Person(Parcel source) {
        String []data = new String[3];
        source.readStringArray(data);
        this.name = String.valueOf(data[0]);
        this.age = Integer.valueOf(data[1]);
        this.gender = data[2].equals("MALE") ? Gender.MALE  : Gender.FEMALE;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Person name: " + name + ", age: " + String.valueOf(age) + ", gender is: " + gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                name, String.valueOf(age),
                gender == Gender.MALE ? "MALE" : "FEMALE"});
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
