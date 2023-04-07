package com.example.colormyviews

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // always remember bina findviewid aur binding k direct views ko unki id se use kr sakte hai
    // uske liye plugins me  id 'kotlin-android-extensions' ye id add kro aur view id ko directly import kro
    // es project ko achche se yaad rakho
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        val clickableViews : List<View> = listOf(
            box_one_text,box_two_text,box_three_text,box_four_text,box_five_text,
            constraint_layout,red_btn,yellow_btn,green_btn
        )

        clickableViews.forEach { item->
            item.setOnClickListener { makeColored(item) }
        }
    }

    private fun makeColored(view: View) {
        when(view.id){
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)
            R.id.box_three_text -> view.setBackgroundResource(android.R.color.holo_green_light)
            R.id.box_four_text -> view.setBackgroundResource(android.R.color.holo_blue_light)
            R.id.box_five_text -> view.setBackgroundResource(android.R.color.holo_red_light)
            R.id.constraint_layout -> view.setBackgroundColor(Color.LTGRAY)
            R.id.red_btn -> view.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            R.id.yellow_btn -> view.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))
            else -> view.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green))

        }
    }


}