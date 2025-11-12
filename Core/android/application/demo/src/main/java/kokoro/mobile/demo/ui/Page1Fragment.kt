package kokoro.mobile.demo.ui

import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.widget.NestedScrollView
import kokoro.mobile.demo.EventFrameLayout
import kokoro.mobile.demo.R
import cn.xj.kokoro.mobile.album.FileManage
import cn.xj.kokoro.mobile.album.PhotoDataManage
import kokoro.mobile.demo.base.BaseFragment
import kokoro.mobile.contract.SharePlug
import kokoro.mobile.demo.album.selectorImage
import kokoro.mobile.demo.utils.createGradientShape
import kokoro.mobile.demo.utils.setOnIntervalClickListener
import kokoro.mobile.demo.view.DashboardView
import kokoro.mobile.demo.view.material500List
import kotlin.concurrent.thread

class Page1Fragment : BaseFragment() {
    override fun getViewId(): Int = R.layout.fragment_page1

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {

    }

    var count = 0
    fun test(url: Uri){
        FileManage.compressImage(requireContext(), url){
            if (count < 1000){
                Log.e(TAG, "test: 压缩第${count}次，文件是：${it}" )
                count ++
                if (count %15 == 0){
                    System.gc()
                }
                Thread.sleep(10)
                test(it)
            }else {
                PhotoDataManage(SharePlug.application!!).addPhoto(it) {
                }
            }

        }
    }

    override fun init(view: View, savedInstanceState: Bundle?) {
        view.apply {
            findViewById<TextView>(R.id.textView).setOnIntervalClickListener {
                requireActivity().selectorImage(9, false) {
                    thread {
                        test(it[0].toUri())
                    }
                }
//                SharePlug.launchComposeUI(requireContext())
            }
            val view1 = findViewById<DashboardView>(R.id.cusView1)
            val seekBar = findViewById<SeekBar>(R.id.seekBar).apply {
                setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        Log.e(TAG, "onProgressChanged: ${progress}")
                        view1.setValue(progress.toFloat(), false)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    }

                })
            }
            view1.valueChangeCallback = {
                seekBar.progress = it.toInt()
            }
            findViewById<Button>(R.id.button1).apply {
                setOnIntervalClickListener {
                    view1.startAnimation(20F)
                }
            }
            findViewById<Button>(R.id.button2).apply {
                setOnIntervalClickListener {
                    view1.startAnimation(50F)
                }
            }
            findViewById<Button>(R.id.button3).apply {
                setOnIntervalClickListener {
                    view1.startAnimation(80F)
                }
            }
            val drawable = createGradientShape(
                backGroundColor =
                    material500List.toIntArray(),
                positions = floatArrayOf(
                    0.0F,
                    0.05F,
                    0.1F,
                    0.15F,
                    0.2F,
                    0.25F,
                    0.3F,
                    0.35F,
                    0.4F,
                    0.45F,
                    0.5F,
                    0.55F,
                    0.6F,
                    0.65F,
                    0.7F,
                    0.75F,
                    0.8F,
                    0.85F,
                    0.9F,

                    ),
                orientation = GradientDrawable.Orientation.TOP_BOTTOM
            )
            findViewById<View>(R.id.canvan3).apply {
                background = drawable
            }


            findViewById<View>(R.id.canvan2).apply {
                background = drawable
            }


            findViewById<View>(R.id.canvan).apply {
                background = drawable

            }
            val root = findViewById<EventFrameLayout>(R.id.rootLayout)
            root.apply {
                //想要拦截事件，有此处处理不传递给子View

                setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(
                        v: View?,
                        event: MotionEvent?
                    ): Boolean {
                        v?.performClick()
                        return true
                    }

                })

                setOnIntervalClickListener {
                    Toast.makeText(requireContext(), "根视图", Toast.LENGTH_SHORT).show()
                }
            }

            findViewById<Switch>(R.id.seekbar1).apply {
                setOnCheckedChangeListener { view, check ->
                    root.interceptEnabled = check
                }
            }

            findViewById<Switch>(R.id.seekbar2).apply {
                setOnCheckedChangeListener { view, check ->
                    if (check) {
                        root.addExcludedViewType(ScrollView::class)
                        root.addExcludedViewType(NestedScrollView::class)
                    } else {
                        root.removeAllExcludeViews()
                    }

                }
            }
        }
    }
}