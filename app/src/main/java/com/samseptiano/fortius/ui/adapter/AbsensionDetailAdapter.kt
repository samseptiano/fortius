package com.samseptiano.fortius.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samseptiano.fortius.data.model.response.AbsensionModel
import com.samseptiano.fortius.databinding.AbsensionPhotoItemListBinding


class AbsensionDetailAdapter(
    ctx:Context,
    dataList: ArrayList<AbsensionModel>,
    private val onClick:(AbsensionModel) -> Unit
) : RecyclerView.Adapter<AbsensionDetailAdapter.ExampleViewHolder>() {
    private var dataListFull = ArrayList<AbsensionModel>()
    private var context:Context
    private var mOnClick: ((AbsensionModel) -> Unit)? = null
    init {
        dataListFull = dataList
        context = ctx
        mOnClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        return ExampleViewHolder(
            AbsensionPhotoItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = dataListFull.size

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        val currentItem = dataListFull[position]
        holder.binding.apply {
            Glide.with(context).load(currentItem.photoPath).into(ivAbsensionPhoto)
        }
    }



    fun clearData() {
        val oldSize = dataListFull.size
        dataListFull.clear()
        notifyItemRangeRemoved(0, oldSize)
    }

    fun setData(list: List<AbsensionModel>) {
        dataListFull.addAll(list)
        notifyItemRangeInserted(0, dataListFull.size)
    }

    inner class ExampleViewHolder(val binding: AbsensionPhotoItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {}
    }

}