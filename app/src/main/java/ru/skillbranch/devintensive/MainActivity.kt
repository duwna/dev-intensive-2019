package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener, TextView.OnEditorActionListener {

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)

        sendBtn.setOnClickListener(this)
        messageEt.setOnEditorActionListener(this)

        textTxt.text = benderObj.askQuestion()

    }

    override fun onClick(v: View?) {

        if (v?.id == R.id.iv_send){

            hideKeyboard()
            et_message.setText("")

            val answer = et_message.text.toString()

            when(benderObj.question) {
                Bender.Question.NAME -> if(answer[0].isLowerCase()) {
                    val textForTv = "Имя должно начинаться с заглавной буквы\n${benderObj.askQuestion()}"
                    textTxt.text = textForTv
                    return
                    }
                Bender.Question.PROFESSION -> if(answer[0].isUpperCase()) {
                    val textForTv = "Профессия должна начинаться со строчной буквы\n${benderObj.askQuestion()}"
                    textTxt.text = textForTv
                    return
                }
                Bender.Question.MATERIAL -> if(answer.contains(Regex("[1-9]"))) {
                    val textForTv = "Материал не должен содержать цифр\n${benderObj.askQuestion()}"
                    textTxt.text = textForTv
                    return
                }
                Bender.Question.BDAY -> if(!answer.isDigitsOnly()) {
                    val textForTv = "Год моего рождения должен содержать только цифры\n${benderObj.askQuestion()}"
                    textTxt.text = textForTv
                    return
                }
                Bender.Question.SERIAL -> if(!(answer.isDigitsOnly() && answer.length == 7)) {
                    val textForTv = "Серийный номер содержит только цифры и их 7\n${benderObj.askQuestion()}"
                    textTxt.text = textForTv
                    return
                }
                Bender.Question.IDLE -> return
            }

            val (phrase, color) = benderObj.listenAnswer(answer.toLowerCase())
            val (r, g, b) = color
            benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
            textTxt.text = phrase

        }
    }

    override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {

        if (p1 == EditorInfo.IME_ACTION_DONE) {

            hideKeyboard()
            et_message.setText("")

            val answer = et_message.text.toString()

            when(benderObj.question) {
                Bender.Question.NAME -> if(answer[0].isLowerCase()) {
                    val textForTv = "Имя должно начинаться с заглавной буквы\n${benderObj.askQuestion()}"
                    textTxt.text = textForTv
                    return true
                }
                Bender.Question.PROFESSION -> if(answer[0].isUpperCase()) {
                    val textForTv = "Профессия должна начинаться со строчной буквы\n${benderObj.askQuestion()}"
                    textTxt.text = textForTv
                    return true
                }
                Bender.Question.MATERIAL -> if(answer.contains(Regex("[1-9]"))) {
                    val textForTv = "Материал не должен содержать цифр\n${benderObj.askQuestion()}"
                    textTxt.text = textForTv
                    return true
                }
                Bender.Question.BDAY -> if(!answer.isDigitsOnly()) {
                    val textForTv = "Год моего рождения должен содержать только цифры\n${benderObj.askQuestion()}"
                    textTxt.text = textForTv
                    return true
                }
                Bender.Question.SERIAL -> if(!(answer.isDigitsOnly() && answer.length == 7)) {
                    val textForTv = "Серийный номер содержит только цифры и их 7\n${benderObj.askQuestion()}"
                    textTxt.text = textForTv
                    return true
                }
                Bender.Question.IDLE -> return true
            }

            val (phrase, color) = benderObj.listenAnswer(et_message.text.toString().toLowerCase())
            val (r, g, b) = color
            benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
            textTxt.text = phrase

            return true
        }
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("QUESTION", benderObj.question.name)
        outState.putString("STATUS", benderObj.status.name)
    }
}
