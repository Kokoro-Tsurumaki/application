package kokoro.mobile.hachimi.ui.common.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

/**
* Material Design 风格的减号图标
*
* 这是一个水平线图标，表示"减少"或"移除"操作
* 图标尺寸为 24x24 dp，符合 Material Design 规范
*/
public val Icons.Filled.Minus: ImageVector
    get() {
        // 使用惰性初始化，避免重复创建
        if (_minus != null) {
            return _minus!!
        }

        // 创建 Material Design 图标
        _minus = materialIcon(name = "Filled.Minus") {
            // 定义图标路径
            materialPath {
                /**
                * 绘制一个水平矩形作为减号
                *
                * 坐标系统说明：
                * - 原点 (0,0) 在左上角
                * - X 轴向右增加
                * - Y 轴向下方增加
                *
                * 图标尺寸为 24x24 dp，我们将在中心绘制一条水平线
                */

                // 移动到起始点 (5,11)
                // 这是水平线的左上角
                moveTo(5.0f, 11.0f)

                // 绘制水平线：
                // 1. 向右水平线到 (19,11)
                lineTo(19.0f, 11.0f)

                // 2. 向下垂直线到 (19,13)
                lineTo(19.0f, 13.0f)

                // 3. 向左水平线到 (5,13)
                lineTo(5.0f, 13.0f)

                // 4. 向上垂直线回到起点 (5,11)
                lineTo(5.0f, 11.0f)

                /**
                * 路径说明：
                * 我们绘制了一个矩形，但实际上只显示为一条水平线
                * 因为高度只有 2dp (13-11=2)，在标准图标尺寸下看起来是一条线
                *
                * 坐标点：
                * (5,11) -------- (19,11)
                *   |               |
                *   |               |
                * (5,13) -------- (19,13)
                */

                // 关闭路径（可选，但保持与 Add 图标一致）
                close()
            }
        }
        return _minus!!
    }

// 私有变量存储图标实例
private var _minus: ImageVector? = null