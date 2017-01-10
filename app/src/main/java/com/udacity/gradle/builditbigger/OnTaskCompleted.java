package com.udacity.gradle.builditbigger;

// Interface to add a callback for EndpointsAsyncTask
public interface OnTaskCompleted {
    void onTaskCompleted(String result);
}