package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_both.*
import kotlinx.android.synthetic.main.popup_layout_word_detail_word_add.*
import kotlinx.android.synthetic.main.popup_layout_word_detail_word_add.view.*
import java.nio.channels.GatheringByteChannel
import java.util.zip.Inflater

class Fragment_both : Fragment() {

    var wordListFB = ArrayList<Word>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_both, container, false)


        wordListFB = arguments!!.get("word") as ArrayList<Word>


        var adapter = CustomViewAdapter(wordListFB,2 )


        var listview = layout.findViewById(R.id.listview_word_both) as ListView

        listview.setAdapter(adapter)

        val button = layout.findViewById<Button>(R.id.btn_add_word_both)

        button.setOnClickListener{

            println("Test Button In Fragment")

            val popupView = getLayoutInflater().inflate(R.layout.popup_layout_word_detail_word_add,null)
            val buttonClose = popupView.findViewById<Button>(R.id.btn_popup_close)
            val buttonAdd = popupView.findViewById<Button>(R.id.btn_popup_add)
            var textName = popupView.findViewById(R.id.txt_edit_word_name) as EditText
            var textMean = popupView.findViewById(R.id.txt_edit_word_mean) as EditText


            val popupWindow = PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)



            buttonClose.setOnClickListener {
                popupWindow.dismiss()
            }

            buttonAdd.setOnClickListener{
                if(textMean.text.toString() == ""||textName.text.toString() == ""){
                    Toast.makeText(container?.context, "?", Toast.LENGTH_LONG)
                }else{
                    wordListFB.add(Word(textName.text.toString(),textMean.text.toString()))
                    adapter.notifyDataSetChanged()
                    popupWindow.dismiss()

                }
            }

            popupWindow.setFocusable(true)

            popupWindow.showAtLocation(popupView,Gravity.CENTER,0,0)

        }

        return layout

    }



}