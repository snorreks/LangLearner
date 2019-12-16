package io.sks.langlearner.android.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class Achievement (
    @PrimaryKey var id: String= "",
    var title: String= "",
    var description: String="",
    var thumbnailUrl: String="",
    var currentCount: Int = 0,
    var goalCount: Int = 0,
    var category: String = "",
    @ServerTimestamp var achievedAt: Date? = null
) : Parcelable