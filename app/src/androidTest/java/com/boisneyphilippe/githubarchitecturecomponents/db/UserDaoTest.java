package com.boisneyphilippe.githubarchitecturecomponents.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.boisneyphilippe.githubarchitecturecomponents.database.MyDatabase;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.User;
import com.boisneyphilippe.githubarchitecturecomponents.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

  private MyDatabase db;

  @Before
  public void initDb() {
    db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), MyDatabase.class).build();
  }

  @After
  public void closeDb() {
    db.close();
  }

  @Test
  public void insertAndLoad() throws InterruptedException {
    final User user = new User(
        "66577",
        "JakeWharton",
        "https://avatars0.githubusercontent.com/u/66577?v=4",
        "Jake Wharton",
        "Google, Inc.",
        "http://jakewharton.com",
        new Date());
    db.userDao().save(user);

    final LiveData<User> userLiveData = db.userDao().load(user.getLogin());
    final User loadedUser = LiveDataTestUtil.getValue(userLiveData);
    assertThat(loadedUser.getLogin(), is("JakeWharton"));
  }
}
