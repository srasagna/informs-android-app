package com.example.iorms.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.iorms.R
//
//class Project :ComponentActivity(){
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent { HomeScreen()  }
//    }
//}
@Composable
fun Project(navController: NavHostController) {
    Box(
        modifier = Modifier
            .width(470.dp)
            .height(800.dp)
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ion_chevron_back),
            contentDescription = "ion:chevron-back",
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 10.dp, y = 121.dp)
                .requiredSize(size = 24.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 0.dp, y = 36.dp)
                .width(470.dp)
                .height(45.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(470.dp)
                    .height(45.dp)
            ) {
                Text(
                    text = "IORMS",
                    color = Color.Black,
                    lineHeight = 1.28.em,
                    style = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = 89.dp, y = 5.dp)
                        .width(69.dp)
                        .height(23.dp)
                )
                Divider(
                    color = Color(0xff988d8d),
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = 0.dp, y = 45.dp)
                        .requiredWidth(width = 470.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.your_app_logo),
                    contentDescription = "logo 1",
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(x = 7.dp, y = 0.2.dp)
                        .requiredSize(width = 70.dp, height = 32.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.humbleicons_logout),
                contentDescription = "humbleicons:logout",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 335.dp, y = 5.dp)
                    .height (23.dp).requiredWidth(width=34.dp)
                    .clickable { navController.navigate("LoginScreen") }
            )
//            Image(
//                painter = painterResource(id = R.drawable.octicon_search),
//                contentDescription = "octicon:search-16",
//                colorFilter = ColorFilter.tint(Color.Gray),
//                modifier = Modifier
//                    .align(alignment = Alignment.TopStart)
//                    .offset(x = 294.dp, y = 10.dp)
//                    .requiredSize(size = 21.dp)
//            )
            Text(
                text = "Logout",
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 21.67.em,
                style = TextStyle(fontSize = 10.sp),
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 330.dp, y = 30.dp)
                    .requiredSize(width = 35.dp, height = 11.dp)
            )
        }
        Box(
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .offset(x = (-1).dp, y = 118.dp)
                .width(324.dp)
                .height(32.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = Color(0xffe3e3e3).copy(alpha = 0.05f))
                .border(
                    border = BorderStroke(1.dp, Color(0xffe3e3e3).copy(alpha = 0.8f)),
                    shape = RoundedCornerShape(15.dp)
                )
        )
        Text(
            text = "Search",
            color = Color(0xffe3e3e3),
            lineHeight = 1.67.em,
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 54.dp, y = 125.dp)
        )
    }
}
//@Preview(showBackground = true)
//@Composable
//fun Previe(){
//    HomeScreen()
//}
