package com.example.p11_latihanstorage

import androidx.room.Entity
import androidx.room.PrimaryKey

// Menandai class ini sebagai Tabel SQLite dengan nama 'tabel_catatan'
@Entity(tableName = "tabel_catatan")
data class Catatan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val judul: String,
    val isi: String,
    val tanggal: String
)