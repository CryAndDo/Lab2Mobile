package com.example.lab2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lab2.R
import com.example.lab2.databinding.FragmentHockeyBinding
import com.example.lab2.model.Hockey
import com.example.lab2.viewmodel.HockeyViewModel
import java.util.UUID
private const val ARG_HOCKEY_ID="hockey_id"
private const val TAG="HockeyFragment"
class HockeyFragment : Fragment(R.layout.fragment_hockey) {

    companion object {
        fun newInstance(hockeyId:UUID) :HockeyFragment {
            val args=Bundle().apply {
                putSerializable(ARG_HOCKEY_ID,hockeyId)
            }
            return HockeyFragment().apply {
                arguments=args
            }
        }
    }
    private lateinit var binding: FragmentHockeyBinding
    private lateinit var hockey:Hockey
    private val hockeyViewModel: HockeyViewModel by lazy {
        ViewModelProvider(this)[HockeyViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hockey=Hockey()
        val hockeyId:UUID=arguments?.getSerializable(ARG_HOCKEY_ID) as UUID
        hockeyViewModel.loadHockey(hockeyId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHockeyBinding.inflate(layoutInflater,container,false)
        hockeyViewModel.hockeyLiveData.observe(viewLifecycleOwner,
            Observer {
                    hockey->
                hockey?.let {
                    this.hockey=hockey
                    binding.tvFIO.text=hockey.name
                    binding.tvBirthDate.text=hockey.birthYear.toString()
                    binding.tvNumberOfGames.text=hockey.number_of_games.toString()
                    binding.tvNumberOfMissedPucks.text=hockey.number_of_missed_pucks.toString()
                }
            })
        return binding.root
    }

}