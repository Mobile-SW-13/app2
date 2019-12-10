package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class OtherViewAdapter(val context : Context, val wordList: ArrayList<Word>) : BaseAdapter() {

    override fun getCount(): Int {
        return wordList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return wordList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        println("other : adapter Set")
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.layout_word_other, null)

        val nameView = view.findViewById<TextView>(R.id.txt_other_name)
        val meanView = view.findViewById<TextView>(R.id.txt_other_mean)

        nameView.text = wordList[position].wordName.toString()
        meanView.text = wordList[position].wordMean.toString()

        return view

    }
}