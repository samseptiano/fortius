package com.samseptiano.fortius.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samseptiano.fortius.R
import com.samseptiano.fortius.data.model.response.AbsensionModel
import com.samseptiano.fortius.databinding.AbsensionItemListBinding
import com.samseptiano.fortius.utils.Constant.Companion.STATUS_CHECK_IN
import com.samseptiano.fortius.utils.Constant.Companion.STATUS_CHECK_OUT
import com.samseptiano.fortius.utils.Constant.Companion.WORK_END_TIME
import com.samseptiano.fortius.utils.Constant.Companion.WORK_START_TIME
import com.samseptiano.fortius.utils.DateUtil


class AbsensionListAdapter(
    ctx:Context,
    dataList: ArrayList<Pair<AbsensionModel, AbsensionModel>>,
    onClick:(Pair<AbsensionModel, AbsensionModel>, String) -> Unit
) : RecyclerView.Adapter<AbsensionListAdapter.ExampleViewHolder>() {
    private var dataListFull = ArrayList<Pair<AbsensionModel, AbsensionModel>>()
    private var context:Context
    private var mOnClick: ((Pair<AbsensionModel, AbsensionModel>, String) -> Unit)? = null
    init {
        dataListFull = dataList
        context = ctx
        mOnClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        return ExampleViewHolder(
            AbsensionItemListBinding.inflate(
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
            tvEmployeeName.text = currentItem.first.name
            tvEmployeeId.text = currentItem.first.companyName
            tvCheckinLocation.text = currentItem.first.location
            tvCheckoutLocation.text =  currentItem.second.location
            tvCheckinTime.text = currentItem.first.time
            tvCheckoutTime.text = currentItem.second.time
            if(currentItem.first.time != "" && currentItem.second.time != "") {
                tvDuration.text = DateUtil.calculateTimeDifference(
                    currentItem.first.time,
                    currentItem.second.time
                )
            }

            tvSeeDetail.setOnClickListener {
                mOnClick?.invoke(currentItem, tvAttendanceStatus.text.toString())
            }
            when(currentItem.first.status) {
                STATUS_CHECK_IN -> {
                    if (DateUtil.isFirstTimeisSmallerThanSecondTime(
                            currentItem.first.time,
                            WORK_START_TIME
                        )
                    ) {
                        tvAttendanceStatus.text = context.getString(R.string.lbl_early_checkin)
                        tvAttendanceStatus.setTextColor(context.resources.getColor(R.color.colorPurple))
                        tvAttendanceStatus.backgroundTintList = context.resources.getColorStateList(R.color.colorPurplePastel);
                    }
                    if (DateUtil.isFirstTimeisBiggerThanSecondTime(
                            currentItem.first.time,
                            WORK_START_TIME
                        )
                    ) {
                        tvAttendanceStatus.text = context.getString(R.string.lbl_late)
                        tvAttendanceStatus.setTextColor(context.resources.getColor(R.color.colorRed))
                        tvAttendanceStatus.backgroundTintList = context.resources.getColorStateList(R.color.colorRedPastel);

                    } else {
                        tvAttendanceStatus.text = context.getString(R.string.check_in)
                        tvAttendanceStatus.setTextColor(context.resources.getColor(R.color.colorOrange))
                        tvAttendanceStatus.backgroundTintList = context.resources.getColorStateList(R.color.colorOrangePastel);
                    }

                }
            }
            when(currentItem.second.status) {
                STATUS_CHECK_OUT ->{
                    if (DateUtil.isFirstTimeisSmallerThanSecondTime(currentItem.second.time, WORK_END_TIME)) {
                        tvAttendanceStatus.text = context.getString(R.string.lbl_early_checkout)
                        tvAttendanceStatus.setTextColor(context.resources.getColor(R.color.colorPurple))
                        tvAttendanceStatus.backgroundTintList = context.resources.getColorStateList(R.color.colorPurplePastel);

                    }
                    else if (DateUtil.isFirstTimeisBiggerThanSecondTime(currentItem.second.time, WORK_END_TIME)) {
                        tvAttendanceStatus.text = context.getString(R.string.lbl_overtime)
                        tvAttendanceStatus.setTextColor(context.resources.getColor(R.color.colorOrange))
                        tvAttendanceStatus.backgroundTintList = context.resources.getColorStateList(R.color.colorOrangePastel);
                    }
                    else{
                        tvAttendanceStatus.text = context.getString(R.string.check_out)
                        tvAttendanceStatus.setTextColor(context.resources.getColor(R.color.colorRed))
                        tvAttendanceStatus.backgroundTintList = context.resources.getColorStateList(R.color.colorRedPastel);

                    }
                }
            }
        }
    }



    fun clearData() {
        val oldSize = dataListFull.size
        dataListFull.clear()
        notifyItemRangeRemoved(0, oldSize)
    }

    fun setData(list: List<Pair<AbsensionModel,AbsensionModel>>) {
        dataListFull.addAll(list)
        notifyItemRangeInserted(0, dataListFull.size)
    }

    inner class ExampleViewHolder(val binding: AbsensionItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {}
    }

}