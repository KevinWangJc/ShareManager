package com.example.share

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.share.login.LoginActivity
import com.example.share.share.ShareActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Description:
 * Main Page
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/3/14
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        btn_login.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        btn_share.setOnClickListener {
            startActivity(Intent(this@MainActivity, ShareActivity::class.java))
        }
    }
}
