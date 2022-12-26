package com.example.enrolleesmpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_documents.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class DocumentsActivity : AppCompatActivity() {
    var okHttpClient: OkHttpClient = OkHttpClient()
    private val URL = "http://${getIp()}:5000/api/DocNotifications"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_documents)
        
        var dateList: MutableList<String> = mutableListOf()
        var date = Date()
        date.date = date.date + 1
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        while (date.month != 7 || date.date != 15) {
            if (date.month >= 5/* && date.day != 6 && date.day != 0*/) {
                if (date.month == 5) {
                    if (date.date >= 19) {
                        dateList.add(dateFormat.format(date))
                        Log.d("DATE", dateFormat.format(date))
                    }
                } else {
                    dateList.add(dateFormat.format(date))
                    Log.d("DATE", dateFormat.format(date))
                }
            } else Log.d("DATE-NO", dateFormat.format(date))
            date.date = date.date + 1
        }

        val arrayAdapter1 = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, dateList)
        dateBox.adapter = arrayAdapter1

        continueBtn.setOnClickListener {
            if (fioBox.text.toString().trim().length != 0) {
                val disappearingAnim = AnimationUtils.loadAnimation(this, R.anim.disappearing)
                val appearingAnim = AnimationUtils.loadAnimation(this, R.anim.appearance)
                headerText.startAnimation(disappearingAnim)
                dateLayout.startAnimation(disappearingAnim)
                continueBtn.startAnimation(disappearingAnim)
                headerText.visibility = View.INVISIBLE
                dateLayout.visibility = View.INVISIBLE
                continueBtn.visibility = View.INVISIBLE

                headerContactsText.startAnimation(appearingAnim)
                contactsLayout.startAnimation(appearingAnim)
                backBtn.startAnimation(appearingAnim)
                signUpBtn.startAnimation(appearingAnim)
                headerContactsText.visibility = View.VISIBLE
                contactsLayout.visibility = View.VISIBLE
                backBtn.visibility = View.VISIBLE
                signUpBtn.visibility = View.VISIBLE
            }
            else Toast.makeText(this, "Заполните все поля.", Toast.LENGTH_LONG).show()
        }

        backBtn.setOnClickListener {
            val disappearingAnim = AnimationUtils.loadAnimation(this, R.anim.disappearing)
            val appearingAnim = AnimationUtils.loadAnimation(this, R.anim.appearance)
            headerContactsText.startAnimation(disappearingAnim)
            contactsLayout.startAnimation(disappearingAnim)
            backBtn.startAnimation(disappearingAnim)
            signUpBtn.startAnimation(disappearingAnim)
            headerContactsText.visibility = View.INVISIBLE
            contactsLayout.visibility = View.INVISIBLE
            backBtn.visibility = View.INVISIBLE
            signUpBtn.visibility = View.INVISIBLE

            headerText.startAnimation(appearingAnim)
            dateLayout.startAnimation(appearingAnim)
            continueBtn.startAnimation(appearingAnim)
            headerText.visibility = View.VISIBLE
            dateLayout.visibility = View.VISIBLE
            continueBtn.visibility = View.VISIBLE
        }
        var a = this
        dateBox.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                val request : Request = Request.Builder().url(URL).build()
                val dates: ArrayList<String> = ArrayList()
                okHttpClient.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("API ERROR", e.message!!)
                    }
                    override fun onResponse(call: Call, response: Response) {
                        val json = (JSONArray(response.body()!!.string()))
                        runOnUiThread {
                            for (i in 0..json.length() - 1) {
                                var spec = json.optJSONObject(i)
                                var dateAndTime = spec.getString("dateAndTime")
                                dates.add(dateAndTime)
                            }
                            var startTime = Time(9, 0, 0)
                            var timeList: MutableList<String> = mutableListOf()
                            Log.w("DATA", dates.size.toString())
                            while (startTime.hours != 18) {
                                var check = true
                                dates.forEach {
                                    if (it.substring(8, 10) + "." + it.substring(5, 7) + "." + it.substring(
                                            0,
                                            4
                                        ) == dateList[selectedItemPosition] && it.substring(11) == startTime.toString()
                                    ) check = false
                                }
                                if (check) {
                                    timeList.add(startTime.toString().substring(0,5))
                                }
                                startTime.minutes += 15
                            }
                            val arrayAdapter = ArrayAdapter(a, R.layout.support_simple_spinner_dropdown_item, timeList)
                            timeBox.adapter = arrayAdapter
                        }
                    }
                })
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        signUpBtn.setOnClickListener {
            if (phoneNumberBox.text.toString().trim().length != 0 && phoneParentNumberBox.text.toString().trim().length != 0 && emailBox.text.toString().trim().length != 0){

                val jsonData = "{\"fio\":\"${fioBox.text}\",\"email\":\"${emailBox.text}\",\"dateAndTime\":" +
                        "\"${dateBox.selectedItem.toString().substring(6, 10)}-${dateBox.selectedItem.toString().substring(3, 5)}-${dateBox.selectedItem.toString().substring(0, 2)}T${timeBox.selectedItem}:00\"," +
                        "\"contact_phone_number\":\"${phoneNumberBox.text}\",\"contact_phone_number_parent\":\"${phoneParentNumberBox.text}\"}"
                val body = RequestBody.create(MediaType.parse("application/json"), jsonData)
                val request : Request = Request.Builder().post(body).url(URL).build()
                okHttpClient.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Ваша заявка успешно отправлена!", Toast.LENGTH_LONG).show()
                            Log.e("DOC", response.message() + " " + "\"${dateBox.selectedItem.toString().substring(6, 10)}-${dateBox.selectedItem.toString().substring(3, 5)}-${dateBox.selectedItem.toString().substring(0, 2)}T${timeBox.selectedItem}:00\",")
                            finish()
                        }
                    }

                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Произошла ошибка.", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
            else Toast.makeText(this, "Заполните все поля.", Toast.LENGTH_LONG).show()
        }
    }

}