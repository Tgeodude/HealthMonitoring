package com.example.healthmonitoring

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.lifecycleScope
import com.example.healthmonitoring.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var stepsTextView: TextView
    private lateinit var healthConnectManager: HealthConnectManager
    private val permissions = setOf(
        HealthPermission.getReadPermission(StepsRecord::class),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            lateinit var selectedFragment: Fragment
            when (item.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_stats -> selectedFragment = StatsFragment()
                R.id.nav_profile -> selectedFragment = ProfileFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            true
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        healthConnectManager = HealthConnectManager(this)
    }

    private fun readSteps() {
        lifecycleScope.launch {
            val startTime = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).toInstant()
            val endTime = Instant.now()
            if (endTime.isAfter(startTime)) {
                val steps = healthConnectManager.readStepsRecord(startTime, endTime)
                stepsTextView.text = "Шаги за сегодня: ${steps.count()}"
            } else {
                stepsTextView.text = "Некоректное время"
            }
        }
    }

    private suspend fun tryWithPermissionsCheck() {
        healthConnectManager.hasAllPermissions(permissions)
    }
}