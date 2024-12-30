package com.example.crud_mahasiswa_berita.model

import androidx.core.app.NotificationCompat.MessagingStyle.Message

data class RegisterResponse(
    val success : Boolean,
    val message: String,
    val values :Int
)
