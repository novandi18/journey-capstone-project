package com.journey.bangkit.data.local.remotekey

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeyEntity(
    @PrimaryKey
    val id: Int = 1,
    val page: Int = 1
)