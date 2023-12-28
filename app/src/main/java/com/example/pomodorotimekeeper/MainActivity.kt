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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.InteropView
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
    TimeSelectMenus()

}

@Composable
fun TimerAndButton(modifier: Modifier = Modifier) {

    var timeSeconds by remember { mutableIntStateOf(10) }
    var timePause by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = timeSeconds, key2 = timePause) {
        while (timeSeconds > 0 && !timePause) {
            delay(1000L)
            timeSeconds--
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
            text = TimeDisplay(timeSeconds),
            modifier = modifier,
            fontSize = 100.sp,
            fontFamily = FontFamily.Monospace
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
            onClick = { timeSeconds = 300 },
            contentPadding = PaddingValues(1.dp),
            modifier = modifier
        ) {
            Text(
                text = "Reset"
            )
        }
    }
}

fun TimeDisplay(totalSeconds: Int): String  {

    val min = (totalSeconds / 60).toString()
    val sec = if ((totalSeconds % 60).toString() == "0") "00" else (totalSeconds % 60).toString()

    return "$min:$sec"
}

@Composable
fun TimeSelectMenus(modifier: Modifier = Modifier) {
    var view by remember { mutableStateOf(false) }

    Row(modifier = Modifier
        .wrapContentSize()
        .width(300.dp)
        .background(Color.Yellow),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Button(onClick = { view = true }) {
            Text(text = "munu")
        }

        DropdownMenu(
            expanded = view,
            onDismissRequest = { view = false }) {

            DropdownMenuItem(text = { Text(text = "hello") }, onClick = { /*TODO*/ })
            DropdownMenuItem(text = { Text(text = "hello") }, onClick = { /*TODO*/ })
            DropdownMenuItem(text = { Text(text = "hello") }, onClick = { /*TODO*/ })

        }


        Button(onClick = { println("98") }) {
            Text(text = "mana")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PomodoroTimeKeeperTheme {
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