package com.example.healthmonitoring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.healthmonitoring.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

/**
 * @author Щеглов Виктор on 20.05.2024
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.profile
        profileViewModel.updateProfile(Profile())
        val caloriesBurned = 410
        val dailyGoal = 1480
        val progress = (caloriesBurned.toFloat() / dailyGoal * 100).toInt()
        binding.cardCallories.pbCalories.setProgress(progress)

        val sleepData = listOf(
            SleepSegment(1.2f, SleepStage.REM),
            SleepSegment(0.5f, SleepStage.LIGHT),
            SleepSegment(1.0f, SleepStage.DEEP),
            SleepSegment(0.1f, SleepStage.AWAKE),
            SleepSegment(1.0f, SleepStage.REM),
            SleepSegment(0.5f, SleepStage.LIGHT),
            SleepSegment(1.0f, SleepStage.DEEP),
            SleepSegment(0.1f, SleepStage.AWAKE),
            SleepSegment(0.5f, SleepStage.REM),
            SleepSegment(0.3f, SleepStage.LIGHT),
            SleepSegment(0.8f, SleepStage.DEEP)
        )

        // Установка данных в SleepChartView
        binding.cardSleep.sleepChartView.setSegments(sleepData)

        // Вычисление общего времени сна
        val totalSleepHours = sleepData.sumByDouble { it.duration.toDouble() }
        binding.cardSleep.tvTotalSleep.text = String.format("%.2f часов", totalSleepHours)

        // resultLauncher = registerForActivityResult(
        //     healthConnectManager.requestPermissionsActivityContract()
        // ) {
        //     lifecycleScope.launch {
        //         tryWithPermissionsCheck()
        //     }
        // }
        //
        // requestPermissionsButton.setOnClickListener {
        //     resultLauncher.launch(
        //         permissions
        //     )
        // }
        // getSteps.setOnClickListener {
        //     readSteps()
        // }
        // healthConnectManager.checkAvailability()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}