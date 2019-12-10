package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    val db = FirebaseDatabase.getInstance()
    var userId = "lch"


    var wordList = ArrayList<Word>()
    var userList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.hasExtra("userId")){
            userId = intent.getStringExtra("userId")
        }else{
            finish()
        }

        val myreff = db.getReference("${userId}/wordNote")

        myreff.addChildEventListener(object : ChildEventListener{
            override fun onChildRemoved(p0: DataSnapshot) {
                println("Removed")
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                println("added : ${p0.value.toString()}")
                wordList.add(Word(p0.key.toString(),p0.value.toString()))
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                println("changed")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                println("moved")
            }

            override fun onCancelled(p0: DatabaseError) {
                println("cancelled")
            }

        })

        val shareRef = db.getReference("")
        shareRef.addChildEventListener(object : ChildEventListener{
            override fun onChildRemoved(p0: DataSnapshot) {
                println("share : removed")
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                println("share : ${p0.key.toString()}")
                userList.add(p0.key.toString())
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                println("share : changeed")
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                println("share : moved")
            }

            override fun onCancelled(p0: DatabaseError) {
                println("share : cancelled")
            }

        })

        /*if(intent.hasExtra("wordList")){
            var getFindBundle = intent.getBundleExtra("wordList")
            wordList = getFindBundle.get("word") as ArrayList<Word>
        }*/

        button_quiz.setOnClickListener{
            var findBundle  = Bundle()
            findBundle.putSerializable("word", wordList)
            val intent=Intent(this,QuizActivity::class.java)
            intent.putExtra("wordList", findBundle)
            startActivity(intent)
        }

        button_find.setOnClickListener{
            //var findBundle  = Bundle()
            //findBundle.putSerializable("word", wordList)

            val intent=Intent(this,FindActivity::class.java)
            //intent.putExtra("wordList", findBundle)
            //startActivityForResult(intent,1)

            intent.putExtra("userId", userId)
            startActivity(intent)
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

        button_share.setOnClickListener {
            Handler().postDelayed({
                if(wordList.size!=0){
                    var shareBundle  = Bundle()
                    shareBundle.putSerializable("user", userList)

                    val intent = Intent(this, ShareActivity::class.java)
                    intent.putExtra("userList", shareBundle)
                    startActivity(intent)
                }

            }, 1000)


        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1){
            if(resultCode == 1){
                if(data!!.hasExtra("wordList")){
                    var getBundle = data.getBundleExtra("wordList")
                    wordList = getBundle.get("word") as ArrayList<Word>
                    var findBundle  = Bundle()
                    findBundle.putSerializable("word", wordList)
                    val intent=Intent(this,MainActivity::class.java)
                    intent.putExtra("wordList", findBundle)
                    finish()
                    startActivity(intent)
                }
            }
        }
    }*/

    fun FragmentBoth(){
        var bothBundle  = Bundle()
        bothBundle.putSerializable("word", wordList)
        bothBundle.putString("userId", userId)

        var bothFragment = Fragment_both()
        bothFragment.arguments = bothBundle


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, bothFragment)
            .commit()
    }

    fun FragmentMean(){
        var meanBundle  = Bundle()
        meanBundle.putSerializable("word", wordList)
        meanBundle.putString("userId", userId)

        var meanFragment = Fragment_mean()
        meanFragment.arguments = meanBundle


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, meanFragment)
            .commit()
    }

    fun FragmentWord(){
        var wordBundle  = Bundle()
        wordBundle.putSerializable("word", wordList)
        wordBundle.putString("userId", userId)

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
