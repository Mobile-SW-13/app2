package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class Fragment_mean : Fragment() {

    var wordListFM = ArrayList<Word>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_mean, container, false)


        wordListFM = arguments!!.get("word") as ArrayList<Word>


        val adapter = CustomViewAdapter(wordListFM,1 )

        val listview = layout.findViewById(R.id.listview_word_mean) as ListView
        listview.setAdapter(adapter)

        return layout

    }
}