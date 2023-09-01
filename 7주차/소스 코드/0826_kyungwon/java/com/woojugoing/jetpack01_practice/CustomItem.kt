package com.woojugoing.jetpack01_practice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomItem(dog: Dog) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(0.dp, 12.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
    ){
        Row(
            modifier = Modifier
                .background(Color(234, 198, 150))
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Image(
                painter = painterResource(id = dog.img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(84.dp)
                    .clip(RoundedCornerShape(CornerSize(16.dp)))
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ){
                Text(
                    text = dog.dogName,
                    color = Color(101, 69, 31),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dog.dogAct,
                    color = Color(118, 88, 39),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }


}

@Composable
@Preview
fun CustomItemPriview() {
    CustomItem(
        dog = Dog(
            id = 0,
            dogName = "누누",
            dogAct = "랄라",
            img = R.drawable.dog1
        )
    )
}
