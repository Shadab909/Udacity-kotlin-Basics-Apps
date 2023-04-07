package com.example.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var editText : EditText
    private lateinit var doneBtn : Button
    private lateinit var nickName : TextView
    private lateinit var binding : ActivityMainBinding
   //creating variable of data class
    private val myName : MyName = MyName("Madison")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
       //setting myName layout variable  to myName data class variable
        binding.myName = myName

        binding.doneBtn.setOnClickListener {
            addNickName(it)
        }
    }

    private fun addNickName(view : View) {
        view.visibility = View.GONE

        binding.apply {
            //setting nickName from MyName class to be what is on editText
            myName?.nickName = nickNameEditText.text.toString()

            nickNameEditText.visibility = View.GONE
            invalidateAll()
            nickNameText.text = binding.nickNameEditText.text
            nickNameText.visibility = View.VISIBLE
        }
        // below code se done btn click krne k baad keyboard gayab ho jayega
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,0)

    }
}