package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.diceroller.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var diceImage : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        diceImage = binding.diceImage
        binding.rollBtn.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {
        //Random().nextInt(6) gives random number from 0 to 5 ,
        // by adding 1 we will get random number from 1 to 6
        val randomInt = Random().nextInt(6) + 1
        val diceImageId = when(randomInt){
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        // baar baar yaha pr function me binding call krna sahi nahi esiliye use
        // pehle hi initialize kiya oncreate me yaha pr use kiya
        diceImage.setImageResource(diceImageId)
    }
}