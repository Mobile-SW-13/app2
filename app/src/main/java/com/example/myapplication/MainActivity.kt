package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    var wordList = ArrayList<Word>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordList.add(Word("KAU", "한국항공대"))
        wordList.add(Word("apple", "사과"))
        wordList.add(Word("banana","바나나"))

        button_quiz.setOnClickListener{
            var findBundle  = Bundle()
            findBundle.putSerializable("word", wordList)
            val intent=Intent(this,QuizActivity::class.java)
            intent.putExtra("wordList", findBundle)
            startActivity(intent)
        }

        button_find.setOnClickListener{
            var findBundle  = Bundle()
            findBundle.putSerializable("word", wordList)

            val intent=Intent(this,FindActivity::class.java)
            intent.putExtra("wordList", findBundle)
            startActivityForResult(intent,1)
        }

        button_word.setOnClickListener{
            FragmentWord()
        }

        button_both.setOnClickListener{
            FragmentBoth()
        }

        button_mean.setOnClickListener{
            FragmentMean()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1){
            if(resultCode == 1){
                if(data!!.hasExtra("wordList")){
                    var getBundle = data.getBundleExtra("wordList")
                    wordList = getBundle.get("word") as ArrayList<Word>
                }
            }
        }
    }

    fun FragmentBoth(){
        var bothBundle  = Bundle()
        bothBundle.putSerializable("word", wordList)

        var bothFragment = Fragment_both()
        bothFragment.arguments = bothBundle


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, bothFragment)
            .commit()


    }

    fun FragmentMean(){
        var meanBundle  = Bundle()
        meanBundle.putSerializable("word", wordList)

        var meanFragment = Fragment_mean()
        meanFragment.arguments = meanBundle


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, meanFragment)
            .commit()
    }

    fun FragmentWord(){
        var wordBundle  = Bundle()
        wordBundle.putSerializable("word", wordList)

        var wordFragment = Fragment_word()
        wordFragment.arguments = wordBundle


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, wordFragment)
            .commit()
    }

}

data class Word(
    var wordName: String? = null,
    var wordMean: String? = null
) : Serializable
