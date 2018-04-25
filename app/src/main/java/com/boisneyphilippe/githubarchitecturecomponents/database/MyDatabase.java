package com.boisneyphilippe.githubarchitecturecomponents.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.boisneyphilippe.githubarchitecturecomponents.database.converter.DateConverter;
import com.boisneyphilippe.githubarchitecturecomponents.database.dao.UserDao;
import com.boisneyphilippe.githubarchitecturecomponents.database.entity.User;

@Database(entities = {User.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class MyDatabase extends RoomDatabase {
  // --- DAO ---
  public abstract UserDao userDao();
}
