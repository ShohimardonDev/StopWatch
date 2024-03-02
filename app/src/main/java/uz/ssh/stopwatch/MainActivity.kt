package uz.ssh.stopwatch

import android.app.Dialog
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import uz.ssh.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isRunning = false
    private var selectedMinutes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.clock.setOnClickListener { showTimerDialog() }
        binding.run.setOnClickListener { toggleTimer() }
    }

    private fun showTimerDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog)
        val numberPicker = dialog.findViewById<NumberPicker>(R.id.numberPicker)
        numberPicker.minValue = 0
        numberPicker.maxValue = 59  // Allow up to 59 minutes
        dialog.findViewById<Button>(R.id.set_timer).setOnClickListener {
            selectedMinutes = numberPicker.value
            binding.clocktime.text = "$selectedMinutes mins"
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun toggleTimer() {
        if (!isRunning) {
            startTimer()
        } else {
            pauseTimer()
        }
    }

    private fun startTimer() {
        isRunning = true
        val totalMilliseconds = selectedMinutes * 60 * 1000L
        binding.chronometer2.base = SystemClock.elapsedRealtime() + totalMilliseconds
        binding.chronometer2.format = "%s"  // Use default format
        binding.chronometer2.start()
    }

    private fun pauseTimer() {
        isRunning = false
        binding.chronometer2.stop()
    }
}
