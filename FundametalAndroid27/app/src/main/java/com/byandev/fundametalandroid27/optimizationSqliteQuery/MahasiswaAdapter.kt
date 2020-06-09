package com.byandev.fundametalandroid27.optimizationSqliteQuery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.byandev.fundametalandroid27.R
import com.byandev.fundametalandroid27.optimizationSqliteQuery.model.MahasiswaModels
import kotlinx.android.synthetic.main.item_mahasiswa_row.view.*

class MahasiswaAdapter : RecyclerView.Adapter<MahasiswaAdapter.Holder>() {

    private val listMahasiswa = ArrayList<MahasiswaModels>()

    fun setData(listMahasiswa: ArrayList<MahasiswaModels>) {
        if (listMahasiswa.size > 0) {
            this.listMahasiswa.clear()
        }
        this.listMahasiswa.addAll(listMahasiswa)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MahasiswaAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mahasiswa_row, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: MahasiswaAdapter.Holder, position: Int) {
        holder.bind(listMahasiswa[position])
    }

    override fun getItemCount(): Int = listMahasiswa.size

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mahasiswa: MahasiswaModels) {
            with(itemView){
                txt_nim.text = mahasiswa.nim
                txt_name.text = mahasiswa.name
            }
        }
    }

}