package com.example.composedemo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.navArgument
import com.example.composedemo.Menu.*
import com.example.composedemo.R.string
import com.example.composedemo.nav.NavPage1
import com.example.composedemo.nav.NavPage2
import com.example.composedemo.nav.NavViewModel
import com.example.composedemo.widget.RowCombineViewModel
import com.google.accompanist.navigation.animation.AnimatedComposeNavigator
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

private val bgColors = listOf(Color.Cyan, Color.Magenta, Color.LightGray, Color.Yellow, Color.Blue, Color.Green)

class MainActivity : AppCompatActivity() {

    private val navViewModel = NavViewModel(2)

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
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                Logging.info("OnDestinationChanged: ${destination.route}, arguments=${arguments.getInformation()}")
                title.value = destination.route ?: title.value
            }
            MaterialTheme {
                Scaffold(
                    topBar = {
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
                    },
                ) {
                    MainNavGraph(
                        modifier = Modifier.padding(it),
                        navController,
                        navViewModel = navViewModel,
                        paddingValues = it
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
        navViewModel: NavViewModel,
        paddingValues: PaddingValues
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = "main",
            modifier = modifier,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            composable(route = "main") {
                LazyVerticalGrid(
                    columns = Adaptive(minSize = 128.dp),
                    content = {
                        itemsIndexed(menuList) { index, menu ->
                            Box(
                                modifier = Modifier
                                    .height(128.dp)
                                    .background(color = bgColors[index % bgColors.size])
                                    .clickable { navController.navigate(menu.router) },
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
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    private fun NavGraphBuilder.navPage1(
        navController: NavHostController,
        viewModel: NavViewModel
    ) {
        composable(route = Nav1.router) { backStackEntry ->
            backStackEntry.arguments?.get("value")?.let {
                (it as? Int).apply {
                    viewModel.result.value = this
                }
            }
            NavPage1(
                viewModel = viewModel
            ) {
                navController.navigate("${Nav2.router}?param=${it}")
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    private fun NavGraphBuilder.navPage2(navController: NavHostController) {
        composable(
            route = "${Nav2.router}?param={param}",
            arguments = listOf(navArgument("param") { defaultValue = -1 })
        ) { backStackEntry ->
            NavPage2(
                param = backStackEntry.arguments?.getInt("param") ?: -1,
                onClick = {
                    navController.previousBackStackEntry?.arguments?.putInt("value", it)
                    navController.popBackStack()
                }
            )
        }
    }

}

class MyRowCombineViewModel(
    override val list: List<String>,
    override val initPosition: Int
) : RowCombineViewModel<String>()
