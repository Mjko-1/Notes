package com.example.notes.app

import android.app.Application
import com.example.notes.conventions.Constant
import com.example.notes.model.retrofit.NoteApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp : Application() {

    private lateinit var retrofit: Retrofit

    val noteApi: NoteApi by lazy { retrofit.create(NoteApi::class.java) }

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}