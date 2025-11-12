package kokoro.mobile.hachimi

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import android.widget.Button
import android.widget.TextView
import kokoro.mobile.contract.SharePlug
import kotlin.math.abs


class OverlayWindow() {
    var params  = LayoutParams()
    //用于移动测量的上一次触摸X轴坐标
    private var touchX = 0F
    //用于移动测量的上一次触摸Y轴坐标
    private var touchY = 0F
    private  val overlayView: View = LayoutInflater.from(SharePlug.application).inflate(R.layout.layout_floating_window, null)
    private lateinit var windowManager: WindowManager
    private lateinit var dialogView: View
    private var isDialogShowing = false

    fun showGlobalDialog(context: Context,finishCallback:(()-> Unit)) {
        if (isDialogShowing) return
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        // 创建对话框视图
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dialogView = inflater.inflate(R.layout.layout_floating_window, null)

        // 设置对话框内容
//        val title = dialogView.findViewById<TextView>(R.id.dialog_title)
//        val message = dialogView.findViewById<TextView>(R.id.dialog_message)
        val closeButton = dialogView.findViewById<Button>(R.id.dialog_close_button)

//        title.text = "全局通知"
//        message.text = "这是一个从服务启动的全局弹窗\n即使应用在后台也能显示"

        closeButton.setOnClickListener {
            dismissGlobalDialog()
            finishCallback.invoke()
        }

        // 创建窗口参数
        val params = createWindowParams()

        // 添加视图到窗口管理器
        try {
            windowManager.addView(dialogView, params)
            isDialogShowing = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createWindowParams(): LayoutParams {
        return LayoutParams().apply {
            // 设置窗口类型
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                LayoutParams.TYPE_PHONE
            }

            // 设置窗口标志
            flags = LayoutParams.FLAG_NOT_FOCUSABLE or
                    LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                    LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    LayoutParams.FLAG_BLUR_BEHIND or
                    LayoutParams.FLAG_FULLSCREEN

            // 设置格式
            format = PixelFormat.TRANSLUCENT

            // 设置尺寸和位置
            width = LayoutParams.WRAP_CONTENT
            height = LayoutParams.MATCH_PARENT
            gravity = Gravity.END

            // 设置动画
            windowAnimations = android.R.style.Animation_Toast
        }
    }

    fun dismissGlobalDialog() {
        if (isDialogShowing) {
            try {
                windowManager.removeView(dialogView)
                isDialogShowing = false
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
            }
        }
    }

    fun View.isEdgeTouched(x: Float, y: Float, edgeThreshold: Float): Boolean {
        return x in edgeThreshold..(width - edgeThreshold) && y in edgeThreshold..(height - edgeThreshold)
    }
    private var downEdgeTouched:Boolean = true
//    fun createOverlayWindow(){
//        activityRef.get()?.let {
//            if (Settings.canDrawOverlays(it)) {
//                // 设置浮窗的布局参数
//                params.type = LayoutParams.TYPE_APPLICATION_OVERLAY // 必须在 Android 8.0（API 26）及以上使用
//                params.format = PixelFormat.TRANSLUCENT // 设置透明
//                params.flags = LayoutParams.FLAG_NOT_FOCUSABLE or LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//                params.width = it.resources.getDimension(cn.xj.kokoro.mobile.contract.R.dimen.dp_200).toInt()
//                params.height = it.resources.getDimension(cn.xj.kokoro.mobile.contract.R.dimen.dp_400).toInt()
//                params.gravity = Gravity.BOTTOM or Gravity.END // 设置悬浮窗的位置
//
//
//                // 设置浮窗的位置，可以根据需求进行调整
////        params.x = resources.displayMetrics.widthPixels / 2
////        params.y = resources.displayMetrics.heightPixels / 4
//
//                // 添加浮窗到窗口管理器
//                windowManager.addView(overlayView, params)
//                overlayView.setOnClickListener{view->
//                    // 当点击弹窗的“OK”按钮时，启动 MainActivity 并将其带到前台
//                    SharePlug.launchComposeUI(it)
//                }
//                overlayView.setOnTouchListener { view, event ->
//                    when(event.action) {
//                        MotionEvent.ACTION_DOWN -> {
//                            touchX = event.rawX
//                            touchY = event.rawY
//                            downEdgeTouched =view.isEdgeTouched(event.x,event.y,it.resources.getDimension(cn.xj.kokoro.mobile.contract.R.dimen.dp_20))
//                        }
//                        MotionEvent.ACTION_UP->{
//                            overlayView.performClick()
//                        }
//
//
//                        MotionEvent.ACTION_MOVE -> {
//                            if (downEdgeTouched){
//                                moveView(event.rawX, event.rawY)
//                            }else{
//                                changeView(event.rawX, event.rawY)
//                            }
//                        }
//                    }
//                    false
//                }
//            }else{
//                Toast.makeText(SharePlug.application, "未授权", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
    /**
     * 移动子View
     */
    private fun moveView(x: Float, y: Float){
        //移动的距离
        val dragX: Float = x - touchX
        val dragY: Float = y - touchY
        val newL: Float = params.x - dragX
        val newT: Float = params.y - dragY
        //重置上一次触摸的位置
        touchX = x
        touchY = y
        params.x = newL.toInt()
        params.y = newT.toInt()
        windowManager.updateViewLayout(overlayView, params)
    }
    private fun changeView(x: Float, y: Float){
        //移动的距离
        val dragX: Float = x - touchX
        val dragY: Float = y - touchY
        //重置上一次触摸的位置
        touchX = x
        touchY = y
        val changeN = if (abs(dragX)>abs(dragY)) dragX else dragY
        params.width -= changeN.toInt()
        params.height -= changeN.toInt()

        windowManager.updateViewLayout(overlayView, params)
    }
    fun getView():View = overlayView


}