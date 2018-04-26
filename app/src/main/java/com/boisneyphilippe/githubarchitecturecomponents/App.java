package com.boisneyphilippe.githubarchitecturecomponents;

import android.app.Activity;
import android.app.Application;

import com.boisneyphilippe.githubarchitecturecomponents.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

  @Inject
  DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

  @Override
  public void onCreate() {
    super.onCreate();
    this.initDagger();
  }

  @Override
  public DispatchingAndroidInjector<Activity> activityInjector() {
    return dispatchingAndroidInjector;
  }

  // ---

  private void initDagger() {
    DaggerAppComponent.builder().application(this).build().inject(this);
  }
}
