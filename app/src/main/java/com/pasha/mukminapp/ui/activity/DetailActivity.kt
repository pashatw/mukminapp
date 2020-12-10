package com.pasha.mukminapp.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.pasha.mukminapp.R
import com.pasha.mukminapp.data.local.database.post.PostEntity
import com.pasha.mukminapp.data.local.database.post.PostViewModel
import com.pasha.mukminapp.fragment.BahasaFragment
import com.pasha.mukminapp.fragment.EnglishFragment
import com.pasha.mukminapp.ui.adapter.MainTabAdapter
import com.pasha.mukminapp.ui.util.GlideImageGetter
import kotlinx.android.synthetic.main.activity_detail_post.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_post.view.*

class DetailActivity : AppCompatActivity() {
    private lateinit var postVM: PostViewModel
    private var id = ""
    private lateinit var postEntity : PostEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)

        btnListener()
        postVM = ViewModelProviders.of(this).get(PostViewModel::class.java)
        if (intent.hasExtra("id")) {
            id = intent.getStringExtra("id").toString()
            postVM.getById(id).observe(this, {
                postEntity = it

                updatePage()
            })
        }
    }

    private fun btnListener() {
        imgBack.setOnClickListener {
            finish()
        }
    }

    private fun updatePage() {

        val glideImageGetter = GlideImageGetter(this, tvContent)
        Glide.with(this).load(postEntity.thumnail).into(imgPost)
        tvTitle.text = postEntity.title
        tvCategory.text = postEntity.category.ifEmpty { "-" }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvContent.text = Html.fromHtml(postEntity.content, Html.FROM_HTML_MODE_LEGACY, glideImageGetter, null)
        } else {
            tvContent.text = Html.fromHtml(postEntity.content, glideImageGetter, null)
        }
    }

}