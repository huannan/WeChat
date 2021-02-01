package com.nan.wechat.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nan.livedatabus.LiveDataBus
import com.nan.router_annotation.RoutePath

@RoutePath("login/login")
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun testEvent(view: View) {
        LiveDataBus.post("key", "登录成功")
    }
}
