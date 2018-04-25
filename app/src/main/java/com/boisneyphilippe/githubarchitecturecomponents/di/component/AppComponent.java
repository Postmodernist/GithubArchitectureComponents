package com.boisneyphilippe.githubarchitecturecomponents.di.component;

import android.app.Application;

import com.boisneyphilippe.githubarchitecturecomponents.App;
import com.boisneyphilippe.githubarchitecturecomponents.di.module.ActivityModule;
import com.boisneyphilippe.githubarchitecturecomponents.di.module.AppModule;
import com.boisneyphilippe.githubarchitecturecomponents.di.module.FragmentModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {ActivityModule.class, FragmentModule.class, AppModule.class})
public interface AppComponent {

  void inject(App app);

  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder application(Application application);

    AppComponent build();
  }
}
