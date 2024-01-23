package com.samseptiano.fortius.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.samseptiano.base.ui.BaseActivity
import com.samseptiano.fortius.R
import com.samseptiano.fortius.data.model.response.AbsensionListModel
import com.samseptiano.fortius.data.model.response.DateModel
import com.samseptiano.fortius.databinding.ActivityAbsensionListBinding
import com.samseptiano.fortius.ui.AbsensionDetailActivity.Companion.DATA_DETAIL
import com.samseptiano.fortius.ui.AbsensionDetailActivity.Companion.DATA_STATUS
import com.samseptiano.fortius.ui.adapter.AbsensionListAdapter
import com.samseptiano.fortius.ui.adapter.DateAdapter
import com.samseptiano.fortius.ui.viewmodel.AbsensionListViewModel
import com.samseptiano.fortius.utils.DateUtil
import com.samseptiano.fortius.utils.getCurrentDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale


@AndroidEntryPoint
class AbsensionListActivity : BaseActivity<ActivityAbsensionListBinding>() {
    private lateinit var viewModel: AbsensionListViewModel
    private var absensionListAdapter: AbsensionListAdapter? = null
    private var dateAdapter: DateAdapter? = null
    private val listdates: ArrayList<DateModel> = arrayListOf()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityAbsensionListBinding =
        ActivityAbsensionListBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        init()
        setupViews()
        setupAdapter()
        setupObserver()
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[AbsensionListViewModel::class.java]
        listdates.addAll(getDateList())
    }

    private fun setupViews() {
        binding.apply{
            toolbar.ivBack.setOnClickListener {
                finish()
            }
            toolbar.tvTitle.text = getString(R.string.attendance)
            toolbar.ivIcon1.visibility = View.VISIBLE
            toolbar.ivIcon2.visibility = View.VISIBLE
        }
    }

    private fun getDateList(): List<DateModel>{
        val currentDate = getCurrentDate()
        val currYear = currentDate.split("-").first()
        val currMonth = currentDate.split("-")[1]
        val currDate = currentDate.split("-").last()

        val cDate = LocalDate.now()
        val dayOfWeek = cDate.dayOfWeek
        val month: Month = cDate.month
        val monthName = month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

        val listDate = arrayListOf<DateModel>()
        listDate.add(DateModel(true, currDate.toInt(), dayName, currMonth.toInt(), monthName, currYear))

        // Get the 7 days before the current date
        for (i in 1..7) {
            val previousDate = cDate.minusDays(i.toLong())
            val previousDayOfWeek = previousDate.dayOfWeek
            val previousMonth = previousDate.month
            val previousMonthName = previousMonth.getDisplayName(TextStyle.FULL, Locale.getDefault())
            val previousDayName = previousDayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

            listDate.add(
                DateModel(
                    false,
                    previousDate.dayOfMonth,
                    previousDayName,
                    previousDate.monthValue,
                    previousMonthName,
                    previousDate.year.toString()
                )
            )
        }

        // Print or use listDate as needed
        listDate.forEach { dateModel ->
            Log.d("aaaaaa", Gson().toJson(dateModel))
        }
        return listDate
    }
    private fun setupAdapter() {
        dateAdapter = DateAdapter(this@AbsensionListActivity, listdates,{ it, pos ->
            //getData("${it.dateYear}-${String.format("%02d", it.dateMonth)}-${it.dateNumber}")
        },{ it, pos ->
            val header = listdates.find { p -> p.isHeader }
            val idx = listdates.indexOf(header)
            listdates[idx].isHeader = false
            dateAdapter?.notifyItemChanged(idx)

            listdates[pos].isHeader = true
            it.isHeader = true
            dateAdapter?.notifyItemChanged(pos)

            getData("${it.dateYear}-${String.format("%02d", it.dateMonth)}-${it.dateNumber}")
        })

        absensionListAdapter = AbsensionListAdapter(this@AbsensionListActivity, arrayListOf()) { it1, it2 ->
            val intent =
                Intent(this@AbsensionListActivity, AbsensionDetailActivity::class.java).apply {

                    val listData = viewModel.getAbsensionListData.value?.let { it1 ->
                        AbsensionListModel(
                            it1
                        )
                    }

                    putExtra(DATA_DETAIL, Gson().toJson(listData))
                    putExtra(DATA_STATUS, Gson().toJson(it2))

                }
            startActivity(intent)
        }

        binding.rvDate.apply {
            adapter = dateAdapter
            layoutManager =
                LinearLayoutManager(this@AbsensionListActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        binding.rvList.apply {
            adapter = absensionListAdapter
            layoutManager =
                LinearLayoutManager(this@AbsensionListActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }


        getData(DateUtil.getCurrentDate())
    }

    private fun getData(date:String){
        Log.d("get dataaaa", date)
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getAbsension(null, date)
            viewModel.getAbsensionListbyDate(date)
        }
    }
    private fun setupObserver() {
        viewModel.getAbsensionData.observe(this) {
            absensionListAdapter?.clearData()
            absensionListAdapter?.setData(it)
        }

        viewModel.getAbsensionListData.observe(this) {}
    }

}