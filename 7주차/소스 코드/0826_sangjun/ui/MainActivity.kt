package likelion.project.compose_recyclerview.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import likelion.project.compose_recyclerview.model.Giphy
import likelion.project.compose_recyclerview.viewmodel.MainViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main") {

                composable("main") {
                    MainScreen(onClickDetail = { urlType ->
                        val encodeUrl =
                            URLEncoder.encode(urlType, StandardCharsets.UTF_8.toString())
                        navController.navigateToDetail(encodeUrl)
                    })
                }
                composable(
                    route = Detail.routeWithArgs,
                    arguments = Detail.arguments
                ) { backStackEntry ->
                    MainDetailScreen(
                        backStackEntry.arguments?.getString(Detail.urlTypeArg) ?: ""
                    )
                }
            }

        }
    }

    fun NavHostController.navigateSingleTopTo(route: String) =
        this.navigate(route) {
            popUpTo(
                this@navigateSingleTopTo.graph.findStartDestination().id
            ) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

    private fun NavHostController.navigateToDetail(urlType: String) {
        this.navigateSingleTopTo("${Detail.route}/$urlType")
    }
}

interface Destination {
    val route: String
}

object Detail : Destination {
    override val route = "mainDetail"
    const val urlTypeArg = "url_type"
    val routeWithArgs = "$route/{$urlTypeArg}"
    val arguments = listOf(
        navArgument(urlTypeArg) { type = NavType.StringType }
    )
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListItem(giphy: Giphy, onClickDetail: (String) -> Unit) {
    val giphyJson = Json.encodeToString(giphy)
    Surface(
        color = Color.White,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        onClick = { onClickDetail(giphyJson) },
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .height(250.dp)
                .fillMaxWidth(),
        ) {

            Row(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            ) {

                GlideImage(
                    model = giphy.images.original.url,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                ) {
                    it.load(giphy.images.original.url)
                }
            }

            Column {
                Text(
                    text = giphy.title, style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }

        }
    }
}

@Composable
fun MainScreen(
    onClickDetail: (String) -> Unit,
    mainViewModel: MainViewModel = viewModel()
) {
    mainViewModel.getGif()
    val uiState by mainViewModel.uiState.collectAsState()
    val giphyList by remember(uiState.giphyList) { mutableStateOf(uiState.giphyList) }

    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(giphyList ?: emptyList()) { giphy ->
            ListItem(giphy = giphy, onClickDetail)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MainDetailScreen(arg: String) {
    val giphy = Json.decodeFromString<Giphy>(arg)
    Column(
        modifier = Modifier
            .padding(24.dp)
            .height(250.dp)
            .fillMaxWidth(),
    ) {

        Row(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            GlideImage(
                model = giphy.images.original.url,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            ) {
                it.load(giphy.images.original.url)
            }
        }

        Column {
            Text(
                text = giphy.title, style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Light
                )
            )
        }

    }
}
