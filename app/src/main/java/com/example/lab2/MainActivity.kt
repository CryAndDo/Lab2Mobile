package com.example.lab2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lab2.databinding.ActivityMainBinding
import com.example.lab2.view.HockeyFragment
import com.example.lab2.view.HockeyListFragment
import java.util.UUID

private const val TAG="MainActivity"
class MainActivity : AppCompatActivity(),HockeyListFragment.Callbacks {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment=supportFragmentManager.
                                findFragmentById(R.id.fragment_container)
        if(currentFragment==null){
            val fragment=HockeyListFragment.newInstance()
            supportFragmentManager.beginTransaction().
            add(R.id.fragment_container,fragment).commit()
        }
    }

    override fun onHockeySelected(hockeytId: UUID) {
        val fragment=HockeyFragment.newInstance(hockeytId)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    fragment).addToBackStack(null).commit()
    }
}