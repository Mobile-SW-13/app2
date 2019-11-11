package com.example.myapplication

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class Fragment_both : Fragment() {

    var wordListFB = ArrayList<Word>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_both, container, false)


        wordListFB = arguments!!.get("word") as ArrayList<Word>


        val adapter = CustomViewAdapter(wordListFB,2 )

        val listview = layout.findViewById(R.id.listview_word_both) as ListView
        listview.setAdapter(adapter)

        return layout

    }
}