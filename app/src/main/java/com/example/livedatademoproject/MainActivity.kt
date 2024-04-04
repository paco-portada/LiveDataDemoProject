package com.example.livedatademoproject

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.livedatademoproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        /** Observing seconds() LiveData object from ViewModel */
        viewModel.seconds().observe(this, Observer {
            binding.numberTxt.text = it.toString()
        })
        /** Observing finished LiveData object from ViewModel */
        viewModel.finished.observe(this, Observer {
            if(it){
                Toast.makeText(this, "Finished!", Toast.LENGTH_SHORT).show()
            }
        })

        binding.startBtn.setOnClickListener {
            if (binding.numberInput.text.isEmpty() || binding.numberInput.text.length < 4){
                Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.timerValue.value = binding.numberInput.text.toString().toLong()
                viewModel.startTimer()
            }

        }
        binding.stopBtn.setOnClickListener {
            binding.numberTxt.text = "0"
            viewModel.stopTimer()
        }

    }
}