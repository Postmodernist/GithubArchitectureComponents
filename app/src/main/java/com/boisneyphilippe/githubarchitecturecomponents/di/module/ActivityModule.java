package com.boisneyphilippe.githubarchitecturecomponents.di.module;

import com.boisneyphilippe.githubarchitecturecomponents.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
  @ContributesAndroidInjector(modules = FragmentModule.class)
  abstract MainActivity contributeMainActivity();
}
