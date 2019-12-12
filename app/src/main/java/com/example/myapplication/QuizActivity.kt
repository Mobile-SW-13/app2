package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {
    var wordListQZ = ArrayList<Word>()
    var quizWordList = ArrayList<QuizWord>()
    var quizList = ArrayList<WordAnswer>()
    var quizStart = 0
    var quizNumber = 0
    var quizProgress = -1
    var correct = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        if(intent.hasExtra("wordList")){
            var getFindBundle = intent.getBundleExtra("wordList")
            wordListQZ = getFindBundle.get("word") as ArrayList<Word>
        }


        if(wordListQZ.size<5){
            txt_notice.text = "단어의 수가 너무 적습니다."
            txt_notice_ex.text = "시작불가"
            quizStart = -1
        }else{
            QuizSetting()
        }


        btn_quiz_home.setOnClickListener{
            setResult(2)
            finish()
        }

        btn_answer_one.setOnClickListener {
            if(quizStart==0){

                NextQuestion()
                quizStart=1
                txt_notice.text = ""
                txt_notice_ex.text = ""
            }else if(quizStart==1){
                if(quizList[quizProgress].right==0){
                    correct+=1
                }

                if((quizNumber==1&&quizProgress==quizWordList.size)||(quizNumber==20&&quizProgress==20)){
                    FinishQuiz()
                }else{
                    NextQuestion()
                }
            }

        }

        btn_answer_two.setOnClickListener {
            if(quizStart==0){
                NextQuestion()
                quizStart=1
                txt_notice.text = ""
                txt_notice_ex.text = ""
            }else if(quizStart==1){
                if(quizList[quizProgress].right==1){
                    correct+=1
                }

                if((quizNumber==1&&quizProgress==quizWordList.size)||(quizNumber==20&&quizProgress==20)){
                    FinishQuiz()
                }else{
                    NextQuestion()
                }
            }
        }

        btn_answer_three.setOnClickListener {
            if(quizStart==0){
                NextQuestion()
                quizStart=1
                txt_notice.text = ""
                txt_notice_ex.text = ""
            }else if(quizStart==1){
                if(quizList[quizProgress].right==2){
                    correct+=1
                }

                if((quizNumber==1&&quizProgress==quizWordList.size)||(quizNumber==20&&quizProgress==20)){
                    FinishQuiz()
                }else{
                    NextQuestion()
                }
            }
        }

        btn_answer_four.setOnClickListener {
            if(quizStart==0){
                NextQuestion()
                quizStart=1
                txt_notice.text = ""
                txt_notice_ex.text = ""
            }else if(quizStart==1){
                if(quizList[quizProgress].right==3){
                    correct+=1
                }

                if((quizNumber==1&&quizProgress==quizWordList.size)||(quizNumber==20&&quizProgress==20)){
                    FinishQuiz()
                }else{
                    NextQuestion()
                }
            }
        }

        btn_answer_five.setOnClickListener {
            if(quizStart==0) {
                NextQuestion()
                quizStart=1
                txt_notice.text = ""
                txt_notice_ex.text = ""
            }else if(quizStart==1){
                if(quizList[quizProgress].right==4){
                    correct+=1
                }

                if((quizNumber==1&&quizProgress==quizWordList.size)||(quizNumber==20&&quizProgress==20)){
                    FinishQuiz()
                }else{
                    NextQuestion()
                }
            }
        }

        btn_quiz_restart.setOnClickListener{
            if(quizStart==1){
                quizStart = 0
                quizProgress = -1
                correct = 0
                quizNumber = 0

                txt_notice.text = "단어와 맞는 뜻을 고르세요."
                txt_notice_ex.text = "아래쪽 임의의 버튼을 눌러 시작하세요."
                txt_question.text = ""

                quizWordList.clear()
                quizList.clear()

                QuizSetting()

                btn_answer_one.text = "1"
                btn_answer_two.text = "2"
                btn_answer_three.text = "3"
                btn_answer_four.text = "4"
                btn_answer_five.text = "5"
            }
        }

    }

    fun QuizSetting(){

        ListCopy()

        var idx = 0

        if(wordListQZ.size<20){
            quizNumber=1
            while(idx<wordListQZ.size){
                println("------------------------------")
                println("quiz ${idx} setting start")
                QuizRandom()
                idx+=1
            }
        }else{
            quizNumber=20
            while(idx<20){
                println("------------------------------")
                println("quiz ${idx} setting start")
                QuizRandom()
                idx+=1
            }
        }

    }

    fun NextQuestion(){
        quizProgress+=1

        if(quizProgress==wordListQZ.size){
            FinishQuiz()
            return
        }else{
            var question = quizList[quizProgress].question
            var one = quizList[quizProgress].one
            var two = quizList[quizProgress].two
            var three = quizList[quizProgress].three
            var four = quizList[quizProgress].four
            var five = quizList[quizProgress].five

            if(question!=null)
                txt_question.text = quizWordList[question].wordName

            if(one!=null)
                btn_answer_one.text = quizWordList[one].wordMean

            if(two!=null)
                btn_answer_two.text = quizWordList[two].wordMean

            if(three!=null)
                btn_answer_three.text = quizWordList[three].wordMean

            if(four!=null)
                btn_answer_four.text = quizWordList[four].wordMean

            if(five!=null)
                btn_answer_five.text = quizWordList[five].wordMean

        }



    }

    fun FinishQuiz(){

        var per : Float? = null
        txt_question.text = ""

        if(quizProgress==0){
            per = 0f
        }
        else{
            per = correct.toFloat()/quizProgress.toFloat()
        }


        val result = "결과\n문제 : ${quizProgress}\n정답 : ${correct}\n정답률 : ${per*100f}%"

        btn_answer_one.text = ""
        btn_answer_two.text = ""
        btn_answer_three.text = ""
        btn_answer_four.text = ""
        btn_answer_five.text = ""

        txt_notice.text = result
    }

    fun QuizRandom(){
        val randM = Random
        var setQuestion = 0
        var setOne = 0
        var setTwo = 0
        var setThree = 0
        var setFour = 0
        var setFive = 0
        var setRight = 0
        var idx = 0

        while(true){
            var tempNum = randM.nextInt(quizWordList.size)
            if(quizWordList[tempNum].defquestion==0){
                quizWordList[tempNum].defquestion=1

                println("question set")

                setQuestion = tempNum
                break
            }
        }//문제 설정,문제로 선정 됐다고 기록

        setRight = randM.nextInt(5)

        when(setRight){
            0 -> {
                setOne = setQuestion
                quizWordList[setOne].defproblem =1
            }
            1 -> {
                setTwo = setQuestion
                quizWordList[setTwo].defproblem =1
            }
            2 -> {
                setThree = setQuestion
                quizWordList[setThree].defproblem =1
            }
            3 -> {
                setFour = setQuestion
                quizWordList[setFour].defproblem =1
            }
            4 -> {
                setFive = setQuestion
                quizWordList[setFive].defproblem =1
            }
        }//정답 설정, 문항으로 다시 나올 수 없게 설정

        if(setRight!=0){
            while(true) {
                setOne = randM.nextInt(quizWordList.size)
                println(setOne)

                if(quizWordList[setOne].defproblem!=1){
                    quizWordList[setOne].defproblem=1
                    break
                }
            }
        }
        println("problem 1 set")

        if(setRight!=1){
            while(true) {
                setTwo = randM.nextInt(quizWordList.size)
                println(setTwo)

                if(quizWordList[setTwo].defproblem!=1){
                    quizWordList[setTwo].defproblem=1
                    break
                }
            }
        }

        println("problem 2 set")

        if(setRight!=2){
            while(true) {
                setThree = randM.nextInt(quizWordList.size)
                println(setThree)

                if(quizWordList[setThree].defproblem!=1){
                    quizWordList[setThree].defproblem=1
                    break
                }
            }
        }

        println("problem 3 set")

        if(setRight!=3){
            while(true) {
                setFour = randM.nextInt(quizWordList.size)
                println(setFour)

                if(quizWordList[setFour].defproblem!=1){
                    quizWordList[setFour].defproblem=1
                    break
                }
            }
        }

        println("problem 4 set")

        if(setRight!=4){
            while(true) {
                setFive = randM.nextInt(quizWordList.size)
                println(setFive)

                if(quizWordList[setFive].defproblem!=1){
                    quizWordList[setFive].defproblem=1
                    break
                }
            }
        }

        println("problem 5 set")

        quizList.add(
            WordAnswer(
                setQuestion,
                setOne,
                setTwo,
                setThree,
                setFour,
                setFive,
                setRight)
        )

        while(idx<quizWordList.size){
            quizWordList[idx].defproblem=0
            idx+=1
        }

        println("------------------------------")
    }

    fun ListCopy(){
        var idx = 0

        while(idx<wordListQZ.size){
            quizWordList.add(
                QuizWord(wordListQZ[idx].wordName,
                    wordListQZ[idx].wordMean,
                    0,
                    0)
            )

            idx+=1
        }
    }

}

data class WordAnswer(
    var question : Int? = null,
    var one : Int? = null,
    var two : Int? = null,
    var three : Int? = null,
    var four : Int? = null,
    var five : Int? = null,
    var right : Int? = null
)

data class QuizWord(
    var wordName: String? = null,
    var wordMean: String? = null,
    var defquestion : Int? = null,
    var defproblem : Int? = null
)
