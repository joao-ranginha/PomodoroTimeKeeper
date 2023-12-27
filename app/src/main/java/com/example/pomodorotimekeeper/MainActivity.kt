package com.example.pomodorotimekeeper

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomodorotimekeeper.ui.theme.PomodoroTimeKeeperTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PomodoroTimeKeeperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PomodoroLayout()
                }
            }
        }
    }
}

@Composable
fun PomodoroLayout(modifier: Modifier = Modifier) {

    TimerAndButton()

}

@Composable
fun TimerAndButton(modifier: Modifier = Modifier) {

    var time by remember { mutableIntStateOf(60) }
    var timePause by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = time, key2 = timePause) {
        while (time > 0 && !timePause) {
            delay(1000L)
            time--
        }
    }


    /*
    var time by remember { mutableStateOf("") }
    val timeCounter = object: CountDownTimer(300000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            time = (millisUntilFinished / 1000 / 60).toString()
        }

        override fun onFinish() {
            var hey = "heççp"
        }
    }.start()
*/

    Column(
        modifier = modifier
            .padding(10.dp)
            .wrapContentSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$time",
            modifier = modifier
        )
        Spacer(modifier = modifier.height(100.dp))
        ElevatedButton(
            onClick = { timePause = !timePause },
            contentPadding = PaddingValues(1.dp),
            modifier = modifier
        ) {
            Text(
                text = if (timePause) "Start" else "Stop"
            )
        }
        ElevatedButton(
            onClick = { time = 60 },
            contentPadding = PaddingValues(1.dp),
            modifier = modifier
        ) {
            Text(
                text = "reste"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PomodoroTimeKeeperTheme {
        PomodoroLayout()
    }
}