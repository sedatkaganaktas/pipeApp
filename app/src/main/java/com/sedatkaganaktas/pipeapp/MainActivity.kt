package com.sedatkaganaktas.pipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sedatkaganaktas.pipeapp.ui.theme.PipeAppTheme
import com.sedatkaganaktas.pipeapp.view.PipeDetailScreen
import com.sedatkaganaktas.pipeapp.view.PipeListScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PipeAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "pipe_list_screen"
                ) {
                    composable("pipe_list_screen") {

                        PipeListScreen(navController = navController)
                    }
                    composable(

                        "pipe_detail_screen/{pipeId}/{pipePrice}",
                        arguments = listOf(
                            navArgument("pipeId") {
                                type = NavType.StringType
                            },
                            navArgument("pipePrice") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val pipeId = remember {
                            it.arguments?.getString("pipeId")
                        }
                        val pipePrice = remember {
                            it.arguments?.getString("pipePrice")
                        }
                        PipeDetailScreen(
                            id= pipeId ?: "",
                            price = pipePrice ?: "",
                            navController = navController
                        )
                    }

                }
            }
        }





        }

}