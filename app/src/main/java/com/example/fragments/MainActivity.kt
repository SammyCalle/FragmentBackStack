package com.example.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val  COMMON_TAG:String = "CombinedLifeCycle"
    private val ACTIVITY_NAME = MainActivity::class.java.simpleName
    private val TAG = ACTIVITY_NAME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        val fm = supportFragmentManager
        textViewFragmentCount.setText("Fragment count in back stack: "+fm.getBackStackEntryCount());
        supportFragmentManager.addOnBackStackChangedListener {
            textViewFragmentCount.setText("Fragment count in back stack: "+fm.backStackEntryCount)
            val backstackentryMessage = StringBuilder("Current status of fragment transaction back stack: "
                    + fm.backStackEntryCount+"\n")
            for(index in supportFragmentManager.backStackEntryCount-1 downTo 0){
                val backStackEntry =  fm.getBackStackEntryAt(index)
                backstackentryMessage.append(backStackEntry.getName()+"\n")
            }
            Log.i(TAG,backstackentryMessage.toString())
        }
        Log.i(COMMON_TAG,"Initial BackStackEntryCount: "+fm.getBackStackEntryCount());

        buttonAddFragmentOne.setOnClickListener(this);
        buttonPopFragment.setOnClickListener(this);
        buttonRemoveFragment.setOnClickListener(this);

    }

    private fun addFragment () : Unit {
        val fragment : Fragment?
        if (supportFragmentManager.backStackEntryCount > 0){
            when(supportFragmentManager.backStackEntryCount){
                0 ->  loadFragmentOne()
                1 ->  loadFragmentTwo()
                2 ->  loadFragmentThree()
                else -> loadFragmentOne()
            }
        }else{
            fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            if(fragment is SampleFragment){
                loadFragmentTwo();
            }else if(fragment is FragmentTwo){
                loadFragmentThree();
            }else if(fragment is FragmentThree){
                loadFragmentOne();
            }else{
                loadFragmentOne();
            }
        }
    }
    private fun loadFragmentOne() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment: Fragment
        /*if(fragment!=null){
            fragmentTransaction.remove(fragment);
        }*/
        fragment = SampleFragment()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, "demofragment")
        fragmentTransaction.addToBackStack("Add $fragment")
        fragmentTransaction.commit()
    }

    private fun loadFragmentTwo() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment: Fragment
        /*if(fragment!=null){
            fragmentTransaction.remove(fragment);
        }*/
        fragment = FragmentTwo()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, "demofragment")
        fragmentTransaction.addToBackStack("Add $fragment")
        fragmentTransaction.commit()
    }

    private fun loadFragmentThree() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment: Fragment
        /*if(fragment!=null){
            fragmentTransaction.remove(fragment);
        }*/fragment = FragmentThree()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, "demofragment")
        fragmentTransaction.addToBackStack("Add $fragment")
        fragmentTransaction.commit()
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

    override fun onClick(v: View) {
        when (v) {
            buttonAddFragmentOne -> addFragment()
//            buttonPopFragment-> supportFragmentManager.popBackStack()
//            buttonPopFragment-> supportFragmentManager.popBackStack(0,0)
            buttonPopFragment -> supportFragmentManager.popBackStack(
                "Add SampleFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            buttonRemoveFragment -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                if (fragment != null) {
                    fragmentTransaction.remove(fragment)
                    fragmentTransaction.addToBackStack("Remove $fragment")
                    fragmentTransaction.commit()
                } else {
                    Toast.makeText(this, "No Fragment to remove", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                
            }
        }
    }
}
