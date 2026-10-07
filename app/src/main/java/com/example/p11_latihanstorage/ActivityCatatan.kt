package com.example.p11_latihanstorage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class ActivityCatatan : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var tvDaftarCatatan: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catatan)
// 1. Inisialisasi Database
        database = AppDatabase.getDatabase(this)
        val etJudul = findViewById<EditText>(R.id.etJudul)
        val etIsi = findViewById<EditText>(R.id.etIsi)
        val etTanggal = findViewById<EditText>(R.id.etTanggal)
        val btnSimpan = findViewById<Button>(R.id.btnSimpanCatatan)
        val btnHapusSemua = findViewById<Button>(R.id.btnHapusSemua)
        tvDaftarCatatan = findViewById(R.id.tvDaftarCatatan)
// 2. Load catatan awal saat layar dibuka
        muatDataCatatan()
// 3. Tombol Simpan
        btnSimpan.setOnClickListener {
            val judul = etJudul.text.toString().trim()
            val isi = etIsi.text.toString().trim()
            val tanggal = etTanggal.text.toString().trim()

            if (judul.isNotEmpty() && isi.isNotEmpty() && tanggal.isNotEmpty()) {
                val dataBaru = Catatan(
                    judul = judul,
                    isi = isi,
                    tanggal = tanggal
                )
// Eksekusi operasi database di Background Thread menggunakan Coroutine
                lifecycleScope.launch(Dispatchers.IO) {
                    database.catatanDao().insertCatatan(dataBaru)
// Kembali ke Main Thread untuk update tampilan
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ActivityCatatan, "Catatan tersimpan di Room DB!",
                            Toast.LENGTH_SHORT).show()
                        etJudul.text.clear()
                        etIsi.text.clear()
                        muatDataCatatan()
                    }
                }
            } else {
                Toast.makeText(this, "Judul dan isi tidak boleh kosong",
                    Toast.LENGTH_SHORT).show()
            }
        }
// 4. Tombol Kosongkan Database
        btnHapusSemua.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                database.catatanDao().deleteAll()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ActivityCatatan, "Seluruh catatan dihapus!",
                        Toast.LENGTH_SHORT).show()
                    muatDataCatatan()
                }
            }
        }
    }
    private fun muatDataCatatan() {
        lifecycleScope.launch(Dispatchers.IO) {
            val listCatatan = database.catatanDao().getAllCatatan()
            withContext(Dispatchers.Main) {
                if (listCatatan.isEmpty()) {
                    tvDaftarCatatan.text = "Belum ada catatan yang tersimpan."
                }

                else {
                    val sb = StringBuilder()
                    for (item in listCatatan) {
                        sb.append("📌 ID #${item.id}: ${item.judul}\n")
                        sb.append("${item.isi}\n")
                        sb.append("Tanggal: ${item.tanggal}\n\n")
                    }
                    tvDaftarCatatan.text = sb.toString()
                }
            }
        }
    }
}