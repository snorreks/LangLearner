package io.sks.langlearner.android.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class History (
    var nativeText: String,
    var selectedText: String,
    var resultText: String,
    @ServerTimestamp var createdAt: Date = Date(),
    @PrimaryKey var id: Long? = null
) : Parcelable



