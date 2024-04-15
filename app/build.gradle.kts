plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    //  alias(libs.plugins.daggerHilts)
    id("androidx.navigation.safeargs.kotlin")
    //   kapt("com.google.dagger:hilt-android-compiler:2.51")
    id("com.google.gms.google-services")

    id("kotlin-kapt")

    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

android {
    namespace = "com.ecmerce.keraa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ecmerce.keraa"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Navigation and safe args
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //Dagger hilt
    //  implementation("com.google.dagger:hilt-android:2.51")
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)


    //Android Ktx
    implementation(libs.androidx.fragment.ktx)

    //smooth bar
    // implementation (libs.SmoothBottomBar)

    //Glide
    implementation(libs.glide)

    //circular image
    implementation(libs.circleimageview)

    //loading button
   // implementation(libs.loading.button.android)

    //Firebase coroutines
    implementation(libs.kotlinx.coroutines.play.services)

    implementation(libs.firebase.storage)
    implementation(libs.firebase.auth)

    //stepView
    implementation("com.github.shuhart:stepview:1.5.1")

    //loading button
    implementation("com.github.leandroborgesferreira:loading-button-android:2.3.0")

}