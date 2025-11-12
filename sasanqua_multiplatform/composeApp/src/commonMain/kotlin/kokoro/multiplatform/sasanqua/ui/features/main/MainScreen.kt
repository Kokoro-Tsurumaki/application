package kokoro.multiplatform.sasanqua.ui.features.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import kokoro.multiplatform.sasanqua.*
import kokoro.multiplatform.sasanqua.common.Modifier.scaffoldModifier
import kokoro.multiplatform.sasanqua.model.RecordModel
import kokoro.multiplatform.sasanqua.network.client.ApplicationApi
import kokoro.multiplatform.sasanqua.ui.WindowSize
import kokoro.multiplatform.sasanqua.ui.widthSize
import org.jetbrains.compose.ui.tooling.preview.*

/**
 * =================================================================================================
 * FILE GENERATED AUTOMATICALLY BY GRADLE TASK
 * Generated on: Wed Oct 29 11:41:13 CST 2025
 * Command:`./gradlew createTemplateForMVI -Ptflag=Main -Ptpackage=kokoro.multiplatform.sasanqua -Ptpath=composeApp/src/commonMain/kotlin/kokoro/multiplatform/sasanqua/ui/features`
 * Module: Main
 * Package: kokoro.multiplatform.sasanqua
 * =================================================================================================
 */

@Composable
fun MainScreen() {
    println("StartLoad")
    val viewModel = remember { MainViewModel(ApplicationApi()) }
    val uiState by viewModel.uiFlow.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }
    MainView(uiState){
        viewModel.sendUIAction(it)
    }
}

@Composable
fun MainView(state: MainContract.MainState, onAction:((MainContract.MainAction)-> Unit)) {
    val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val currentWindowSize: IntSize = currentWindowSize()
    val current = windowSizeClass.widthSize()
    val netData = mutableStateOf("nodata")
    Scaffold(modifier = scaffoldModifier, content = { innerPadding ->
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //标签
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(3),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                content = {
                    items(state.types.size) { position ->
                        CategoryChip(
                            category = state.types[position].type,
                            isSelected = state.types[position].type == state.selectedType,
                            onClick = {  }
                        )
                    }
                },
                modifier = Modifier.wrapContentSize()
            )



            LazyColumn {
                items(state.records.size) { position ->
                    if (current == WindowSize.Compact) {
                        MessageRow(state.records[position], Modifier.padding(20.dp, 0.dp))
                    }
                    if (current == WindowSize.Medium) {
                        MessageRow(
                            state.records[position],
                            Modifier.padding(((currentWindowSize.width - windowSizeClass.minWidthDp) / 4).dp, 0.dp)
                        )
                    }
                    if (current == WindowSize.Expanded) {
                        MessageRow(
                            state.records[position],
                            Modifier.padding(((currentWindowSize.width - windowSizeClass.minWidthDp) / 4).dp, 0.dp)
                        )
                    }
                }
            }


            Markdown(content = state.markdownContent,
                colors = markdownColor(),
                typography = markdownTypography(), modifier = Modifier.verticalScroll(ScrollState(0)).wrapContentSize()
            )
        }
    })

}

@Composable
fun MessageRow(record: RecordModel, modifier: Modifier) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = record.title,
            modifier = Modifier.padding(12.dp, 16.dp, 12.dp, 6.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = record.hint,
            modifier = Modifier.padding(12.dp, 6.dp, 12.dp, 12.dp),
            textAlign = TextAlign.Left
        )
    }


    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        color = backgroundColor,
        border = BorderStroke(
            width = 1.dp,
            color = if (!isSelected) MaterialTheme.colorScheme.outline else Color.Transparent
        ),
        onClick = onClick,
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = category,
            color = contentColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 0.dp)
                .wrapContentHeight()
        )
    }
}

@Preview
@Composable
fun PreView() {
    PreviewContainer {
        MainView(MainContract.MainState()){

        }
    }
}