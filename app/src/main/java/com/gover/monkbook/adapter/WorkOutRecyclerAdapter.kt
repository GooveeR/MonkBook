package com.gover.monkbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.gover.monkbook.R
import com.gover.monkbook.model.UserData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_row.view.*

class WorkOutRecyclerAdapter(val postList : ArrayList<UserData>) : RecyclerView.Adapter<WorkOutRecyclerAdapter.WorkOutHolder>() {

    class WorkOutHolder (itemView : View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkOutHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return WorkOutHolder(view)
    }

    override fun onBindViewHolder(holder: WorkOutHolder, position: Int) {


        holder.itemView.recycler_row_kullanici_email.text = postList[position].kullaniciEmail
        holder.itemView.recycler_row_kullanici_yorum.text = postList[position].kullaniciYorumu
        Picasso.get().load(postList[position].gorselUrl).into(holder.itemView.recycler_row_gorsel_url)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}