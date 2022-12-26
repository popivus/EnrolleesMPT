package com.example.enrolleesmpt

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_courses.*
import okhttp3.*
import java.io.IOException

class CoursesActivity : AppCompatActivity() {

    var okHttpClient: OkHttpClient = OkHttpClient()
    private val URL = "http://${getIp()}:5000/api/CoursesNotifications"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)
        val coursesList: List<String> = listOf("Математика", "Русский язык", "Информатика")
        val arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, coursesList)
        coursesBox.adapter = arrayAdapter

        continueBtn.setOnClickListener {
            if (fioBox.text.toString().trim().length != 0) {
                val disappearingAnim = AnimationUtils.loadAnimation(this, R.anim.disappearing)
                val appearingAnim = AnimationUtils.loadAnimation(this, R.anim.appearance)
                headerText.startAnimation(disappearingAnim)
                courseLayout.startAnimation(disappearingAnim)
                continueBtn.startAnimation(disappearingAnim)
                headerText.visibility = View.INVISIBLE
                courseLayout.visibility = View.INVISIBLE
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
            courseLayout.startAnimation(appearingAnim)
            continueBtn.startAnimation(appearingAnim)
            headerText.visibility = View.VISIBLE
            courseLayout.visibility = View.VISIBLE
            continueBtn.visibility = View.VISIBLE
        }

        signUpBtn.setOnClickListener {
            if (phoneNumberBox.text.toString().trim().length != 0 && phoneParentNumberBox.text.toString().trim().length != 0 && emailBox.text.toString().trim().length != 0){
                val jsonData = "{\"fio\":\"${fioBox.text}\",\"email\":\"${emailBox.text}\",\"courses_id\":${coursesBox.selectedItemPosition + 1}," +
                        "\"contact_phone_number\":\"${phoneNumberBox.text}\",\"contact_phone_number_parent\":\"${phoneParentNumberBox.text}\"}"
                val body = RequestBody.create(MediaType.parse("application/json"), jsonData)
                val request : Request = Request.Builder().post(body).url(URL).build()
                okHttpClient.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Ваша заявка успешно отправлена!", Toast.LENGTH_LONG).show()
                            Log.e("DOC", response.message())
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