package com.pasha.mukminapp.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.pasha.mukminapp.R
import com.pasha.mukminapp.data.local.database.post.PostEntity
import com.pasha.mukminapp.data.remote.HijriahRequest
import com.pasha.mukminapp.data.remote.PostRequest
import com.pasha.mukminapp.data.retrofit.ApiClient
import com.pasha.mukminapp.fragment.BahasaFragment
import com.pasha.mukminapp.fragment.EnglishFragment
import com.pasha.mukminapp.ui.adapter.MainTabAdapter
import com.pasha.mukminapp.ui.helper.AppHelper
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_bahasa.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    var hijriah : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        updateUI()
        tablayoutListener();
        getHijriah()
    }

    private fun getHijriah() {
        ApiClient.instance.getHijriah(AppHelper.getYear(), AppHelper.getMonth(), AppHelper.getDate(), "ctoh", 1).enqueue(object : Callback<HijriahRequest> {

            override fun onFailure(call: Call<HijriahRequest>, t: Throwable) {
                Log.e("pasha", "export Err : $t")
                Toast.makeText(this@HomeActivity, "Err : $t", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<HijriahRequest>,
                response: Response<HijriahRequest>
            ) {
                if (!response.isSuccessful) {
                    if (response.code() == 401) {
                        Toast.makeText(this@HomeActivity, "Session end", Toast.LENGTH_LONG).show()
                    } else {
                        try {
                            val errBody = JSONObject(response.errorBody()!!.string())
                            val message = errBody.getString("message")
                            Toast.makeText(this@HomeActivity,message, Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Toast.makeText(this@HomeActivity, "Unknown error", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    hijriah = response.body()?.tanggal_hijriyah.toString()
                    updateUI()
                }
            }
        })
    }

    private fun updateUI() {
        tvDate.text = AppHelper.getDateNow()
        tvDateHijriah.text = hijriah
    }

    private fun tablayoutListener() {
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_bahasa_indonesia)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_english)))
//        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = MainTabAdapter(supportFragmentManager)
        adapter.addFragment(BahasaFragment(), getString(R.string.tab_bahasa_indonesia))
        adapter.addFragment(EnglishFragment(), getString(R.string.tab_english))
        viewPager?.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}