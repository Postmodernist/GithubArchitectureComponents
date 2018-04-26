package com.boisneyphilippe.githubarchitecturecomponents.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.boisneyphilippe.githubarchitecturecomponents.api.UserWebservice;
import com.boisneyphilippe.githubarchitecturecomponents.database.dao.UserDao;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.User;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class UserRepository {

  private static final int FRESH_TIMEOUT_IN_MINUTES = 1;

  private final UserWebservice webservice;
  private final UserDao userDao;
  private final Executor executor;
  private final Context context;

  @Inject
  public UserRepository(UserWebservice webservice, UserDao userDao, Executor executor, Context context) {
    this.webservice = webservice;
    this.userDao = userDao;
    this.executor = executor;
    this.context = context;
  }

  // ---

  public LiveData<User> getUser(String userLogin) {
    refreshUser(userLogin);  // try to refresh data if possible from Github Api
    return userDao.load(userLogin);  // return a LiveData directly from the database.
  }

  // ---

  private void refreshUser(final String userLogin) {
    executor.execute(() -> {
      // Check if user was fetched recently
      boolean userExists = (userDao.hasUser(userLogin, getMaxRefreshTime(new Date())) != null);
      // If user have to be updated
      if (!userExists) {
        webservice.getUser(userLogin).enqueue(new Callback<User>() {
          @Override
          public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
            Log.e("TAG", "DATA REFRESHED FROM NETWORK");
            Toast.makeText(context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
            executor.execute(() -> {
              User user = response.body();
              if (user != null) {
                user.setLastRefresh(new Date());
                userDao.save(user);
              }
            });
          }

          @Override
          public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
          }
        });
      }
    });
  }

  // ---

  private Date getMaxRefreshTime(Date currentDate) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(currentDate);
    cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES);
    return cal.getTime();
  }
}
