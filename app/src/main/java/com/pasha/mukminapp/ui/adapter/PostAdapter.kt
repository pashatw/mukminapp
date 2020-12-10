package com.pasha.mukminapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pasha.mukminapp.R
import com.pasha.mukminapp.data.local.database.post.PostEntity
import com.pasha.mukminapp.ui.activity.DetailActivity
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var vendorList = mutableListOf<PostEntity>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(
            onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClick(data: PostEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VendorListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
    }
    override fun getItemCount(): Int = vendorList.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = viewHolder as VendorListViewHolder
        holder.bindView(vendorList[position], position)
        holder.itemView.setOnClickListener {
            onItemClickCallback
                    .onItemClick(vendorList[position])
        }
    }

    fun setList(listOfVendor: List<PostEntity>) {
        this.vendorList = listOfVendor.toMutableList()
        notifyDataSetChanged()
    }

    fun addData(data: PostEntity) {
        this.vendorList.add(data)
        notifyDataSetChanged()
    }

    fun clear() {
        this.vendorList.clear()
        notifyDataSetChanged()
    }

    class VendorListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bindView(vendorModel: PostEntity, position: Int) {
            itemView.tvTitle.text = vendorModel.title.ifEmpty { "-" }
            itemView.tvCategory.text = vendorModel.category.ifEmpty { "-" }
            Glide.with(itemView).load(vendorModel.thumnail).into(itemView.imgPost)

            val color: Int
            when (position%4) {
                0 -> color = ContextCompat.getColor(itemView.context, R.color.bg_post_item_default)
                1 -> color = ContextCompat.getColor(itemView.context, R.color.light_peach)
                2 -> color = ContextCompat.getColor(itemView.context, R.color.light_mint)
                3 -> color = ContextCompat.getColor(itemView.context, R.color.light_peach3)
                else -> {
                    color = ContextCompat.getColor(itemView.context, R.color.bg_post_item_default)
                }
            }
            itemView.bgPost.setCardBackgroundColor(color)
        }
    }
}