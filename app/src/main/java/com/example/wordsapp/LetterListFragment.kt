package com.example.wordsapp

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding


class LetterListFragment : Fragment() {

    private var _binding: FragmentLetterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private var isLinearLayoutManager = true


    // onCreate(), for Fragments, does only the required actions before creating the view
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // specifies whether the fragment is accompanied by an option menu in the app bar
        setHasOptionsMenu(true)
    }


    /*  onCreateView() is responsible for inflating the fragment layout
        $inflater: instance of LayoutInflater used to inflate the layout
        $container: parent view group where the Fragment is "attached" to
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        return binding.root
    }


    // onViewCreated() is where to bind properties to views
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Method to inflate the layout of the option menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
    }

    /*  Method to describe the action to be performed when the menu item is clicked
        The clicked item of the menu is passed as argument of the function.
        Use the when() clause to perform an action, according to the specific item passed
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_switch_layout -> {
                setLayout(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Set the layout of the MainActivity according to the app bar button indication
    private fun setLayout(item: MenuItem){
        when (isLinearLayoutManager) {
            true -> {
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
                item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_linear_layout)
            }
            false -> {
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_grid_layout)
            }
        }
        isLinearLayoutManager = !isLinearLayoutManager
    }

}