package com.example.crud_mahasiswa_berita.screen

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crud_mahasiswa_berita.DetailUserActivity
import com.example.crud_mahasiswa_berita.MainActivity
import com.example.crud_mahasiswa_berita.R
import com.example.crud_mahasiswa_berita.model.RegisterResponse
import com.example.crud_mahasiswa_berita.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahDataUserScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_data_user_screen)

        //deklarasi widget
        val etUsername : EditText = findViewById(R.id.etUsername)
        val etfullname : EditText = findViewById(R.id.etFullName)
        val etEmail : EditText = findViewById(R.id.etEmail)
        val etpassword : EditText = findViewById(R.id.etPassword)
        val btnRegister : Button = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener(){
            //proses get data dari widget
            val nUsername = etUsername.text.toString()
            val nFullname = etfullname.text.toString()
            val nPassword = etpassword.text.toString()
            val nEmail = etEmail.text.toString()

            //proses untuk data ke database
            //registerRequest = RegisterRequest(nUsername,nFullname,nEmail,nPassword)
            try{
                ApiClient.retrofit.register(nUsername,nFullname,nEmail,nPassword).enqueue(
                    object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if (response.isSuccessful){
                                Toast.makeText(this@TambahDataUserScreenActivity, response.body()?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Navigasi ke DetailUserActivity
                                val toDetail = Intent(this@TambahDataUserScreenActivity, DetailUserActivity::class.java)
                                toDetail.putExtra("username", nUsername)
                                toDetail.putExtra("fullname", nFullname)
                                toDetail.putExtra("email", nEmail)
                                toDetail.putExtra("tanggalDaftar", "2023-12-23") // Bisa diganti sesuai kebutuhan
                                startActivity(toDetail)

                                finish()
//
//                                val toMain = Intent(this@TambahDataUserScreenActivity, MainActivity ::class.java)
//                                startActivity(toMain)
//                                finish()
                            }else{
                                val errorMessage = response.errorBody()?.string()?: "Unknow Error"
                                Log.e("Register Error", errorMessage)
                                Toast.makeText(this@TambahDataUserScreenActivity, "Register Failur",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }


                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(this@TambahDataUserScreenActivity, t.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                )
            }catch (e: Exception){
                Toast.makeText(this@TambahDataUserScreenActivity, "Error Occured : ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG,"Error Occured : ${e.message}",e)
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
            }
    }
}