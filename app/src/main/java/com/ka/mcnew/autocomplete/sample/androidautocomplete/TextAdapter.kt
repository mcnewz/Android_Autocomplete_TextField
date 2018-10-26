package com.ka.mcnew.autocomplete.sample.androidautocomplete

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.view.LayoutInflater

class TextAdapter(context: Context?,
                  private val resource: Int,
                  private val listPeople: MutableList<DataFilters>
) : ArrayAdapter<DataFilters>(context, resource, listPeople), Filterable {
    var dataList: MutableList<DataFilters>? = null
    private val listFilter: ListFilter
    private var dataListAllItems: List<DataFilters>? = null

    init {
        listFilter = ListFilter()
        dataList = listPeople!!
    }

    override fun getCount(): Int {
        return dataList?.size ?: 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if (convertView == null) {
        }
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent!!.context).inflate(resource, parent, false)
        }
        val strName = view!!.findViewById(android.R.id.text1) as TextView
        strName.text = getItem(position).value
        return view
    }

    override fun getFilter(): Filter {
        return listFilter
    }

    override fun getItem(position: Int): DataFilters {
        Log.d("CustomListAdapter", dataList!![position].toString())
        return dataList!![position]
    }

    inner class ListFilter : Filter() {
        private val lock = Any()
        override fun performFiltering(prefix: CharSequence?): Filter.FilterResults {
            val results = Filter.FilterResults()
            if (dataListAllItems == null) {
                synchronized(lock) {
                    dataListAllItems = ArrayList<DataFilters>(listPeople)
                }
            }

            if (prefix == null || prefix.isEmpty()) {
                synchronized(lock) {
                    results.values = dataListAllItems
                    results.count = dataListAllItems!!.size
                }
            } else {
                val searchStrLowerCase = prefix.toString()
                val matchValues = ArrayList<DataFilters>()

                for (dataItem in dataListAllItems!!) {
                    if (dataItem.filterName!!.startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem)
                    }
                }
                results.values = matchValues
                results.count = matchValues.size
            }

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {
            if (results != null) {
                if (results.values != null) {
                    dataList = (results.values as ArrayList<DataFilters>?)!!
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
}