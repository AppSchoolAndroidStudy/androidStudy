package com.test.compose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                SetRecyclerView()
            }
        }
    }
}

// Scaffold
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetRecyclerView() {
    Scaffold {
        RecyclerViewContent()
    }
}

@Composable
fun RecyclerViewContent() {
    val photos = remember { DataProvider.photoList }
    LazyColumn(contentPadding = PaddingValues(16.dp, 16.dp)) {
        items(
            items = photos,
            itemContent = { photo ->
                // 각 항목에 간격을 두기 위해 Modifier.padding을 사용
                PhotoListItem(photo, Modifier.padding(bottom = 24.dp))
            }
        )
    }
}

@Composable
fun PhotoListItem(photo: Photo, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // 카드 내부의 배경 색상을 원하는 색상으로 설정합니다.
        ) {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                PhotoImage(photo = photo)
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = photo.title, fontSize = 20.sp)
                    Text(text = photo.date, fontSize = 14.sp, color = Color.Gray)
                    Text(text = photo.content, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun PhotoImage(photo: Photo) {
    Image(
        painter = painterResource(id = photo.image),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.size(132.dp, 197.dp)
    )
}

object DataProvider {
    val photoList = listOf(
        Photo("Hakata Station, Hukuoka", "22.12.25", R.drawable.hakata, "🎄크리스마스 하카타역 앞에서!"),
        Photo("Ohori Park, Hukuoka", "23.03.27", R.drawable.ohori, "🦆"),
        Photo("Hakata, Hukuoka", "22.12.26", R.drawable.hukuoka_moon, "🌙"),
        Photo("Hukuoka Tower, Hukuoka", "22.12.27", R.drawable.momochi, "🗼"),
        Photo("Seoak, Gyenogju", "23.03.27", R.drawable.cherry_blossoms, "🌸"),
        Photo("Woljeonggyo, Gyenogju", "23.03.27", R.drawable.gyeongju, "월정교 야경 언제 봐도 예쁘다.."),
        Photo("Dongseong-ro, Daegu", "23.04.18", R.drawable.camera, "텐동 먹고 나오는 길... 카메라 사고 싶.."),
        Photo("Leehyun Park, Daegu", "23.04.30", R.drawable.daisy, "🌼데이지 보러 간날"),
        Photo("Dadaepo, Busan", "23.06.03", R.drawable.dadaepo_moon, "다대포 꼭 가 보세요..."),
        Photo("Dadaepo, Busan", "23.06.03", R.drawable.dadaepo_sunset, "위에 달 사진이랑 같은 날!"),
        Photo("☁️", "22.06.15", R.drawable.cloud, "️어디였더라.."),
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        RecyclerViewContent()
    }
}


