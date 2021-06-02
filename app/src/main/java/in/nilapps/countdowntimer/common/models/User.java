package in.nilapps.countdowntimer.common.models;

/**
 * Created by Swapnil G. on 02-06-2021.
 */

public class User {

    String firstName;
    String lastName;
    int age;
    String gender;
    long count;
    long realCount;

    public User(String firstName, String lastName, int age, String gender, long count) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.count = count;
        this.realCount = count;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getRealCount() {
        return realCount;
    }
}

