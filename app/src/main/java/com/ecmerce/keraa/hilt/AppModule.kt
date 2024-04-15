package com.ecmerce.keraa.hilt

import android.app.Application
import android.content.Context
import com.ecmerce.keraa.firebase.FirebaseCommon
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseFirestoreDatabase() = com.google.firebase.ktx.Firebase.firestore

    @Provides
    fun provideIntroductionSp(application: Application) = application.getSharedPreferences(
        "IntroductionSP",
        Context.MODE_PRIVATE
    )

    @Provides
    @Singleton
    fun provideFirebaseCommon(firebaseFirestore: FirebaseFirestore, firebaseAuth: FirebaseAuth) =
        FirebaseCommon(firebaseFirestore, firebaseAuth)

    @Provides
    @Singleton
    fun provideStorage() = FirebaseStorage.getInstance().reference
}