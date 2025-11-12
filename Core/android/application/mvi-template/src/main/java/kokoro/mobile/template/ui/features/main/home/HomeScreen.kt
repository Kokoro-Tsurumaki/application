package kokoro.mobile.template.ui.features.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kokoro.mobile.template.common.nonScaledSp
import kokoro.mobile.template.R
import kokoro.mobile.template.theme.White
import kokoro.mobile.template.theme.White_33
import kokoro.mobile.template.ui.common.base.VMStateContainer

/**
 * Created by xianjie on 2025年1月3日21:31:10
 *
 * Description:
 */
@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    _root_ide_package_.kokoro.mobile.template.ui.common.base.VMStateContainer<HomeViewModel>(onInit = {

    }, effectCallBack = {

    }) {
        HomeView(innerPadding)
    }
}

@Composable
fun HomeView(innerPadding: PaddingValues) {
    val viewModel: HomeViewModel = viewModel()
    //状态
    val uiState by viewModel.uiFlow.collectAsStateWithLifecycle()

    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp), contentScale = ContentScale.FillBounds,
        painter = painterResource(id = R.drawable.icon_bg), contentDescription = ""
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = innerPadding.calculateBottomPadding(),
                start = 16.dp,
                end = 16.dp,
                top = innerPadding.calculateTopPadding() + 10.dp
            ),
    ) {
        //欢迎来到海南雨昕智慧互联网医院
        Image(
            modifier = Modifier
                .size(250.dp, 30.dp),
            painter = painterResource(id = R.drawable.hometitle), contentDescription = ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        //doctor info
        Box(Modifier) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(80.dp),
                    painter = painterResource(id = R.drawable.icon_head_image),
                    contentDescription = ""
                )
                Image(
                    modifier = Modifier
                        .wrapContentSize()
                        .offset(x = (-20).dp),
                    painter = painterResource(id = R.drawable.icon_verify), contentDescription = ""
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 100.dp)
            ) {
                Column(modifier = Modifier) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = "未认证",
                        color = _root_ide_package_.kokoro.mobile.template.theme.White,
                        fontSize = 22.nonScaledSp,
                    )
                    Text(
                        text = "测试",
                        color = _root_ide_package_.kokoro.mobile.template.theme.White,
                        fontSize = 15.nonScaledSp,
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))

                Column {
                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier.Companion
                            .background(_root_ide_package_.kokoro.mobile.template.theme.White_33, RoundedCornerShape(25.dp)),
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp, 3.dp, 10.dp, 3.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = "主任医师",
                                color = _root_ide_package_.kokoro.mobile.template.theme.White,
                                fontSize = 12.nonScaledSp,
                            )

                            Spacer(
                                modifier = Modifier.Companion
                                    .background(_root_ide_package_.kokoro.mobile.template.theme.White)
                                    .width(1.dp)
                                    .height(18.dp)
                            )

                            Text(
                                text = "内科",
                                color = _root_ide_package_.kokoro.mobile.template.theme.White,
                                fontSize = 12.nonScaledSp,
                            )
                        }

                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(18.dp))

        //待办事项
        Row(
            Modifier
                .height(74.dp)
                .fillMaxWidth()
                .padding(paddingValues = PaddingValues(4.dp,0.dp)),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),

                    painter = painterResource(id = R.drawable.home_djz),
                    contentDescription = "", contentScale = ContentScale.FillBounds
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.home_dhf),
                    contentDescription = "", contentScale = ContentScale.FillBounds
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        //功能分发
        Row(
            Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(Color.White,RoundedCornerShape(10.dp,10.dp,0.dp,0.dp))
                .padding(paddingValues = PaddingValues(4.dp,0.dp))
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.icon_invitation),
                    contentDescription = "", contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "邀请患者", fontSize = 14.nonScaledSp, color = Color.Black)
            }


            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.icon_manager),
                    contentDescription = "", contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "患者管理", fontSize = 14.nonScaledSp, color = Color.Black)
            }

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.icon_team),
                    contentDescription = "", contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "团队", fontSize = 14.nonScaledSp, color = Color.Black)
            }

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.icon_meeting),
                    contentDescription = "", contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "会诊", fontSize = 14.nonScaledSp, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        //医疗资讯
        Column( Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(Color.White)
            .padding(paddingValues = PaddingValues(10.dp,0.dp))) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "医疗资讯", fontSize = 16.nonScaledSp, color = Color.Black)

        }
    }
}

@Preview
@Composable
fun PreView() {
    HomeView(PaddingValues(20.dp))
}