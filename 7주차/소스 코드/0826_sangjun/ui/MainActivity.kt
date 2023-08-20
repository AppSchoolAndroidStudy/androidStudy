package likelion.project.compose_recyclerview.ui

import android.os.Bundle
import android.util.Log
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import likelion.project.compose_recyclerview.model.Giphy
import likelion.project.compose_recyclerview.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListItem(giphy: Giphy) {
    Surface(
        color = Color.White,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
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
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    mainViewModel.getGif()
    val uiState by mainViewModel.uiState.collectAsState()
    val giphyList by remember(uiState.giphyList) { mutableStateOf(uiState.giphyList) }

    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(giphyList ?: emptyList()) { giphy ->
            ListItem(giphy = giphy)
        }
    }
}

