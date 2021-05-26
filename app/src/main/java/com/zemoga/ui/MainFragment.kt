package com.zemoga.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.zemoga.R
import com.zemoga.databinding.FragmentMainBinding
import com.zemoga.domain.entity.PostEntity
import com.zemoga.ui.adapter.PostsAdapter
import com.zemoga.ui.util.observe
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment(), PostsAdapter.OnPostClickListener {

    private val viewModel: PostsVM by sharedViewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: PostsAdapter

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = requireView().findViewById(R.id.recycler)

        initUI()
        checkInternetConnection()
        initObservers()
    }

    override fun onOptionClicked(option: PostEntity) {
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
        viewModel.itemClicked(option.id!!)
    }

    private fun initUI() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.allPostsTabClicked()
                    1 -> viewModel.favoriteTabClicked()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.fabDelete.setOnClickListener {
            viewModel.deleteFabClicked()
        }

        binding.refreshIcon.setOnClickListener {
            viewModel.refreshIconClicked()
        }
    }

    private fun initObservers() {
        observe(viewModel.posts) {
            viewAdapter = PostsAdapter(this, it)
            recyclerView.apply {
                visibility = View.VISIBLE
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = viewAdapter
            }
        }

        observe(viewModel.arePostsDeleted) {
            if (it) binding.recycler.visibility = View.GONE else View.VISIBLE
        }

        observe(viewModel.isErrorThrown) { isErrorActive ->
            if (isErrorActive) {
                createDialog("System Error", "Something went wrong while fetching the data. Please, try again later.")
                recyclerView.visibility = View.GONE
            }
        }
    }

    private fun checkInternetConnection() {
        observe(viewModel.hasConnectivity()) { hasConnection ->
            if (!hasConnection) {
                createDialog("No internet connection", "You must be connected to the internet in order to use the app")
                recycler.visibility = View.GONE
            } else {
                viewModel.initLoading()
            }
        }
    }

    private fun createDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog,_ ->
            dialog.dismiss()
        }
        builder.show()
    }
}