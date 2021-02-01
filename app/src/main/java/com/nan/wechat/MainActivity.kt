package com.nan.wechat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nan.livedatabus.LiveDataBus
import com.nan.router_annotation.RoutePath
import com.nan.wechat.router.Router

@RoutePath("main/main")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LiveDataBus.with("key", String::class.java).observe(this) {
            Toast.makeText(this, "message: $it", Toast.LENGTH_SHORT).show()
        }
    }

    fun testRoute(view: View) {
        val activityClass = Router.getInstance().getActivity("login/login")
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}