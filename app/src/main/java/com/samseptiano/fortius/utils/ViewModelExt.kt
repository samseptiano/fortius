package com.samseptiano.fortius.utils

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


fun <T : ViewModel> obtainViewModel(
    activity: FragmentActivity,
    viewModelClass: Class<T>,
    viewModelFactory: ViewModelProvider.Factory
) = ViewModelProvider(activity, viewModelFactory)[viewModelClass]