package com.example.crud_mahasiswa_berita.model

data class ModelBerita( val id : Int,
                        val judul : String,
                        val isi_berita : String,
                        val gambar_berita : String,
                        val tgl_berita : String,
                        val tgl_daftar : String,
                        val thumbnail : String)
