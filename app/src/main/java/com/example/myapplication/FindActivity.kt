package com.example.myapplication

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_find.*
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class FindActivity : AppCompatActivity() {

    var sourceText: String? = null
    var wordListFA = ArrayList<Word>()
    private var statusProgress: ProgressBar?=null;
    var confirmValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        if(intent.hasExtra("wordList")){
            var getFindBundle = intent.getBundleExtra("wordList")
            wordListFA = getFindBundle.get("word") as ArrayList<Word>
        }

        btn_search.setOnClickListener(){
            sourceText = txt_edit.text.toString()
            //AsyncTask를 실행한다.
            AsyncTaskNMT().execute(sourceText)
            confirmValue = 1
        }

        btn_popup_add.setOnClickListener {
            if(confirmValue == 0){
                Toast.makeText(this, "저장할 단어가 없습니다.", Toast.LENGTH_SHORT).show();
            }else{
                wordListFA.add(Word(txt_edit.text.toString(), txt_result.text.toString()))
                Toast.makeText(this, "\"${txt_edit.text}\" 단어가 추가됐습니다.", Toast.LENGTH_SHORT).show();
            }
        }

        btn_home.setOnClickListener{
            var sendBundle  = Bundle()
            sendBundle.putSerializable("word", wordListFA)

            val sendintent=Intent(this,MainActivity::class.java)
            sendintent.putExtra("wordList", sendBundle)

            setResult(1, sendintent)
            finish()
        }

    }

    inner class AsyncTaskNMT : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            statusProgress?.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String): String {

            val clientId = "sUoNOVQRX2ytJDihmSkH"//애플리케이션 클라이언트 아이디값";
            val clientSecret = "MoraHUpncQ"//애플리케이션 클라이언트 시크릿값";
            try {
                val text = URLEncoder.encode(params[0], "UTF-8") //넘어온게 배열이었어?
                val apiURL = "https://openapi.naver.com/v1/papago/n2mt"
                val url = URL(apiURL)
                val con = url.openConnection() as HttpURLConnection
                con.setRequestMethod("POST")
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                val postParams = "source=en&target=ko&text=" + text
                con.doOutput = true
                println(con.outputStream)
                val wr = DataOutputStream(con.outputStream)
                wr.writeBytes(postParams)
                wr.flush()
                wr.close()
                val responseCode = con.responseCode
                val br: BufferedReader
                if (responseCode === 200) { // 정상 호출
                    br = BufferedReader(InputStreamReader(con.inputStream))
                } else {  // 에러 발생
                    br = BufferedReader(InputStreamReader(con.errorStream))
                }
                var inputLine: String? = null
                val response = StringBuffer()
                while ({inputLine = br.readLine(); inputLine}() != null) {
                    response.append(inputLine)
                }
                br.close()
                //println(response.toString())

                var gson = Gson() //오브젝트 생성
                //json file -> Gson object
                val parser = JsonParser()
                val rootObj = parser.parse(response.toString())
                    //원하는 데이터까지 찾아 들어간다.
                    .getAsJsonObject().get("message")
                    .getAsJsonObject().get("result")
                var post = gson.fromJson(rootObj, Message::class.java)
                var stringBuilder = StringBuilder("결과\n---------------------")
                stringBuilder?.append("\n소스언어: " + post.srcLangType)
                stringBuilder?.append("\n타겟언어: " + post.tarLangType)
                stringBuilder?.append("\n번역내용: " + post.translatedText)



                return post.translatedText.toString()


            } catch (e: Exception) {
                println(e)
            }

            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            statusProgress?.visibility = View.GONE

            //번역된 결과를 resultView에 출력하자.
            txt_result.setText(result)


        }
    }
}

data class Message(
    var srcLangType: String? = null,
    var tarLangType: String? = null,
    var translatedText: String? = null
)

