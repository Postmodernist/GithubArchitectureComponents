package com.boisneyphilippe.githubarchitecturecomponents.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.boisneyphilippe.githubarchitecturecomponents.R;
import com.boisneyphilippe.githubarchitecturecomponents.fragments.UserProfileFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

  private static final String USER_LOGIN = "JakeWharton";

  @Inject
  DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    this.configureDagger();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    this.showFragment(savedInstanceState);
  }

  @Override
  public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
    return dispatchingAndroidInjector;
  }

  // ---

  private void showFragment(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      return;
    }

    UserProfileFragment fragment = new UserProfileFragment();

    Bundle bundle = new Bundle();
    bundle.putString(UserProfileFragment.UID_KEY, USER_LOGIN);
    fragment.setArguments(bundle);

    getSupportFragmentManager().beginTransaction()
        .add(R.id.fragment_container, fragment)
        .commit();
  }

  private void configureDagger() {
    AndroidInjection.inject(this);
  }
}
