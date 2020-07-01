package com.rt.mylibrary

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.rt.mylibrary.basebean.BaseResp
import com.rt.mylibrary.utils.Utils

class DealView {
    companion object {
        fun dealData(mContext: Context, bean: BaseResp, rootView: ViewGroup) {
            Glide.with(mContext)
                .load("http://e.hiphotos.baidu.com/zhidao/pic/item/b64543a98226cffc7a951157b8014a90f703ea9c.jpg")
                .into(object : CustomViewTarget<ViewGroup, Drawable>(rootView) {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        rootView.background = resource
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                        rootView.background = placeholder
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        rootView.background = errorDrawable
                    }

                })


            val list = bean.pages
            val layersList = list[0].layers
            Utils.needScale(
                mContext,
                list[0].style.width,
                list[0].style.height
            )
            for (i in 0 until layersList.size) {
                Log.i("Layers", "${layersList[i].type}")
                when (layersList[i].type) {
                    "text" -> {
                        rootView.addView(ViewCreate.createTextView(mContext, layersList[i]))
                    }
                    "video" -> {
                        ViewCreate.createVLC(mContext, layersList[i], rootView)
                    }
                    "qrcode" -> {
                        rootView.addView(ViewCreate.createQRcode(mContext, layersList[i]))
                    }
                    "img" -> {
                        rootView.addView(ViewCreate.createImageView(mContext, layersList[i]))
                    }
                    "weather" -> {
                        rootView.addView(ViewCreate.createWeatherView(mContext, layersList[i]))
                    }

                    "run" -> {
                        rootView.addView(ViewCreate.createRunText(mContext, layersList[i]))
                    }

                    "btn" -> {

                    }
                    "music" -> {
                        ViewCreate.createMusic(mContext)

                    }
                    "time" -> {
                        rootView.addView(ViewCreate.createTextClock(mContext, layersList[i]))
                    }


                }
            }
        }
    }
}