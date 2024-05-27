package com.example.lab2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab2.databinding.HockeyDialogBinding
import com.example.lab2.model.Hockey
import com.example.lab2.viewmodel.HockeyViewModel

class HockeyDialogFragment:DialogFragment() {
    private lateinit var binding: HockeyDialogBinding
    private lateinit var hockey:Hockey
    private val hockeyViewModel: HockeyViewModel by lazy {
        ViewModelProvider(this).get(HockeyViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= HockeyDialogBinding.inflate(inflater,container,false)
        binding.btOk.setOnClickListener {
            hockey= Hockey()
            hockey.name=binding.etFIO.text.toString()
            hockey.birthYear=binding.etAge.text.toString().toInt()
            hockey.number_of_games=binding.etNumberOfGames.text.toString().toInt()
            hockey.number_of_missed_pucks=binding.etNumberOfMissedPucks.text.toString().toInt()
            hockeyViewModel.saveHockey(hockey)
            dismiss()
        }
        return binding.root
    }
}