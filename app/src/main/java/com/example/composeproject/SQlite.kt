package com.example.composeproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.composeproject.model.ArtEntity

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val FAIL_STATUS = -1

    companion object {
        private const val DATABASE_VERSION = 3
        private const val DATABASE_NAME = "2ndMemory.db"
        private const val TBL_ART = "table_art"
        private const val ID_COL = "id"
        private const val TITLE_COL = "title"
        private const val AUTHOR_COL = "author"
        private const val DESCRIPTION_COL = "description"
        private const val MARK_COL = "mark"
        private const val TYPE_COL = "type"
        private const val STATE_COL = "mark"
        private const val CREATED_DATE_COL = "created_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblAudio =
            ("CREATE TABLE $TBL_ART (" +
                    "$ID_COL TEXT PRIMARY KEY, " +
                    "$TITLE_COL TEXT, " +
                    "$AUTHOR_COL TEXT, " +
                    "$DESCRIPTION_COL TEXT, " +
                    "$MARK_COL TEXT, " +
                    "$TYPE_COL TEXT, " +
                    "$STATE_COL TEXT, " +
                    "$CREATED_DATE_COL TEXT)")

        db?.execSQL(createTblAudio)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_ART")
        onCreate(db)
    }

    fun addArt(art: ArtEntity): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID_COL, art.artId)
        contentValues.put(TITLE_COL, art.title)
        contentValues.put(AUTHOR_COL, art.author)
        contentValues.put(DESCRIPTION_COL, art.description)
        contentValues.put(MARK_COL, art.mark)
        contentValues.put(TYPE_COL, art.type)
        contentValues.put(STATE_COL, art.state)
        contentValues.put(CREATED_DATE_COL, art.createdDate)

        val success = db.insert(TBL_ART, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllAudio(): ArrayList<ArtEntity> {
        val result = ArrayList<ArtEntity>()
        val selectQuery = "SELECT * FROM $TBL_ART ORDER BY $CREATED_DATE_COL DESC"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val id: Int = cursor.getInt(cursor.getColumnIndex(ID_COL))
                    val title: String = cursor.getString(cursor.getColumnIndex(TITLE_COL))
                    val author: String = cursor.getString(cursor.getColumnIndex(AUTHOR_COL))
                    val description: String = cursor.getString(cursor.getColumnIndex(DESCRIPTION_COL))
                    val mark: String = cursor.getString(cursor.getColumnIndex(MARK_COL))
                    val type: String = cursor.getString(cursor.getColumnIndex(TYPE_COL))
                    val state: String = cursor.getString(cursor.getColumnIndex(STATE_COL))


                    result.add(
                        ArtEntity(
                            artId = id,
                            title = title,
                            author = author,
                            description = description,
                            mark = mark,
                            type = type,
                            state = state,
                        )
                    )

                } while (cursor.moveToNext())
            }
            cursor.close()

        } catch (e: Exception) {
            Log.println(Log.ERROR, "sql get all students", "An error happened")
            e.printStackTrace()
        }
        return result
    }

//    fun getAudiosByCountry(countryName: String): ArrayList<AudioModel> {
//        val result = ArrayList<AudioModel>()
//        val selectQuery = "SELECT * FROM $TBL_AUDIO WHERE $COUNTRY_COL = '$countryName' ORDER BY $CREATED_DATE_COL DESC"
//        val db = this.readableDatabase
//        val cursor: Cursor?
//
//
//        try {
//            cursor = db.rawQuery(selectQuery, null)
//
//            if (cursor.moveToFirst()) {
//                do {
//                    val id: String = cursor.getString(cursor.getColumnIndex(ID_COL))
//                    val sentence: String = cursor.getString(cursor.getColumnIndex(SENTENCE_COL))
//                    val translation: String =
//                        cursor.getString(cursor.getColumnIndex(TRANSLATION_COL))
//                    val country: String = cursor.getString(cursor.getColumnIndex(COUNTRY_COL))
//                    result.add(
//                        AudioModel(
//                            id = id,
//                            sentence = sentence,
//                            translation = translation,
//                            country = country
//                        )
//                    )
//
//                } while (cursor.moveToNext())
//            }
//            cursor.close()
//
//        } catch (e: Exception) {
//            Log.println(Log.ERROR, "sql get all audios by country name", "An error happened")
//            e.printStackTrace()
//        }
//
//        return result
//    }

    fun deleteAudio(art: ArtEntity): Int {
        val db = this.writableDatabase

        val success = db.delete(TBL_ART, "id='${art.artId}'", null)
        db.close()

        return success
    }
}