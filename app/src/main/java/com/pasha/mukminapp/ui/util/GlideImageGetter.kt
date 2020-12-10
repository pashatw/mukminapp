package com.pasha.mukminapp.ui.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.os.AsyncTask
import android.os.Build
import android.text.Html
import android.text.Html.ImageGetter
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import com.pasha.mukminapp.R
import kotlinx.android.synthetic.main.activity_detail_post.view.*
import java.io.InputStream
import java.net.URL


class GlideImageGetter(context: Context, view: TextView) : ImageGetter {
    private val mContext: Context
    private val mHtmlView: View
    override fun getDrawable(source: String): Drawable {
        val drawable = LevelListDrawable()
        val empty: Drawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher)
        drawable.addLevel(0, 0, empty)
        drawable.setBounds(0, 0, empty.intrinsicWidth, empty.intrinsicHeight)
        HtmlImageLoad().execute(source, drawable)
        return drawable
    }

    internal inner class HtmlImageLoad :
        AsyncTask<Any?, Void?, Bitmap?>() {
        private var mDrawable: LevelListDrawable? = null

        override fun onPostExecute(bitmap: Bitmap?) {
            if (bitmap != null) {
                val drawable = BitmapDrawable(bitmap)
                mDrawable!!.addLevel(1, 1, drawable)
                mDrawable!!.setBounds(0, 0, bitmap.width, bitmap.height)
                mDrawable!!.level = 1
                /*htmlView.invalidate();
                if (htmlView.getParent() != null) {
                    htmlView.getParent().requestLayout();
                }*/if (mHtmlView is TextView) {
                    val htmlView = mHtmlView as TextView
                    val textChar = htmlView.text
                    htmlView.text = textChar
                } else if (mHtmlView is RadioButton) {
                    val htmlView = mHtmlView as RadioButton
                    val textChar = htmlView.text
                    htmlView.text = textChar
                } else if (mHtmlView is Button) {
                    val htmlView: Button = mHtmlView as Button
                    val textChar: CharSequence = htmlView.getText()
                    htmlView.setText(textChar)
                } else if (mHtmlView is CheckBox) {
                    val htmlView = mHtmlView as CheckBox
                    val textChar = htmlView.text
                    htmlView.text = textChar
                }
            }
        }

        override fun doInBackground(vararg params: Any?): Bitmap? {
            val source = params[0] as String
            mDrawable = params[1] as LevelListDrawable
            try {
                val `is`: InputStream = URL(source).openStream()
                return BitmapFactory.decodeStream(`is`)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }

    companion object {
        fun loadUsingHtml(view: View?, value: String?) {
            if (view != null) {
                if (view is TextView) {
                    val valueView = view as TextView
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        valueView.text =
                            Html.fromHtml(
                                value,
                                Html.FROM_HTML_MODE_COMPACT,
                                GlideImageGetter(view.getContext(), valueView),
                                null
                            )
                    } else {
                        valueView.text =
                            Html.fromHtml(
                                value,
                                GlideImageGetter(view.getContext(), valueView),
                                null
                            )
                    }
                } else if (view is RadioButton) {
                    val valueView = view as RadioButton
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        valueView.text =
                            Html.fromHtml(
                                value,
                                Html.FROM_HTML_MODE_COMPACT,
                                GlideImageGetter(view.getContext(), valueView),
                                null
                            )
                    } else {
                        valueView.text =
                            Html.fromHtml(
                                value,
                                GlideImageGetter(view.getContext(), valueView),
                                null
                            )
                    }
                }
            }
        }
    }

    init {
        mContext = context
        mHtmlView = view
    }
}