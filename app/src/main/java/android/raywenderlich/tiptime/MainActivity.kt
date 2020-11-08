package android.raywenderlich.tiptime

import android.os.Bundle
import android.raywenderlich.tiptime.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import java.text.NumberFormat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener{ calculateTip() }
        MobileAds.initialize(this, "ca-app-pub-4319726139601549~7644958677")

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 200000.0/1000000.0
            R.id.option_eighteen_percent -> 134250.0/1000000.0
            else -> 108000.0/1000000.0
        }
        var tip = tipPercentage * cost
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

}