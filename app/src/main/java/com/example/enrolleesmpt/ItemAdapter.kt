package com.example.enrolleesmpt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ItemAdapter (private val context : Context?,
                   private val specs: ArrayList<ItemOfList>,
                   val listener: (ItemOfList) -> Unit) : RecyclerView.Adapter<ItemAdapter.ImageViewHolder>() {
    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.imageSpec)
        val name = view.findViewById<TextView>(R.id.nameSpec)
        val qualification = view.findViewById<TextView>(R.id.qualSpec)

        fun bindView(spec : ItemOfList, listener: (ItemOfList) -> Unit) {
            name.text = spec.name
            qualification.text = spec.qualification
            itemView.setOnClickListener { listener(spec) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder = ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount(): Int = specs.size

    override fun onBindViewHolder(
        holder: ImageViewHolder,
        position: Int
    ) {
        var item = specs.get(position)
        if (item.urlToImage.trim().length == 0)Picasso.with(context).load("item.urlToImage").fit().centerInside().placeholder(R.drawable.ic_spec_placeholder).error(R.drawable.ic_spec_placeholder)
            .into(holder.image)
        else Picasso.with(context).load(item.urlToImage).fit().centerInside().placeholder(R.drawable.ic_spec_placeholder).error(R.drawable.ic_spec_placeholder)
            .into(holder.image)
        holder.bindView(specs[position], listener)
    }
}