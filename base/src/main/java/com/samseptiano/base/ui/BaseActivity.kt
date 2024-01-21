package com.samseptiano.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
    }

    fun showSnackBar(view: View, message:String, action: String = "", actionListener: () -> Unit = {}){
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        if (action != "") {
            snackbar.duration = Snackbar.LENGTH_INDEFINITE
            snackbar.setAction(action) {
                actionListener()
                snackbar.dismiss()
            }
        }
        snackbar.show()
    }

    protected open fun showProgress() {}

    protected open fun hideProgress() {}
}