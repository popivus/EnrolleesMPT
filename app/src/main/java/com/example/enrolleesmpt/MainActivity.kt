package com.example.enrolleesmpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val docFragment = DocFragment()
        val specFragment = SpecFragment()
        val coursesFragment = CoursesFragment()
        makeCurrentFragment(specFragment)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.specFragment -> makeCurrentFragment(specFragment)
                R.id.docFragment -> makeCurrentFragment(docFragment)
                R.id.coursesFragment -> makeCurrentFragment(coursesFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_replacer, fragment)
            commit()
        }
}