package ro.pub.acs.pollutionchecker.history

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ro.pub.acs.pollutionchecker.R
import ro.pub.acs.pollutionchecker.database.HistoryDatabase
import ro.pub.acs.pollutionchecker.databinding.FragmentHistoryListBinding


/**
 * A fragment representing a list of Items.
 */
class HistoryFragment : Fragment() {

    lateinit var historyViewModel: HistoryViewModel

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clearItem) {
            historyViewModel.onClear()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val binding: FragmentHistoryListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_history_list, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = HistoryDatabase.getInstance(application).historyDatabaseDao
        val viewModelFactory = HistoryViewModelFactory(dataSource)

        historyViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(HistoryViewModel::class.java)

        binding.historyViewModel = historyViewModel

        val adapter = HistoryAdapter(historyViewModel)
        binding.historyList.adapter = adapter

        historyViewModel.history.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.history = it
            }
        })

        binding.lifecycleOwner = this

        historyViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                historyViewModel.doneShowingSnackbar()
            }
        })
        return binding.root
    }
}