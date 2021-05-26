package com.zemoga.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zemoga.R
import com.zemoga.databinding.FragmentDetailBinding
import com.zemoga.ui.adapter.CommentsAdapter
import com.zemoga.ui.util.observe
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailFragment : Fragment() {

    private val viewModel: PostsVM by sharedViewModel()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: CommentsAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            viewModel = this@DetailFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        binding.arrowBackIcon.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        binding.favoriteIcon.setOnClickListener {
            viewModel.favoriteClicked()
            if (viewModel.post.value!!.isFavorite) binding.favoriteIcon.setImageResource(R.drawable.ic_star)
            else binding.favoriteIcon.setImageResource(R.drawable.ic_star_border)
        }

        viewAdapter = CommentsAdapter(viewModel.relatedComments.value!!)
        recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_comments).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewAdapter
        }
    }
}