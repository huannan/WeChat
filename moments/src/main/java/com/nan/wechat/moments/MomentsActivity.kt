package com.nan.wechat.moments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nan.router_annotation.RoutePath

@RoutePath("moments/moments")
class MomentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moments)
    }
}
