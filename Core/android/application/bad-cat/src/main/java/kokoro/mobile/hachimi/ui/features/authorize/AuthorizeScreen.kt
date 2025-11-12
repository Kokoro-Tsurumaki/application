package kokoro.mobile.hachimi.ui.features.authorize

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kokoro.mobile.hachimi.R
import kokoro.mobile.hachimi.common.Modifier.scaffoldModifier
import kokoro.mobile.hachimi.common.nonScaledSp
import kokoro.mobile.hachimi.theme.color_d7dee0
import kokoro.mobile.hachimi.ui.PreviewContainer
import kokoro.mobile.hachimi.ui.common.base.VMStateContainer
import kokoro.mobile.hachimi.ui.common.view.specific.HachimiFlag

/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Wed Sep 17 10:13:34 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Authorize -Ptpackage=kokoro.mobile.hachimi -Ptpath=application/bad-cat/src/main/java/kokoro/mobile/hachimi/ui/features`
 * Module: Authorize
 * Package: kokoro.mobile.hachimi
 * =================================================================================================
 */

@Composable
fun AuthorizeScreen() {
    VMStateContainer<AuthorizeViewModel>(onInit = { viewModel ->

    }, effectCallBack = {

    }) {
        val viewModel: AuthorizeViewModel = viewModel()
        val uiState by viewModel.uiFlow.collectAsStateWithLifecycle()
        AuthorizeView(uiState){
            viewModel.sendUIAction(it)
        }
    }
}

@Composable
fun AuthorizeView(state: AuthorizeContract.AuthorizeState, onAction:((AuthorizeContract.AuthorizeAction)-> Unit)) {
    Scaffold(modifier = scaffoldModifier, content = { innerPadding ->

        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Spacer(Modifier.size(20.dp))
            HachimiFlag(Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.size(20.dp))
            PermissionView( R.drawable.baseline_unarchive_24,"存储权限","用于头像设置，保存分享图片")
            Spacer(Modifier.size(10.dp))
            PermissionView( R.drawable.baseline_add_to_home_screen_24,"显示悬浮窗权限","用于快捷记账，当未启动应用时进行记账")

           Box(modifier = Modifier.padding(20.dp).fillMaxWidth().weight(1F).verticalScroll(
               ScrollState(0))) {
               Text(text = state.hint,
                   fontSize = 12.nonScaledSp,)
           }
            Spacer(Modifier.size(20.dp))
            Box(modifier = Modifier.wrapContentHeight().fillMaxWidth().align(Alignment.CenterHorizontally)) {


                Row(modifier = Modifier.align(Alignment.BottomCenter).padding(0.dp,20.dp)) {
                    OutlinedButton(onClick = { onAction(AuthorizeContract.AuthorizeAction.OnAuthorizeRejectClick) }) {
                        Text("不同意")
                    }

                    Spacer(Modifier.size(20.dp))
                        Button(onClick = {  onAction(AuthorizeContract.AuthorizeAction.OnAuthorizeAgreeClick) }) {
                            Text("同意并继续")
                        }
                }
            }
        }
    })
}

@Composable
fun PermissionView(resourceId:Int,title:String,content: String){
    Row (modifier = Modifier.fillMaxWidth().height(50.dp)){

        Spacer(Modifier.fillMaxHeight().width(20.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .border(1.dp, color_d7dee0,RoundedCornerShape(90.dp))
                .size(width = 40.dp, height = 40.dp).align(Alignment.CenterVertically)
        ) {
            Box( modifier = Modifier.fillMaxSize()){
                Image( modifier = Modifier
                    .size(24.dp, 24.dp).align(Alignment.Center),
                    painter = painterResource(id = resourceId),
                    contentDescription = "hachimi")
            }

        }

        Spacer(Modifier.fillMaxHeight().width(20.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(modifier = Modifier.fillMaxWidth(),text = title, maxLines = 1)
            Text(modifier = Modifier.fillMaxWidth(),text = content,maxLines = 1,
                fontSize = 12.nonScaledSp,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.fillMaxHeight().width(20.dp))
    }
}

@Preview
@Composable
fun PreView() {
    PreviewContainer {
        AuthorizeView(AuthorizeContract.AuthorizeState()){

        }
    }
}