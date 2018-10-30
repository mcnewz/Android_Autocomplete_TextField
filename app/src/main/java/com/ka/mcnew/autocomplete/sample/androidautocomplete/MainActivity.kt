package com.ka.mcnew.autocomplete.sample.androidautocomplete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val adapter = CustomListAdapter(this,
//                android.R.layout.simple_spinner_dropdown_item, getData())
        val adapter = TextAdapterGeneric(this, android.R.layout.simple_spinner_dropdown_item, retrieveDataFilter())

        ac_text.apply {
            setAdapter(adapter)
            threshold = 1
            filters = arrayOf<InputFilter>(InputFilter.AllCaps())
            onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                val vo = parent!!.getItemAtPosition(position) as DataFilters
                val strItem = vo.filterName
                setText(strItem)
            }
            setOnTouchListener { _, _ ->
                showDropDown()
                false
            }
        }

        ac_text_2.apply {
            setAdapter(adapter)
            threshold = 1
            filters = arrayOf<InputFilter>(InputFilter.AllCaps())
            onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                val vo = parent!!.getItemAtPosition(position) as DataFilters
                val strItem = vo.filterName
                setText(strItem)
            }
            setOnTouchListener { _, _ ->
                showDropDown()
                false
            }

            onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                Log.d("onFocus", "$hasFocus")
            }
        }
    }

    private fun getData(): List<String> {
        val dataList = ArrayList<String>()
        dataList.add("Fashion Men")
        dataList.add("Fashion Women")
        dataList.add("Baby")
        dataList.add("Kids")
        dataList.add("Electronics")
        dataList.add("Appliance")
        dataList.add("Travel")
        dataList.add("Bags")
        dataList.add("FootWear")
        dataList.add("Jewellery")
        dataList.add("Sports")
        dataList.add("Electrical")
        dataList.add("Sports Kids")
        return dataList
    }

    private fun retrieveDataFilter(): ArrayList<DataFilters> {
        val list = ArrayList<DataFilters>()
        list.add(DataFilters("James", "Bond"))
        list.add(DataFilters("อะไรนะครับ", "ไม่มีอะไร"))
        list.add(DataFilters("Jason", "Bourne"))
        list.add(DataFilters("Ethan", "Hunt"))
        list.add(DataFilters("Sherlock", "Holmes"))
        list.add(DataFilters("David", "Beckham"))
        list.add(DataFilters("Bryan", "Adams"))
        list.add(DataFilters("Baryan", "Adams"))
        list.add(DataFilters("Baryan", "Adams"))
        list.add(DataFilters("Bsryan", "Adams"))
        list.add(DataFilters("Bsryan", "Adams"))
        list.add(DataFilters("Arjen", "Robben"))
        list.add(DataFilters("Van", "Persie"))
        list.add(DataFilters("Zinedine", "Zidane"))
        list.add(DataFilters("Luis", "Figo"))
        list.add(DataFilters("John", "Watson"))
        return list
    }
}
