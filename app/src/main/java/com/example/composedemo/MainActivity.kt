package com.example.composedemo

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import com.example.composedemo.Menu.*
import com.example.composedemo.R.string
import com.example.composedemo.widget.RowCombineViewModel
import com.google.accompanist.navigation.animation.AnimatedComposeNavigator
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

private val bgColors = listOf(Color.Cyan, Color.Magenta, Color.LightGray, Color.Yellow, Color.Blue, Color.Green)

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    private val navController: NavHostController by lazy {
        NavHostController(this).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            navigatorProvider.addNavigator(DialogNavigator())
            navigatorProvider.addNavigator(AnimatedComposeNavigator())
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appName = stringResource(id = string.app_name)
            val title = remember { mutableStateOf(appName) }
            val toolbarVisible = remember { mutableStateOf(true) }
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                Logging.info("OnDestinationChanged: ${destination.route}, arguments=${arguments.getInformation()}")
                title.value = destination.route ?: title.value
                requestedOrientation = if (destination.route == MultipleScrollSample.router) {
                    toolbarVisible.value = false
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                } else {
                    toolbarVisible.value = true
                    ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                }
            }
            MaterialTheme {
                Scaffold(
                    topBar = {
                        if (toolbarVisible.value) {
                            TopAppBar(
                                title = {
                                    Text(text = title.value)
                                },
                                navigationIcon = {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(Icons.Filled.ArrowBack, contentDescription = "")
                                    }
                                }
                            )
                        }
                    },
                ) {
                    MainNavGraph(
                        modifier = Modifier.padding(it),
                        navController,
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun MainNavGraph(
        modifier: Modifier,
        navController: NavHostController,
    ) {
        val context = LocalContext.current
        AnimatedNavHost(
            navController = navController,
            startDestination = "main",
            route = "list",
            modifier = modifier
        ) {
            val activeMenu = menuList.indexOfFirst { it is DefaultPage }
            composable(route = "main") {
                LazyVerticalGrid(
                    state = rememberLazyGridState(activeMenu, 0),
                    columns = Adaptive(minSize = 128.dp),
                    content = {
                        itemsIndexed(menuList) { index, menu ->
                            Box(
                                modifier = Modifier
                                    .height(128.dp)
                                    .background(color = bgColors[index % bgColors.size])
                                    .clickable {
                                        onMenuClick(context, menu)
                                    }
                                    .padding(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = menu.name,
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    })
            }
            menuList.forEach { menu ->
                composable(menu.router) { menu.content() }
            }
            if (activeMenu >= 0) {
                onMenuClick(context = context, menuList[activeMenu])
            }
        }
    }

    private fun onMenuClick(context: Context, menu: Menu) {
        if (menu !is ActivityPage) navController.navigate(menu.router)
        else startActivity(Intent(context, menu.targetPage.java))
    }
}

class MyRowCombineViewModel(
    override val list: List<String>,
    override val initPosition: Int
) : RowCombineViewModel<String>()
