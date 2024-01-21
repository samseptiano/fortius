package com.samseptiano.fortius.ui

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.samseptiano.fortius.data.model.response.AbsensionListModel
import com.samseptiano.fortius.databinding.ActivityAbsensionListBinding
import com.samseptiano.fortius.ui.AbsensionDetailActivity.Companion.DATA_DETAIL
import com.samseptiano.fortius.ui.AbsensionDetailActivity.Companion.DATA_STATUS
import com.samseptiano.fortius.ui.adapter.AbsensionListAdapter
import com.samseptiano.fortius.ui.viewmodel.AbsensionListViewModel
import com.samseptiano.fortius.utils.DateUtil
import com.samseptiano.base.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AbsensionListActivity : BaseActivity<ActivityAbsensionListBinding>() {
    private lateinit var viewModel: AbsensionListViewModel
    private var absensionListAdapter: AbsensionListAdapter? = null

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityAbsensionListBinding =
        ActivityAbsensionListBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        init()
        setupViews()
        setupAdapter()
        setupObserver()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[AbsensionListViewModel::class.java]
    }

    private fun setupViews() {}

    private fun setupAdapter() {
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

        binding.rvList.apply {
            adapter = absensionListAdapter
            layoutManager =
                LinearLayoutManager(this@AbsensionListActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getAbsension(null, DateUtil.getCurrentDate())
            viewModel.getAbsensionListbyDate(DateUtil.getCurrentDate())
        }
    }

    private fun setupObserver() {
        viewModel.getAbsensionData.observe(this) {
            absensionListAdapter?.setData(it)
        }

        viewModel.getAbsensionListData.observe(this) {}
    }

}