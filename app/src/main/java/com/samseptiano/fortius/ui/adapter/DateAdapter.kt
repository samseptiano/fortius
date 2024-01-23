package com.samseptiano.fortius.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samseptiano.fortius.data.model.response.AbsensionModel
import com.samseptiano.fortius.data.model.response.DateModel
import com.samseptiano.fortius.databinding.CurrentDateItemBinding
import com.samseptiano.fortius.databinding.OtherDateItemBinding

/**
 * Created by samuel.septiano on 22/01/2024.
 */
class DateAdapter(
    private val context: Context,
    itemList: List<DateModel>,
    onHeaderClick: (DateModel, Int) -> Unit,
    onClick: (DateModel, Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mItemList: ArrayList<DateModel> = itemList as ArrayList<DateModel>
    var mOnHeaderClick = onHeaderClick
    var mOnClick = onClick

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = CurrentDateItemBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }

            VIEW_TYPE_ITEM -> {
                val binding = OtherDateItemBinding.inflate(inflater, parent, false)
                ItemViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < mItemList.size) {
            val item = mItemList[position]

            when (holder.itemViewType) {
                VIEW_TYPE_HEADER -> {
                    val headerHolder = holder as HeaderViewHolder
                    headerHolder.bind(item, mOnHeaderClick, position)
                }

                VIEW_TYPE_ITEM -> {
                    val itemHolder = holder as ItemViewHolder
                    itemHolder.bind(item, mOnClick, position)
                }

                else -> throw IllegalArgumentException("Invalid view type")
            }
        }
    }

    fun clearData() {
        synchronized(this) {
            val oldSize = mItemList.size
            mItemList.clear()
            notifyItemRangeRemoved(0, oldSize)
        }
    }

    fun setData(list: List<DateModel>) {
        synchronized(this) {
            mItemList.addAll(list)
            notifyItemRangeInserted(0, mItemList.size)
        }
    }

    override fun getItemCount(): Int = mItemList.size

    override fun getItemViewType(position: Int): Int {
        return if (mItemList[position].isHeader) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }
}

class HeaderViewHolder(binding: CurrentDateItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val bindingData = binding
    fun bind(date: DateModel, onHeaderClick: (DateModel, Int) -> Unit, pos: Int) {
        bindingData.tvDateNumber.text = date.dateNumber.toString()
        bindingData.tvDateDay.text = date.dateDay
        bindingData.tvMonthYear.text = "${date.dateMonthString} ${date.dateYear}"
        bindingData.clDate.setOnClickListener {
            onHeaderClick.invoke(date, pos)
        }
    }
}

class ItemViewHolder(binding: OtherDateItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val bindingData = binding
    fun bind(date: DateModel, onClick: (DateModel, Int) -> Unit, pos: Int) {
        bindingData.tvDateNumber.text = date.dateNumber.toString()
        bindingData.tvDateDay.text = date.dateDay[0].toString()
        bindingData.clDate.setOnClickListener {
            onClick.invoke(date, pos)
        }
    }
}