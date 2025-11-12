package kokoro.mobile.hachimi.ui.common.base

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import kokoro.mobile.contract.SharePlug


abstract class BaseActivity() : ComponentActivity() {
    protected val TAG by lazy {
        if (this::class.java.canonicalName!=null){
            val className: List<String> = this::class.java.canonicalName!!.split(".")
            className[className.size-1]
        }else "TAG"
    }
    //Activity启动器
    private var mStartActivityLauncher: ActivityResultLauncher<Intent>? = null
    //使用启动器跳转Activity
    fun startActivityForResult(intent: Intent, options: ActivityOptionsCompat? = null, result:(ActivityResult.()->Unit)){
        if (mStartActivityLauncher!=null){
            startActivityResult = result
            mStartActivityLauncher!!.launch(intent,options)
        }else throw RuntimeException("未初始化启动器-Activity")
    }
    private var startActivityResult:(ActivityResult.()->Unit)? = null

    //权限请求启动器
    private var mRequestPermissionLauncher: ActivityResultLauncher<Array<String>>? = null
    //有任一一项权限未请求即走false
    fun requestPermission(permissionList:Array<String>,result:((Boolean)->Unit)){
        if (mRequestPermissionLauncher!=null){
            requestPermissionResult = result
            mRequestPermissionLauncher!!.launch(permissionList)
        }else throw RuntimeException("未初始化启动器-Permission")
    }

    private var requestPermissionResult:((Boolean)->Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStartActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            startActivityResult?.invoke(it)
        }
        mRequestPermissionLauncher =  registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val requestPermissionList = it.keys
            var requestSuccess = true
            Log.i(TAG, "RequestPermission: ${it}", )
            for (i in requestPermissionList){
                if (!it[i]!!) {
                    requestSuccess = false
                }
            }
            requestPermissionResult?.invoke(requestSuccess)
        }
    }

    fun requestOverlayPermission(successCallBack:(()->Unit)){
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivityForResult(intent){
                if (Settings.canDrawOverlays(this@BaseActivity)) {
                    successCallBack.invoke()
                }else{
                    Toast.makeText(SharePlug.application, "未授权", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            successCallBack.invoke()
        }
    }

}