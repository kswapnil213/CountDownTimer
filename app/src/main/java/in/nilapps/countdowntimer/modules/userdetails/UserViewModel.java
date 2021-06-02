package in.nilapps.countdowntimer.modules.userdetails;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Iterator;

import in.nilapps.countdowntimer.common.models.User;

/**
 * Created by Swapnil G. on 02-06-2021.
 */

public class UserViewModel extends ViewModel {

    MutableLiveData<ArrayList<User>> userLiveData;
    ArrayList<User> userList;
    long counter;

    public UserViewModel() {
        userLiveData = new MutableLiveData<>();
        init();
    }

    public MutableLiveData<ArrayList<User>> getUserMutableLiveData() {
        return userLiveData;
    }

    public long getCounter() {
        for (User user: userList) {
            if (user.getCount() > counter)
                counter = user.getCount();
        }
        return counter;
    }

    public void init(){
        populateList();
    }

    public void populateList(){
        userList = new ArrayList<>();
        userList.add(new User("Salman", "Khan", 56, "Male", 100));
        userList.add(new User("Aishwarya", "Rai", 40, "Female", 20));
        userList.add(new User("Dilip", "Kumar", 80, "Male", 80));
        userList.add(new User("Aliya", "Bhatt", 25, "Female", 20));
        userList.add(new User("Shahrukh", "Khan", 54, "Male", 40));
        userList.add(new User("Ajay", "Devgan", 49, "Male", 35));
    }

    public void updateList(final long countDown) {
        for (Iterator<User> iterator = userList.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            user.setCount(user.getRealCount() - countDown);
            if (user.getCount() < 0) {
                iterator.remove();
            }
        }
        userLiveData.setValue(userList);
    }

}