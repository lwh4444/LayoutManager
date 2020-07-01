package com.rt.mylibrary

//import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard
import android.content.Context
import android.media.MediaPlayer
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.gongwen.marqueen.SimpleMF
import com.gongwen.marqueen.SimpleMarqueeView
import com.google.gson.Gson
import com.rt.layoutlibrary.view.WeatherView
import com.rt.layoutlibrary.viewbean.*
import com.rt.mylibrary.basebean.SimpleResp
import com.rt.mylibrary.fragment.VlcFragment
import com.rt.mylibrary.utils.Utils
import com.rt.mylibrary.viewbean.*
import java.lang.Exception
import java.util.*
import java.util.concurrent.ThreadPoolExecutor

class ViewCreate {


    companion object {
        private lateinit var myThreadPool: ThreadPoolExecutor
        fun init(myThreadPoolExecutor: ThreadPoolExecutor) {
            myThreadPool = myThreadPoolExecutor
        }

        /*文本*/
        fun createTextView(mContext: Context, bean: SimpleResp<Any>): TextView {
            val textBean = Gson().fromJson<TextBean>(Gson().toJson(bean.data), TextBean::class.java)
            val mTextView = TextView(mContext)
            mTextView.text = textBean.text
            textBean.style.run {
//     todo           mTextView.setTextColor(Color.parseColor(color))
                if (textAlign == "left") {
                    mTextView.gravity = Gravity.START
                } else if (textAlign == "center") {
                    mTextView.gravity = Gravity.CENTER
                } else if (textAlign == "right") {
                    mTextView.gravity = Gravity.END
                }

                mTextView.textSize = fontSize
                mTextView.setLineSpacing(lineHeight, 0f)
                mTextView.letterSpacing = letterSpacing.toFloat()

            }

            mTextView.run {
                layoutParams =
                    ViewGroup.LayoutParams(bean.style.width.toInt(), bean.style.height.toInt())
                x = bean.style.left.toFloat()
                y = bean.style.top.toFloat()
            }

            return mTextView
        }

        /*时间*/
        fun createTextClock(mContext: Context, bean: SimpleResp<Any>): TextClock {
            val timeBean = Gson().fromJson<TimeBean>(Gson().toJson(bean.data), TimeBean::class.java)
            val mTextClock = TextClock(mContext)
            mTextClock.run {
//        todo        setTextColor(Color.parseColor(timeBean.style.color))
                textSize = timeBean.style.fontSize.toFloat()
                format24Hour = timeBean.type
                layoutParams =
                    ViewGroup.LayoutParams(bean.style.width.toInt(), bean.style.height.toInt())
                x = bean.style.left.toFloat()
                y = bean.style.top.toFloat()
            }
            return mTextClock
        }

        /*图片*/
        fun createImageView(mContext: Context, bean: SimpleResp<Any>): ImageView {
            val img = ImageView(mContext)
            img.run {
                layoutParams =
                    ViewGroup.LayoutParams(bean.style.width.toInt(), bean.style.height.toInt())
                x = bean.style.left.toFloat()
                y = bean.style.top.toFloat()
            }
            Glide.with(mContext).asBitmap().load(
                Gson().fromJson<ImageBean>(
                    Gson().toJson(bean.data),
                    ImageBean::class.java
                ).src
            ).into(img)
            return img
        }

        /*二维码*/
        fun createQRcode(mContext: Context, bean: SimpleResp<Any>): ImageView {
            val img = ImageView(mContext)
            img.run {
                layoutParams =
                    ViewGroup.LayoutParams(bean.style.width.toInt(), bean.style.height.toInt())
                x = bean.style.left.toFloat()
                y = bean.style.top.toFloat()
            }
            val url = Gson().fromJson<QRCodeBean>(
                Gson().toJson(bean.data),
                QRCodeBean::class.java
            ).url
            val bitmap =
                Utils.createQRCode(url, bean.style.width.toInt(), bean.style.height.toInt())
            img.setImageBitmap(bitmap)
            return img
        }

        /*音乐*/
        fun createMusic(mContext: Context) {
            if (myThreadPool == null) {
                throw Exception("do not initialization ThreadPool")
            }
            Log.i("MediaPlayLog", "创建音乐")
            val mediaPlayer = MediaPlayer()
            myThreadPool.execute {
                mediaPlayer.setDataSource("https://sharefs.yun.kugou.com/202006191819/68c0027d5a60f96fe908c13d72cfc558/G071/M04/0A/09/54YBAFdkQNuAIyAyADrXMKk8lzA358.mp3")
                Log.i("MediaPlayLog", "设置连接")
                try {
                    mediaPlayer.prepare()
                    mediaPlayer.isLooping = true
                    mediaPlayer.setOnPreparedListener { mediaPlayer ->
                        Timer().schedule(object : TimerTask() {
                            override fun run() {
                                Log.i("MediaPlayLog", "开始播放")
                                mediaPlayer.start() // 准备好了就播放
                            }
                        }, 3000)
                    }
                } catch (e: Exception) {
                    Log.e("MediaPlayLog", "播放出错$e")
                }

            }

            mediaPlayer.setOnErrorListener { mp, what, extra ->
                Log.e("MediaPlayLog", "播放出错$extra")
                false
            }
            mediaPlayer.setOnBufferingUpdateListener { mp, percent ->
                Log.d(
                    "MediaPlayLog",
                    "Progress:$percent%"
                );
            };

        }

        /*VLC播放器*/
        fun createVLC(context: Context, bean: SimpleResp<Any>, rootView: ViewGroup) {
            if (myThreadPool == null) {
                throw Exception("do not initialization ThreadPool")
            }
            val data = Gson().fromJson<VideoBean>(Gson().toJson(bean.data), VideoBean::class.java)
            val fm = FrameLayout(context)
            fm.run {
                layoutParams =
                    ViewGroup.LayoutParams(bean.style.width.toInt(), bean.style.height.toInt())
                x = bean.style.left.toFloat()
                y = bean.style.top.toFloat()
            }
            val id = View.generateViewId()
            fm.id = id
            rootView.addView(fm)
            val list = data.playItemList
            var tag = 0// 播放标记
            var temp = 0//时间中间值
            myThreadPool.execute {
                while (true) {
                    if (temp <= 0) {
                        temp = list!![tag].timer * 1000
                        (context as FragmentActivity).supportFragmentManager.beginTransaction()
                            .replace(
                                id,
//                                VlcFragment.getInstance(list[tag].url)
                                VlcFragment.getInstance("http://vodkgeyttp8.vod.126.net/cloudmusic/MTA1MzMxMzAy/83156654658d5f09a36352637661420e/320484d03272ab6e2b1850f6148ab965.mp4?wsSecret=74564a7ef732412b33d7a83e2d19ed15&wsTime=1592462440")
                            )
                            .commitAllowingStateLoss()
                        tag++
                        tag %= list.size

                    } else {
                        temp -= 1000
                    }
                    Thread.sleep(1000)
                }
            }
        }

        /*跑马灯*/
        fun createRunText(mContext: Context, baseBean: SimpleResp<Any>): SimpleMarqueeView<String> {
            val bean =
                Gson().fromJson<RunTextBean>(Gson().toJson(baseBean.data), RunTextBean::class.java)
            val runText = SimpleMarqueeView<String>(mContext)
            val marqueeFactory = SimpleMF<String>(mContext)
            marqueeFactory.data = listOf(bean.text)
            runText.run {
                setMarqueeFactory(marqueeFactory)
                layoutParams = ViewGroup.LayoutParams(
                    baseBean.style.width.toInt(),
                    baseBean.style.height.toInt()
                )
                x = baseBean.style.left
                y = baseBean.style.top
                setTextSize(bean.style.fontSize)
//       todo         setTextColor(Color.parseColor(bean.style.color))
                //todo 行高
                setTextEllipsize(TextUtils.TruncateAt.END)
                setTextSingleLine(true)
                setTextGravity(Gravity.CENTER)
                setInAnimation(mContext, R.anim.in_right)
                setOutAnimation(mContext, R.anim.in_left)

            }

            runText.startFlipping()

            return runText
        }

        /*天气*/
        fun createWeatherView(mContext: Context, baseBean: SimpleResp<Any>): WeatherView {
            val weatherBean =
                Gson().fromJson<WeatherBean>(Gson().toJson(baseBean.data), WeatherBean::class.java)
            val weatherView = WeatherView(mContext, weatherBean.area[0])
            weatherView.run {
                textSize = weatherBean.style.fontSize
                layoutParams = ViewGroup.LayoutParams(
                    baseBean.style.width.toInt(),
                    baseBean.style.height.toInt()
                )
                x = baseBean.style.left
                y = baseBean.style.top

            }
            return weatherView
        }
    }
}