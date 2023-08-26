package com.test.jetpackcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.jetpackcompose.model.AnimalDataClass
import com.test.jetpackcompose.data.AnimalDateProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme{
                Surface(modifier = Modifier.fillMaxSize()) {
                    RecyclerviewContent()
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MyApp(){
//    Scaffold{
//        it.calculateBottomPadding()
//        RecyclerviewContent()
//    }
//}

@Composable
fun RecyclerviewContent(){
    MaterialTheme{
        Surface {
            val animals = remember { AnimalDateProvider.animalList }
            LazyColumn(contentPadding = PaddingValues(16.dp, 8.dp)){
                items(
                    items = animals,
                    itemContent = { AnimalListItem(it) }
                )
            }
        }
    }
}

//recycler view 아이템 설정
//약간 뷰홀더 같은 느낌..?
@Composable
fun AnimalListItem(animal : AnimalDataClass){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 10.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        var isExpanded by remember { mutableStateOf(false) }

        Column(
                modifier = Modifier
                    .clickable { isExpanded = !isExpanded }
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {

                AnimalImage(animal = animal)

                //여백
                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = animal.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                //여백
                Spacer(modifier = Modifier.height(5.dp))

                Surface(
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = animal.content,
                        modifier = Modifier.padding(all = 4.dp),
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }


            }

    }

}

//이미지 뷰(?)에 해당 이미지를 표시
@Composable
fun AnimalImage(animal: AnimalDataClass){
    Image(
        painter = painterResource(id = animal.image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(300.dp)
            .clip(RoundedCornerShape(CornerSize(16.dp)))
    )
}
