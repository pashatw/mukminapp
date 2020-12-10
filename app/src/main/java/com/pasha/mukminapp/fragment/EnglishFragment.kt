package com.pasha.mukminapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pasha.mukminapp.R
import com.pasha.mukminapp.data.local.database.post.PostEntity
import com.pasha.mukminapp.data.local.database.post.PostViewModel
import com.pasha.mukminapp.data.remote.PostRequest
import com.pasha.mukminapp.data.retrofit.ApiClient
import com.pasha.mukminapp.ui.activity.DetailActivity
import com.pasha.mukminapp.ui.adapter.PostAdapter
import kotlinx.android.synthetic.main.fragment_english.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnglishFragment : Fragment() {
    private lateinit var postVM: PostViewModel
    private var postAdapter = PostAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_english, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postVM = ViewModelProviders.of(this).get(PostViewModel::class.java)
        postVM.getAll("english").observe(viewLifecycleOwner, {
            postAdapter.setList(it)
        })
        setAdapter()
        getPost()

        itemsswipetorefresh.setOnRefreshListener {
            getPost()
        }
    }

    private fun setAdapter(){
        rvEnglish?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvEnglish?.adapter = postAdapter

        postAdapter.setOnItemClickCallback(object : PostAdapter.OnItemClickCallback {
            override fun onItemClick(data: PostEntity) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra("id", data.id)
                startActivity(intent)
            }
        })
    }

    private fun getPost() {
        ApiClient.instance.getPost(25).enqueue(object : Callback<List<PostRequest>> {
            override fun onFailure(call: Call<List<PostRequest>>, t: Throwable) {

                itemsswipetorefresh.isRefreshing = false
                Log.e("pasha", "export Err : $t")
                Toast.makeText(context, "Err : $t", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<PostRequest>>,
                response: Response<List<PostRequest>>
            ) {

                itemsswipetorefresh.isRefreshing = false
                if (!response.isSuccessful) {
                    if (response.code() == 401) {
                        Toast.makeText(context, "Session end", Toast.LENGTH_LONG).show()
                    } else {
                        try {
                            val errBody = JSONObject(response.errorBody()!!.string())
                            val message = errBody.getString("message")
                            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Unknown error", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    val thread = object : Thread() {
                        override fun run() {
                            response.body()?.forEach {
                                postVM.insert(
                                    PostEntity(
                                        it?.id ?: "",
                                        it?.slug ?: "",
                                        it?.title?.rendered ?: "",
                                        it?.thumnail ?: "",
                                        "english",
                                        "Taharah",
                                        it?.content?.rendered?: "",
                                    )
                                )
                            }
                        }
                    }
                    thread.start()
                }

            }
        })
    }
}