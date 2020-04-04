package com.example.fragments

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.addOnBackStackChangedListener {
            textViewFragmentCount.setText("Fragment count in back stack: "+supportFragmentManager.backStackEntryCount)
            val backstackentryMessage = StringBuilder("Current status of fragment transaction back stack: " + supportFragmentManager.backStackEntryCount+"\n")
            for(index in supportFragmentManager.backStackEntryCount-1 downTo 0){

            }
        }
        val buttonAddFragment = findViewById<Button>(R.id.buttonAddFragment);
        buttonAddFragment.setOnClickListener {
            addFragment()
        }
    }

    private fun addFragment () : Unit {
        val fragment : Fragment
        when(supportFragmentManager.backStackEntryCount){
            0 ->  fragment = SampleFragment()
            1 ->  fragment = FragmentTwo()
            2 ->  fragment = FragmentThree()
            else -> fragment = SampleFragment()
        }
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.fragmentContainer,fragment)
//            .addToBackStack("fragmentStack1")
            .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer);
        if (fragment!=null){
            val ft = supportFragmentManager.beginTransaction()
            ft.remove(fragment)
                .addToBackStack(null)
                .commit()
        }else{
            super.onBackPressed()
        }
    }
}
