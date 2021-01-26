package com.nan.wechat.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nan.router_annotation.RoutePath
import com.nan.wechat.router.Router

@RoutePath("login/login")
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun testRoute(view: View) {
        val activityClass = Router.getInstance().getActivity("moments/moments")
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
