package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class Fragment_word : Fragment() {

    var wordListFW = ArrayList<Word>()
    var userId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_word, container, false)


        wordListFW = arguments!!.get("word") as ArrayList<Word>
        userId = arguments!!.get("userId") as String



        val adapter = CustomViewAdapter(wordListFW,0 , userId)

        val listview = layout.findViewById(R.id.listview_word_name) as ListView
        listview.setAdapter(adapter)

        return layout

    }

}

