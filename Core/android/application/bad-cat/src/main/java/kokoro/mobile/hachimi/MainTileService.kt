package kokoro.mobile.hachimi

import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

class MainTileService: TileService() {
    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
        Log.e("TAG", "onStartListening: ", )
    }

    override fun onStopListening() {
        super.onStopListening()
        Log.e("TAG", "onStopListening: ", )
    }

    override fun onClick() {
        super.onClick()
        togglePowerSavingMode()
        updateTileState()
    }

    val overlayWindow by lazy{
        OverlayWindow()
    }
    private fun togglePowerSavingMode() {
        val isActive = !isPowerSavingActive()
        // 保存状态到 SharedPreferences 或数据库
        getSharedPreferences("tile_prefs", MODE_PRIVATE)
            .edit()
            .putBoolean("power_saving", isActive)
            .apply()

        // 实际执行功能切换
        if (isActive) {
            enablePowerSaving()
        } else {
            disablePowerSaving()
        }
    }

    private fun updateTileState() {
        val tile = qsTile
        val isActive = isPowerSavingActive()
        Log.e("TAG", "updateTileState: ${isActive}",)

        tile.state = if (isActive) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        tile.label = if (isActive) "快捷记账" else "快捷记账"
        tile.icon = Icon.createWithResource(
            this,
            if (isActive) R.drawable.ic_launcher else R.drawable.ic_launcher
        )

        tile.updateTile()
    }

    private fun isPowerSavingActive(): Boolean {
        return getSharedPreferences("tile_prefs", MODE_PRIVATE)
            .getBoolean("power_saving", false)
    }

    private fun enablePowerSaving() {
        // 实现省电模式开启逻辑
        overlayWindow.showGlobalDialog(this){
            togglePowerSavingMode()
            updateTileState()
        }
    }

    private fun disablePowerSaving() {
        // 实现省电模式关闭逻辑
        overlayWindow.dismissGlobalDialog()
    }
}