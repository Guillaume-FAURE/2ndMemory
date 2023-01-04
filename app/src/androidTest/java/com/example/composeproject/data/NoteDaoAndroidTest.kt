package com.example.composeproject.data

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.composeproject.model.ArtEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ArtDaoAndroidTest {

    private lateinit var systemUnderTest: ArtDAO
    private lateinit var memoryDatabase: AppDatabase

    @Before
    fun createDataBase(){
        // val context = ApplicationProvider.getApplicationContext<Context>()
        memoryDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        systemUnderTest = memoryDatabase.artDao()
    }

    @After
    fun cleanUp(){
        memoryDatabase.close()
    }

    @Test
    fun testInsertArtAndRead() = runBlocking{
        // Arrange
        val fakeTitle = "A random title"
        val fakeArt = ArtEntity(
            author = "Unknown",
            title = fakeTitle,
            description = "Some description",
            mark = "?/10",
            type = "Book",
            state = "To Do"
        )

        // Act
        systemUnderTest.insert(fakeArt)
        val artList = systemUnderTest.getAll().first()

        // Assert
        assertThat(artList.first().title).isEqualTo(fakeTitle)
    }

    @Test
    fun testUpdateArtAndRead() = runBlocking{
        // Arrange
        val fakeTitle = "A random title"
        val updateTitle = "An updated title"
        val fakeArt = ArtEntity(
            author = "Unknown",
            title = fakeTitle,
            description = "Some description",
            mark = "?/10",
            type = "Book",
            state = "To Do"
        )

        // Act
        systemUnderTest.insert(fakeArt)
        val artList = systemUnderTest.getAll().first()
        systemUnderTest.update(artList.first().copy(title = updateTitle))
        val artListUpdated = systemUnderTest.getAll().first()

        // Assert
        assertThat(artList.first().title).isEqualTo(fakeTitle)
        assertThat(artListUpdated.first().title).isEqualTo(updateTitle)
    }

    @Test
    fun testDeleteArtAndRead() = runBlocking{
        // Arrange
        val fakeTitle = "A random title"
        val fakeArt = ArtEntity(
            author = "Unknown",
            title = fakeTitle,
            description = "Some description",
            mark = "?/10",
            type = "Book",
            state = "To Do"
        )

        // Act
        systemUnderTest.insert(fakeArt)
        val artList = systemUnderTest.getAll().first()
        systemUnderTest.delete(artList.first())
        val artListUpdated = systemUnderTest.getAll().first()

        // Assert
        assertThat(artListUpdated).isEmpty()
    }


}