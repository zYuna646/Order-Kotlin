package com.example.kfc

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var nameEditText: EditText
    private lateinit var foodSpinner: Spinner
    private lateinit var checkBoxSambal: CheckBox
    private lateinit var checkBoxTelur: CheckBox
    private lateinit var quantitySeekBar: SeekBar
    private lateinit var quantityTextView: TextView
    private lateinit var orderButton: Button
    private lateinit var priceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.nameEditText)
        foodSpinner = findViewById(R.id.foodSpinner)
        checkBoxSambal = findViewById(R.id.checkBoxSambal)
        checkBoxTelur = findViewById(R.id.checkBoxTelur)
        quantitySeekBar = findViewById(R.id.quantitySeekBar)
        quantityTextView = findViewById(R.id.quantityTextView)
        orderButton = findViewById(R.id.orderButton)
        priceTextView = findViewById(R.id.priceTextView)

        val foodOptions = arrayOf("Nasi Goreng", "Mie Goreng", "Sate Ayam", "Gado-Gado")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, foodOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        foodSpinner.adapter = adapter

        orderButton.setOnClickListener(this)

        quantitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                quantityTextView.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onClick(view: View?) {
        if (view == orderButton) {
            val name = nameEditText.text.toString()
            val food = foodSpinner.selectedItem.toString()
            val hasSambal = checkBoxSambal.isChecked
            val hasTelur = checkBoxTelur.isChecked
            val quantity = quantitySeekBar.progress

            val totalPrice = calculatePrice(hasSambal, hasTelur, quantity)
            val orderSummary = createOrderSummary(name, food, hasSambal, hasTelur, quantity, totalPrice)

            priceTextView.text = orderSummary
        }
    }

    private fun calculatePrice(hasSambal: Boolean, hasTelur: Boolean, quantity: Int): Int {
        var price = 0

        when (foodSpinner.selectedItemPosition) {
            0 -> price = 15000
            1 -> price = 12000
            2 -> price = 10000
            3 -> price = 8000
        }

        if (hasSambal) {
            price += 2000
        }

        if (hasTelur) {
            price += 3000 
        }

        return price * quantity
    }

    private fun createOrderSummary(
        name: String,
        food: String,
        hasSambal: Boolean,
        hasTelur: Boolean,
        quantity: Int,
        totalPrice: Int
    ): String {
        val sambalString = if (hasSambal) "Ya" else "Tidak"
        val telurString = if (hasTelur) "Ya" else "Tidak"

        val orderSummary = "Nama: $name\n" +
                "Makanan: $food\n" +
                "Topping Sambal: $sambalString\n" +
                "Topping Telur: $telurString\n" +
                "Jumlah: $quantity\n" +
                "Total Harga: Rp $totalPrice"

        return orderSummary
    }
}
