package com.example.composedemo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.navArgument
import com.example.composedemo.Menu.*
import com.example.composedemo.nav.NavPage1
import com.example.composedemo.nav.NavPage2
import com.example.composedemo.nav.NavViewModel
import com.example.composedemo.widget.HorizontalPagerDemo
import com.example.composedemo.widget.RowCombinePagerAdapter
import com.example.composedemo.widget.RowCombineViewModel
import com.example.composedemo.widget.collapsing_layout.CollapsingHeaderDemo
import com.google.accompanist.navigation.animation.AnimatedComposeNavigator
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainNavGraph(navController, navViewModel = navViewModel)
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun MainNavGraph(
        navController: NavHostController,
        navViewModel: NavViewModel
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            main(navController)
            navPage1(navController, navViewModel)
            navPage2(navController)
            composable(Pager.router) {
                HorizontalPagerDemo()
            }
            rowCombinePager()
            composable(ScrollableAppBar.router) {
                CollapsingHeaderDemo()
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    private fun NavGraphBuilder.main(navController: NavHostController) {
        composable(route = "main") { backStackEntry ->
            MainPage { _, router ->
                navController.navigate(router)
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

    @OptIn(ExperimentalAnimationApi::class)
    private fun NavGraphBuilder.rowCombinePager() {
        composable(route = RowPager.router) { _ ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
                val viewModel: MyRowCombineViewModel by viewModels(
                    factoryProducer = {
                        object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return MyRowCombineViewModel(
                                    list = (1..100).map { it.toString() },
                                    initPosition = 100 / 2,
                                ) as T
                            }
                        }
                    })
                Logging.info("NavGraphBuilder.rowCombinePager, position=${viewModel.position.collectAsState().value}")
                RowCombinePagerAdapter(
                    viewModel = viewModel,
                    horizontalItem = { position, item, onClick ->
                        Box(
                            modifier = Modifier
                                .width(48.dp)
                                .height(64.dp)
                                .padding(2.dp)
                                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(24.dp))
                                .clickable { onClick.invoke() }
                        ) {
                            Text(
                                position.toString(),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 16.sp
                            )
                        }
                    },
                    pagerItem = { position, item ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Cyan)
                        ) {
                            Text(
                                position.toString(),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                )
                // anchor view
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .align(Alignment.Center)
                        .background(Color.Red)
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .align(Alignment.Center)
                        .background(Color.Red)
                )
            }
        }
    }
}

class MyRowCombineViewModel(
    override val list: List<String>,
    override val initPosition: Int
) : RowCombineViewModel<String>()
