package com.maciel.murillo.muney.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.maciel.murillo.muney.model.repository.Repository
import com.maciel.murillo.muney.model.repository.RepositoryImpl
import com.maciel.murillo.muney.model.service.AuthService
import com.maciel.murillo.muney.model.service.AuthServiceImpl
import com.maciel.murillo.muney.model.service.DbService
import com.maciel.murillo.muney.model.service.DbServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseDatabaseReference(): DatabaseReference = FirebaseDatabase.getInstance().reference

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun provideAuthService(auth: FirebaseAuth): AuthService = AuthServiceImpl(auth)

    @Singleton
    @Provides
    fun provideDbService(dbRef: DatabaseReference, auth: AuthService): DbService = DbServiceImpl(dbRef, auth)

    @Singleton
    @Provides
    fun provideRepository(dbService: DbService, auth: AuthService): Repository = RepositoryImpl(auth, dbService)
}