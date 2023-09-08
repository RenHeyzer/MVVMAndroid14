package com.geeks.mvvmandroid14.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.geeks.mvvmandroid14.R
import com.geeks.mvvmandroid14.databinding.FragmentMainBinding
import com.geeks.mvvmandroid14.models.Box
import com.geeks.mvvmandroid14.ui.adapters.BoxesAdapter

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<BoxViewModel>()
    private val boxesAdapter = BoxesAdapter(this::onItemClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        subscribeToBoxes()
    }

    private fun initialize() {
        binding.rvBoxes.adapter = boxesAdapter
    }

    private fun subscribeToBoxes() {
        viewModel.boxLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it.isLoading.toString(), Toast.LENGTH_SHORT).show()
            it.error?.let { message ->
                Log.e("error", message)
            }
            it.success?.let { boxes ->
                boxesAdapter.setBoxes(boxes)
            }
        }
    }

    private fun onItemClick(box: Box) {
        Handler().postDelayed(
            {
                viewModel.handOverTheBox(box)
                Toast.makeText(requireContext(), "Data transferred successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_mainFragment_to_detailBoxFragment)
            },
            2000
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}