package com.example.tipxml

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var amountField: TextInputEditText
    private lateinit var tipOptions: MaterialAutoCompleteTextView
    private lateinit var roundupSwitch: MaterialSwitch
    private lateinit var tipamountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        amountField = findViewById(R.id.amount_field)
        tipOptions = findViewById(R.id.tip_options)
        roundupSwitch = findViewById(R.id.round_up_switch)
        tipamountText = findViewById(R.id.tip_amount)

        val percentages = resources.getStringArray(R.array.tip_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, percentages)

        tipOptions.setAdapter(adapter)
        tipOptions.setText(percentages[0], false)

        tipOptions.setOnItemClickListener { _, _, _, _ -> calculateTip() }
        roundupSwitch.setOnCheckedChangeListener { _, _ -> calculateTip() }

        amountField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateTip()
            }
        })
    }

    private fun calculateTip() {
        val stringinTextField = amountField.text.toString()
        val amount = stringinTextField.toDoubleOrNull()

        if (amount == null || amount == 0.0) {
            tipamountText.text = getString(R.string.tip_amount, "$0.0")
            return
        }

        val tipString = tipOptions.text.toString().replace("%", "")
        val tipPercentage = tipString.toDoubleOrNull() ?: 0.0

        var tip = amount * (tipPercentage / 100)

        if (roundupSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        val formatTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
        tipamountText.text = getString(R.string.tip_amount, formatTip)
    }
}