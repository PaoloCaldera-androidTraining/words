package com.example.wordsapp

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.adapters.LetterAdapter
import com.example.wordsapp.data.SettingsDataStore
import com.example.wordsapp.databinding.FragmentLetterListBinding
import kotlinx.coroutines.launch


class LetterListFragment : Fragment() {

    private var _binding: FragmentLetterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingsDataStore: SettingsDataStore

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
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        // Initialize the preferences DataStore
        settingsDataStore = SettingsDataStore(requireContext())

        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        return binding.root
    }


    // onViewCreated() is where to bind properties to views
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsDataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) {
            // Once updated the preferences datastore, update also the fragment flag variable
            isLinearLayoutManager = it
            setLayout()
            activity?.invalidateOptionsMenu()
        }

        recyclerView = binding.recyclerView
        recyclerView.adapter = LetterAdapter()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Method to inflate the layout of the option menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
        setIcon(menu.findItem(R.id.item_switch_layout))
    }

    /*  Method to describe the action to be performed when the menu item is clicked
        The clicked item of the menu is passed as argument of the function.
        Use the when() clause to perform an action, according to the specific item passed
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_switch_layout -> {
                /*  Lifecycle aware components (activities, fragments) provide coroutine support
                    for logical scopes: a LifecycleScope is defined for each Lifecycle object. Any
                    coroutine launched in the lifecycleScope is removed when the Lifecycle object
                    is destroyed
                 */
                lifecycleScope.launch {
                    // When the different layout is selected, update the preferences DataStore
                    settingsDataStore.saveLayoutToPreferencesStore(!isLinearLayoutManager,
                        requireContext())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Set the icon of the MainActivity menu according to the boolean flag value
    private fun setIcon(item: MenuItem) {
        when (isLinearLayoutManager) {
            true -> item.icon = ContextCompat.getDrawable(requireContext(),
                R.drawable.ic_linear_layout)
            false -> item.icon = ContextCompat.getDrawable(requireContext(),
                R.drawable.ic_grid_layout)
        }
    }

    // Set the layout of the MainActivity according to the boolean flag value
    private fun setLayout(){
        when (isLinearLayoutManager) {
            true -> recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            false -> recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

}