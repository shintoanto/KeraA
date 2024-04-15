buildscript {
    dependencies {
        classpath(libs.google.services)

        //  classpath("com.google.dagger:hilt-android-gradle-plugin:2.45")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    //alias(libs.plugins.daggerHilts) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.5" apply false
    id("dagger.hilt.android.plugin") apply false
}