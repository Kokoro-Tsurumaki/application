package kokoro.mobile.hachimi.ui.features.main.mine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kokoro.mobile.hachimi.R
import kokoro.mobile.hachimi.common.Modifier.scaffoldModifier
import kokoro.mobile.hachimi.ui.PreviewContainer
import kokoro.mobile.hachimi.ui.common.base.VMStateContainer


/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Wed Sep 24 11:33:39 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Mine -Ptpackage=kokoro.mobile.hachimi -Ptpath=application/bad-cat/src/main/java/kokoro/mobile/hachimi/ui/features`
 * Module: Mine
 * Package: kokoro.mobile.hachimi
 * =================================================================================================
 */

@Composable
fun MineScreen() {
    VMStateContainer<MineViewModel>(onInit = { viewModel ->

    }, effectCallBack = {

    }) {
        val viewModel: MineViewModel = viewModel()
        val uiState by viewModel.uiFlow.collectAsStateWithLifecycle()
        MineView(uiState){
            viewModel.sendUIAction(it)
        }
    }
}

@Composable
fun MineView(state: MineContract.MineState, onAction:((MineContract.MineAction)-> Unit)) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Spacer(Modifier.height(40.dp).wrapContentWidth())
        }
        // 用户信息区域
        item {
            UserInfoSection(
                avatarUrl = state.avatarUrl,
                nickname = state.nickname,
                onProfileClick = { onAction(MineContract.MineAction.NavigateToProfile) }
            )
        }

        // 主题切换
        item {
            ThemeSwitchSection(
                isDarkTheme = state.isDarkTheme,
                onThemeChange = { onAction(MineContract.MineAction.ToggleTheme) }
            )
        }

        // 功能列表
        item {
            Text(
                text = "功能设置",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
            )
        }

        items(state.functions) { function ->
            FunctionItem(
                icon = function.icon,
                title = function.title,
                onClick = { onAction(function.action) }
            )
        }

        // 退出登录
        item {
            Spacer(modifier = Modifier.height(24.dp))
            LogoutButton(
                onClick = { onAction(MineContract.MineAction.Logout) }
            )
        }
    }
}
@Composable
fun UserInfoSection(
    avatarUrl: String?,
    nickname: String,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onProfileClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 头像
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            if (avatarUrl != null) {
                // 实际项目中应使用 Coil 或 Glide 加载网络图片
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher), // 占位图
                    contentDescription = "用户头像",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "默认头像",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // 昵称
        Column {
            Text(
                text = nickname,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "查看个人资料",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // 右侧箭头
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "查看个人资料",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ThemeSwitchSection(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onThemeChange(!isDarkTheme) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "主题切换",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "深色模式",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = isDarkTheme,
            onCheckedChange = onThemeChange
        )
    }
}

@Composable
fun FunctionItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "进入",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Divider(
            modifier = Modifier.padding(start = 56.dp, end = 16.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "退出登录",
            color = MaterialTheme.colorScheme.error,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


@Preview
@Composable
fun PreView() {
    PreviewContainer {
        Scaffold(modifier = scaffoldModifier) { innerPadding ->
            MineView(MineContract.MineState()) {

            }
        }
    }
}