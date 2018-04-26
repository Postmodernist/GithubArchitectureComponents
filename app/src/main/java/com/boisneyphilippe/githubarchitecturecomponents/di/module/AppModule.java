package com.boisneyphilippe.githubarchitecturecomponents.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.boisneyphilippe.githubarchitecturecomponents.api.UserWebservice;
import com.boisneyphilippe.githubarchitecturecomponents.database.MyDatabase;
import com.boisneyphilippe.githubarchitecturecomponents.database.dao.UserDao;
import com.boisneyphilippe.githubarchitecturecomponents.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

  // --- DATABASE INJECTION ---

  private static final String BASE_URL = "https://api.github.com/";

  @Provides
  @Singleton
  MyDatabase provideDatabase(Application application) {
    return Room.databaseBuilder(application, MyDatabase.class, "MyDatabase.db").build();
  }

  // --- REPOSITORY INJECTION ---

  @Provides
  @Singleton
  UserDao provideUserDao(MyDatabase database) {
    return database.userDao();
  }

  @Provides
  Executor provideExecutor() {
    return Executors.newSingleThreadExecutor();
  }

  // --- NETWORK INJECTION ---

  @Provides
  @Singleton
  Context provideApplicationContext(Application application) {
    return application.getApplicationContext();
  }

  @Provides
  @Singleton
  UserRepository provideUserRepository(UserWebservice webservice, UserDao userDao, Executor executor, Context context) {
    return new UserRepository(webservice, userDao, executor, context);
  }

  @Provides
  Gson provideGson() {
    return new GsonBuilder().create();
  }

  @Provides
  Retrofit provideRetrofit(Gson gson) {
    return new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .build();
  }

  @Provides
  @Singleton
  UserWebservice provideApiWebservice(Retrofit restAdapter) {
    return restAdapter.create(UserWebservice.class);
  }
}
