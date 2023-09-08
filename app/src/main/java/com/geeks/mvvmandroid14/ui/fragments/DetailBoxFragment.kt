package com.geeks.mvvmandroid14.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.geeks.mvvmandroid14.databinding.FragmentDetailBoxBinding

class DetailBoxFragment : Fragment() {

    private var _binding: FragmentDetailBoxBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<BoxViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBoxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        acceptData()
    }

    private fun acceptData() {
        viewModel.detailBoxLiveData.observe(viewLifecycleOwner) {
            it.success?.let { box ->
                Log.e("box", box.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}