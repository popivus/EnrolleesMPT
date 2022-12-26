package com.example.enrolleesmpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val item = intent.getParcelableExtra<ItemOfList>("OBJECT_INTENT")!!

        val name = findViewById<TextView>(R.id.nameSpecDetail)
        val description = findViewById<TextView>(R.id.descriptionSpecDetail)
        val qualification = findViewById<TextView>(R.id.qualSpecDetail)
        val class9 = findViewById<TextView>(R.id.period9SpecDetail)
        val class11 = findViewById<TextView>(R.id.period11SpecDetail)

        name.text = item.name
        description.text = item.description
        qualification.text = item.qualification
        var trainingPeriods = item.trainingPeriod.split('\n')
        class9.text = trainingPeriods[0]
        class11.text = trainingPeriods[1]
    }
}