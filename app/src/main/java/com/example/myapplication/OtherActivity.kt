package com.example.myapplication

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_other_list.*

class OtherActivity : AppCompatActivity() {
    var wordListOther = ArrayList<Word>()
    var userID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_list)


        if(intent.hasExtra("wordList")){
            var getFindBundle = intent.getBundleExtra("wordList")
            var wordListTemp = getFindBundle.get("word") as ArrayList<Word>
            userID = intent.getStringExtra("userID")

            var idx = 0
            while(idx<wordListTemp.size){
                wordListOther.add(wordListTemp[idx])
                idx+=1
            }
        }

        text_other_list_title.text = "${userID}'s WordNote"

        val listView = findViewById<ListView>(R.id.listview_other)
        val adapter = OtherViewAdapter(this, wordListOther)
        listView.adapter = adapter

        button_other_back.setOnClickListener {
            wordListOther.clear()
            println("size : ${wordListOther.size}")
            finish()
        }
    }

}