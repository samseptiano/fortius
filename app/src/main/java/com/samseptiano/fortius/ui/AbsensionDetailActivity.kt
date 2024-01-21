package com.samseptiano.fortius.ui

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.samseptiano.fortius.data.model.response.AbsensionListModel
import com.samseptiano.fortius.databinding.ActivityAbsensionDetailBinding
import com.samseptiano.fortius.ui.adapter.AbsensionDetailAdapter
import com.samseptiano.fortius.ui.viewmodel.AbsensionDetailViewModel
import com.samseptiano.fortius.utils.DateUtil
import com.samseptiano.fortius.utils.DateUtil.calculateDaysDifference
import com.samseptiano.base.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AbsensionDetailActivity : BaseActivity<ActivityAbsensionDetailBinding>() {
    companion object {
        const val DATA_DETAIL = "data_detail"
        const val DATA_STATUS = "data_status"
    }

    private lateinit var viewModel: AbsensionDetailViewModel
    private lateinit var dataDetailCheckIn: AbsensionListModel
    private lateinit var status: String
    private var absensionDetailAdapter: AbsensionDetailAdapter? = null

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityAbsensionDetailBinding =
        ActivityAbsensionDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        init()
        setupAdapter()
        getIntentExtraData()
        setupViews()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[AbsensionDetailViewModel::class.java]
    }

    private fun setupViews() {
        binding.apply {
            tvEmployeeName.text = dataDetailCheckIn.list.first().name
            tvEmployeeId.text = dataDetailCheckIn.list.first().companyName
            tvTotalCheckin.text = "(${dataDetailCheckIn.list.size}) Check In(s)"
            tvDate.text = dataDetailCheckIn.list.first().date
            tvStatus.text = status
            tvDuration.text = DateUtil.calculateTimeDifference(
                dataDetailCheckIn.list.first().time,
                dataDetailCheckIn.list.last().time
            )
            tvCheckinTime.text = dataDetailCheckIn.list.first().time
            tvCheckoutTime.text = dataDetailCheckIn.list.last().time
            tvCheckinLocation.text = dataDetailCheckIn.list.first().location
            tvCheckoutLocation.text = dataDetailCheckIn.list.last().location
            tvCrossDay.text = calculateDaysDifference(
                dataDetailCheckIn.list.first().date,
                dataDetailCheckIn.list.last().date
            )

        }
    }

    private fun getIntentExtraData() {
        if (intent.hasExtra(DATA_DETAIL)) {
            val stringExtra = intent.getStringExtra(DATA_DETAIL)
            stringExtra.let {
                dataDetailCheckIn = Gson().fromJson(stringExtra, AbsensionListModel::class.java)
                Log.d("display detaillll", stringExtra.toString())
                absensionDetailAdapter?.setData(dataDetailCheckIn.list)
            }
        }

        if (intent.hasExtra(DATA_STATUS)) {
            status = intent.getStringExtra(DATA_STATUS) ?: "-"
        }
    }

    private fun setupAdapter() {
        absensionDetailAdapter = AbsensionDetailAdapter(this, arrayListOf()) {}

        binding.rvPhotos.apply {
            adapter = absensionDetailAdapter
            layoutManager =
                LinearLayoutManager(
                    this@AbsensionDetailActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            setHasFixedSize(true)
        }
    }

}