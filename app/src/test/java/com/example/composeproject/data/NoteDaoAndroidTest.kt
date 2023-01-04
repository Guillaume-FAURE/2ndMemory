package com.example.composeproject.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry

class NoteDaoAndroidTest {

    private lateinit var systemUnderTest: ArtDAO
    private var memoryDatabase = Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().context,
        AppDatabase::class.java
    ).allowMainThreadQueries().build()
}