package com.example.simplediseroller

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.example.simplediseroller.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    val diceList = mutableMapOf<TextView, Int>()
    var grain: Int = 6
    lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addDiceToList()
        spinnerPutUp()

    }

    fun spinnerPutUp() {
        spinner = binding.spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.dices,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
    }

    fun addDiceToList() {
        val arr = mutableListOf<Int>()
        var i = 0
        binding.ml.forEach {
            if (it is TextView && it.tag == "dice") {
                i++
                arr.add(it.id)
                diceList[it] = i
                it.setOnClickListener {
                    diceClick(it as TextView)
                }
            }
        }
        binding.mainFlow.referencedIds = arr.toIntArray()
    }

    fun diceClick(obj: TextView) {
        val ind = diceList[obj]
        diceList.filter { it.value <= ind!! }.forEach {
            rollDice(it.key, grain)
        }

        diceList.filter { it.value > ind!! }.forEach { textView, _ -> textView.text ="1"  }

    }

    private fun rollDice(dice: TextView, grain: Int) {
        val r = Random.nextInt(1, grain + 1)
        dice.text = r.toString()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item: String = p0?.getItemAtPosition(p2) as String
        grain = item.removePrefix("D").toInt()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}