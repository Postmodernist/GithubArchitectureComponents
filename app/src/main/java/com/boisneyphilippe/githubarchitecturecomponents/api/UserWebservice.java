package com.boisneyphilippe.githubarchitecturecomponents.api;

import com.boisneyphilippe.githubarchitecturecomponents.database.entity.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserWebservice {
  @GET("/users/{user}")
  Call<User> getUser(@Path("user") String userId);
}
