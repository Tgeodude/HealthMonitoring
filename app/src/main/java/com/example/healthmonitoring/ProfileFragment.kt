package com.example.healthmonitoring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.healthmonitoring.databinding.FragmentProfileBinding

/**
 * @author Щеглов Виктор on 20.05.2024
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? =  null
    private val binding: FragmentProfileBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}