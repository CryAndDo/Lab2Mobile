package com.example.lab2.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2.R
import com.example.lab2.database.HockeyDao
import com.example.lab2.databinding.FragmentHockeyListBinding
import com.example.lab2.model.Hockey
import com.example.lab2.viewmodel.HockeyListViewModel
import com.example.lab2.viewmodel.HockeyViewModel
import java.util.UUID

private const val TAG="HockeyListFragment"
private const val DIALOG="HockeyDialog"

class HockeyListFragment:Fragment(R.layout.fragment_hockey_list) {
    interface Callbacks{
        fun onHockeySelected(hockeyId:UUID)
    }
    private var callbacks:Callbacks?=null
    private lateinit var binding: FragmentHockeyListBinding
    private var adapter: HockeyAdapter?=HockeyAdapter(emptyList())
    private val hockeyListViewModel:HockeyListViewModel by lazy {
        ViewModelProvider(this)[HockeyListViewModel::class.java]
    }
    private val hockeyViewModel: HockeyViewModel by lazy {
        ViewModelProvider(this).get(HockeyViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHockeyListBinding.inflate(layoutInflater,
            container,false)
       // binding.studentRecyclerView.layoutManager=LinearLayoutManager(context)
        binding.hockeyRecyclerView.layoutManager=GridLayoutManager(context,2)

        hockeyListViewModel.hockeyListLiveData.observe(viewLifecycleOwner,
            Observer { hockeys->
                adapter=HockeyAdapter(hockeys)
                binding.hockeyRecyclerView.adapter=adapter
                binding.idAdd.setOnClickListener{
                    HockeyDialogFragment().apply {
                        show(this@HockeyListFragment.parentFragmentManager, DIALOG)
                    }
                }
                binding.AVG.setOnClickListener{

                    val totalAge = hockeyListViewModel.hockeyListLiveData.value?.sumOf { it.birthYear } ?: 0
                    val numberOfPlayers = hockeyListViewModel.hockeyListLiveData.value?.size ?: 0

                    val averageAge = if (numberOfPlayers > 0) totalAge / numberOfPlayers else 0

                    val averageAgeString = "Average Age: $averageAge"
                    val dialogFragment = InfoDialogFragment.newInstance(averageAgeString)
                    dialogFragment.show(this@HockeyListFragment.parentFragmentManager, DIALOG)
                }
                binding.B25.setOnClickListener {
                    val playersOver25 = hockeyListViewModel.hockeyListLiveData.value?.filter { it.birthYear > 25 }

                    val playersOver25Info = playersOver25?.joinToString("\n") {
                        "Name: ${it.name}, Age: ${it.birthYear}, Number of Games: ${it.number_of_games}, Number of Missed Pucks: ${it.number_of_missed_pucks}"
                    } ?: "No players over 25 years old."

                    val dialogFragment = InfoDialogFragment.newInstance("Players over 25 years old:\n$playersOver25Info")
                    dialogFragment.show(this@HockeyListFragment.parentFragmentManager, DIALOG)
                }
            })

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks=context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks=null
    }

    private inner class HockeyAdapter(var hockeys:List<Hockey>):
                RecyclerView.Adapter<HockeyAdapter.HockeyHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HockeyHolder {
            val view=layoutInflater.inflate(R.layout.list_item_hockey,parent,false)
            return HockeyHolder(view)
        }

        override fun getItemCount(): Int = hockeys.size

        override fun onBindViewHolder(holder: HockeyHolder, position: Int) {
            val hockey=hockeys[position]
            holder.bind(hockey)
        }
        private inner class HockeyHolder(view:View):RecyclerView.ViewHolder(view),
        View.OnClickListener{
            lateinit var hockey:Hockey
            val hockeyNameTextView:TextView=itemView.findViewById(R.id.hockey_name)
            val hockeyYear:TextView=itemView.findViewById(R.id.hockey_year)
            val hockeyNumberOfGames:TextView=itemView.findViewById(R.id.hockey_number_of_games)
            val hockeyNumberOfMissedPucks:TextView=itemView.findViewById(R.id.hockey_number_of_missed_pucks)

            init {
                itemView.setOnClickListener(this)
            }
            fun bind(hockey: Hockey){
                this.hockey=hockey
                hockeyNameTextView.text=hockey.name
                hockeyYear.text=hockey.birthYear.toString()
                hockeyNumberOfGames.text=hockey.number_of_games.toString()
                hockeyNumberOfMissedPucks.text=hockey.number_of_missed_pucks.toString()

            }

            override fun onClick(v: View?) {
                callbacks?.onHockeySelected(hockey.id)
            }
        }

    }
    companion object{
        fun newInstance():HockeyListFragment{
            return HockeyListFragment()
        }
    }
}