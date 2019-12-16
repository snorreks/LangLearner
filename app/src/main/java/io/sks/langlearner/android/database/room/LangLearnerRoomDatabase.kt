package io.sks.langlearner.android.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.sks.langlearner.android.model.History

@Database(entities = [History::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LangLearnerRoomDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryRoomDao

    companion object {
        private const val DATABASE_NAME = "LANGLEARNER_DATABASE"

        @Volatile
        private var INSTANCE: LangLearnerRoomDatabase? = null

        fun getDatabase(context: Context): LangLearnerRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(LangLearnerRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            LangLearnerRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}