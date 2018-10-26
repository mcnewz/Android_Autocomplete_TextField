package com.ka.mcnew.autocomplete.sample.androidautocomplete

import android.content.Context
import android.util.Log
import java.nio.file.Files.size
import androidx.annotation.NonNull
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter

class CustomListAdapter(
        mContext: Context,
        private val itemLayout: Int,
        private var dataList: List<String>?
) : ArrayAdapter<String>(mContext, itemLayout, dataList) {
    private val listFilter = ListFilter()
    private var dataListAllItems: List<String>? = null

    override fun getCount(): Int {
        return dataList?.size ?: 0
    }

    override fun getItem(position: Int): String? {
        Log.d("CustomListAdapter",
                dataList!![position])
        return dataList!![position]
    }

    override fun getView(position: Int, v: View?, parent: ViewGroup): View? {
        var view = v
        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
        }
        val strName = view!!.findViewById(android.R.id.text1) as TextView
        strName.text = getItem(position)
        return view
    }

    override fun getFilter(): Filter {
        return listFilter
    }

    inner class ListFilter : Filter() {
        private val lock = Any()
        override fun performFiltering(prefix: CharSequence?): FilterResults {
            val results = FilterResults()
            if (dataListAllItems == null) {
                synchronized(lock) {
                    dataListAllItems = ArrayList(dataList)
                }
            }

            if (prefix == null || prefix.isEmpty()) {
                synchronized(lock) {
                    results.values = dataListAllItems
                    results.count = dataListAllItems!!.size
                }
            } else {
                val searchStrLowerCase = prefix.toString().toLowerCase()
                val matchValues = ArrayList<String>()

                for (dataItem in dataListAllItems!!) {
                    if (dataItem.toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem)
                    }
                }

                results.values = matchValues
                results.count = matchValues.size
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results!!.values != null) {
                dataList = results.values as List<String>?
            } else {
                dataList = null
            }
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }
}