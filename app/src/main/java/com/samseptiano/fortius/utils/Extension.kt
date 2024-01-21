package com.samseptiano.fortius.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.samseptiano.fortius.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


fun View.setBottomsheetWrapContent(): View {
    val layoutParams = this.layoutParams
    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    this.layoutParams = layoutParams
    return this
}

fun Int.formatRupiah(): String {
    val localeID = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    return formatRupiah.format(this)
}

fun getCurrentDate(): String {
    val c = Calendar.getInstance().time
    val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return df.format(c)
}

fun Double.doubleToStringWithSeparator(): String {
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###")
    return formatter.format(this)
}

fun Context.showSpinnerDialog(
    title: String,
    listArray: ArrayList<String>,
    callback: (Int, String) -> Unit
) {
    // Initialize dialog
    val dialog = Dialog(this)

    // set custom dialog
    dialog.setContentView(R.layout.dialong_search_spinner)

    // set custom height and width
    val lp = WindowManager.LayoutParams()
    lp.copyFrom(dialog.window!!.attributes)
    lp.width = WindowManager.LayoutParams.MATCH_PARENT
    lp.height = 800

    dialog.show()
    val window = dialog.window
    window!!.attributes = lp

    // set transparent background
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    // show dialog
    dialog.show()

    // Initialize and assign variable
    val tvTitle: TextView = dialog.findViewById(R.id.dialog_title)
    val editText: EditText = dialog.findViewById(R.id.edit_text)
    val listView: ListView = dialog.findViewById(R.id.list_view)

    tvTitle.text = title

    // Initialize array adapter
    val adapter: ArrayAdapter<String> =
        ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listArray)

    // set adapter
    listView.adapter = adapter
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            adapter.filter.filter(s)
        }

        override fun afterTextChanged(s: Editable) {}
    })

    listView.onItemClickListener =
        AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
            callback(position, adapter.getItem(position).toString())
            // Dismiss dialog
            dialog.dismiss()
        }
}
