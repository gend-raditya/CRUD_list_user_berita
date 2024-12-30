package com.example.crud_mahasiswa_berita.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crud_mahasiswa_berita.DetailActivity
import com.example.crud_mahasiswa_berita.R
import com.example.crud_mahasiswa_berita.model.ModelBerita

class BeritaAdapter(
    val OnClik : (ModelBerita) -> Unit

) : ListAdapter<ModelBerita, BeritaAdapter.BeritaViewHolder>(BeritaCallBack) {
    class BeritaViewHolder (itemView: View, val onClick: (ModelBerita) -> Unit):
        RecyclerView.ViewHolder(itemView){

        private val imgBerita: ImageView = itemView.findViewById(R.id.imgBerita)
        private val judulBerita: TextView = itemView.findViewById(R.id.judulBerita)
        private val tglBerita: TextView = itemView.findViewById(R.id.tglBerita)
        val cardBerita: CardView = itemView.findViewById(R.id.cardBerita)


        // Cek berita yang saat ini
        private var currentBerita: ModelBerita? = null

        init {
            itemView.setOnClickListener() {
                currentBerita?.let {
                    onClick(it)
                }
            }
        }


        fun bind(berita: ModelBerita) {
            currentBerita = berita

            // Set ke Widget
            judulBerita.text = berita.judul
            tglBerita.text = berita.tgl_berita

            // Untuk menampilkan Gambar
            Glide.with(itemView).load("http://192.168.1.16/berita/image/" + berita.gambar_berita).centerCrop()
                .into(imgBerita)
        }
    }


    // implement produkAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item_berita,parent,false
        )
        return  BeritaViewHolder(view, OnClik)
    }

    override fun onBindViewHolder(holder: BeritaViewHolder, position: Int) {
        val berita =getItem(position)
        holder.bind(berita)

        holder.cardBerita.setOnClickListener() {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("judul", berita.judul)
            intent.putExtra("gambar_berita", berita.gambar_berita)
            intent.putExtra("tgl_berita", berita.tgl_berita)
            intent.putExtra("isi_berita", berita.isi_berita)
            holder.itemView.context.startActivity(intent)
        }
    }

}







//implement productCallBack
object BeritaCallBack: DiffUtil.ItemCallback<ModelBerita>() {
    override fun areItemsTheSame(oldItem: ModelBerita, newItem: ModelBerita): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ModelBerita, newItem: ModelBerita): Boolean {
        return oldItem.id == newItem.id
    }

}