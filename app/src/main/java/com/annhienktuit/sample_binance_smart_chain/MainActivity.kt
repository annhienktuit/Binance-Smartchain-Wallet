package com.annhienktuit.sample_binance_smart_chain

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.centerprime.binance_smart_chain_sdk.BinanceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val edtWallet = findViewById<EditText>(R.id.edtWallet)
        val txtBalance = findViewById<TextView>(R.id.txtbalance)
        val binanceManager:BinanceManager = BinanceManager.getInstance()
        // binanceManager.init("https://data-seed-prebsc-1-s1.binance.org:8545"); // for test net
        binanceManager.init("https://bsc-dataseed1.binance.org:443")
        var walletAddress = "WALLET_ADDRESS"
        btnSubmit.setOnClickListener {
            walletAddress = edtWallet.text.toString()
            if (!walletAddress.startsWith("0x")) {
                walletAddress = "0x$walletAddress"
            }
            binanceManager.getBNBBalance(walletAddress, this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { balance: BigDecimal ->
                        txtBalance.text = "Your BSC balance is: $balance BNB "
                        println(balance)
                    }
                ) { error: Throwable ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

}

