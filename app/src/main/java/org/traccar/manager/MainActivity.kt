/*
 * Copyright 2016 - 2022 Anton Tananaev (anton@traccar.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("DEPRECATION")
package app.datatrak.manager

import android.os.Build
import android.os.Bundle
import android.webkit.WebViewFragment
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            initContent()
        }
    }

    private fun initContent() {
        if (PreferenceManager.getDefaultSharedPreferences(this).contains(PREFERENCE_URL)) {
            fragmentManager.beginTransaction().add(android.R.id.content, MainFragment()).commit()
        } else {
            fragmentManager.beginTransaction().add(android.R.id.content, StartFragment()).commit()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val fragment = fragmentManager.findFragmentById(android.R.id.content)
        fragment?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onBackPressed() {
        val fragment = fragmentManager.findFragmentById(android.R.id.content) as? WebViewFragment
        if (fragment?.webView?.canGoBack() == true) {
            fragment.webView.goBack();
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val PREFERENCE_URL = "url"
    }
}
