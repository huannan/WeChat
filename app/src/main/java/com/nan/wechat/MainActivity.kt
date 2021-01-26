package com.nan.wechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nan.router_annotation.RoutePath
import com.nan.wechat.router.Router

@RoutePath("main/main")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun testRoute(view: View) {
        val activityClass = Router.getInstance().getActivity("login/login")
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}