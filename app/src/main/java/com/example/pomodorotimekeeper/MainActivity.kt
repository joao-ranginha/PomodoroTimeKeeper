package com.example.pomodorotimekeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomodorotimekeeper.resourcelocal.TotalTime
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

    val x by remember {mutableStateOf(TotalTime())}

    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimerAndButton(totalTime = x)
        TimeSelectMenus(totalTime = x)
    }


}

@Composable
fun TimerAndButton(totalTime: TotalTime, modifier: Modifier = Modifier) {


    var timePause by remember { mutableStateOf(true) }
    var currentPomodoro by remember { mutableStateOf(true) }



    if (currentPomodoro) {
        var timeSeconds by remember { mutableStateOf(totalTime.pomodoroTime) }
        if (timeSeconds == 0) {
            currentPomodoro = false
        }

        LaunchedEffect(key1 = timeSeconds, key2 = timePause) {
            while (timeSeconds > 0 && !timePause) {
                delay(1000L)
                timeSeconds--
            }
        }

        Column(
            modifier = modifier
                .padding(10.dp)
                .wrapContentSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Work")
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
                onClick = {
                    timeSeconds = totalTime.pomodoroTime
                    timePause = true
                },
                contentPadding = PaddingValues(1.dp),
                modifier = modifier
            ) {
                Text(
                    text = "Reset"
                )
            }
            OutlinedButton(
                onClick = {
                    currentPomodoro = !currentPomodoro
                    timePause = true
                },
                contentPadding = PaddingValues(1.dp),
                modifier = modifier
            ) {
                Text(
                    text = "Skip"
                )
            }
        }
    } else {
        var timeSeconds by remember { mutableStateOf(totalTime.breakTime) }
        if (timeSeconds == 0) {
            currentPomodoro = true
        }

        LaunchedEffect(key1 = timeSeconds, key2 = timePause) {
            while (timeSeconds > 0 && !timePause) {
                delay(1000L)
                timeSeconds--
            }
        }

        Column(
            modifier = modifier
                .padding(10.dp)
                .wrapContentSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Break")
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
                onClick = {
                    timeSeconds = totalTime.breakTime
                    timePause = true
                },
                contentPadding = PaddingValues(1.dp),
                modifier = modifier
            ) {
                Text(
                    text = "Reset"
                )
            }
            OutlinedButton(
                onClick = {
                    currentPomodoro = !currentPomodoro
                    timePause = true
                },
                contentPadding = PaddingValues(1.dp),
                modifier = modifier
            ) {
                Text(
                    text = "Skip"
                )
            }

        }
    }
}



@Composable
fun TimeSelectMenus(totalTime: TotalTime, modifier: Modifier = Modifier) {
    var viewPomodoroMenu by remember { mutableStateOf(false) }
    var viewBreakMenu by remember { mutableStateOf(false) }

    var pomodoroSelectedItem by remember { mutableStateOf("30MIN") }
    var breakSelectedItem by remember { mutableStateOf("5MIN") }

    fun IsSelected():@Composable ()->Unit {
        return {Icon(imageVector = Icons.Rounded.Check, contentDescription = null)}
    }

    Row(modifier = Modifier
        .wrapContentSize()
        .width(300.dp),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Box {
            OutlinedButton(onClick = { viewPomodoroMenu = true }) {
                Text(text = "Pomodoro Time")
            }
            DropdownMenu(
                expanded = viewPomodoroMenu,
                onDismissRequest = { viewPomodoroMenu = false }) {

                    DropdownMenuItem(text = { Text(text = "45 min") },
                        onClick = { totalTime.pomodoroTime = 2700; pomodoroSelectedItem = "45MIN" },
                        trailingIcon = when (pomodoroSelectedItem) {
                            "45MIN" -> IsSelected()
                            else -> null
                        }
                    )
                    DropdownMenuItem(text = { Text(text = "30 min") },
                        onClick = { totalTime.pomodoroTime = 1800; pomodoroSelectedItem = "30MIN" },
                        trailingIcon = when (pomodoroSelectedItem) {
                            "30MIN" -> IsSelected()
                            else -> null
                        }
                    )
                    DropdownMenuItem(text = { Text(text = "25 min") },
                        onClick = { totalTime.pomodoroTime = 1500; pomodoroSelectedItem = "25MIN" },
                        trailingIcon = when (pomodoroSelectedItem) {
                            "25MIN" -> IsSelected()
                            else -> null
                        }
                    )
                }
        }


        Box {
            OutlinedButton(onClick = { viewBreakMenu = true }) {
                Text(text = "Break Time")
            }
            DropdownMenu(
                expanded = viewBreakMenu,
                onDismissRequest = { viewBreakMenu = false }) {

                    DropdownMenuItem(text = { Text(text = "10 min") },
                        onClick = { totalTime.breakTime = 600; breakSelectedItem = "10MIN" },
                        trailingIcon = when (breakSelectedItem) {
                            "10MIN" -> IsSelected()
                            else -> null
                        }
                    )
                    DropdownMenuItem(text = { Text(text = "5 min") },
                        onClick = { totalTime.breakTime = 300; breakSelectedItem = "5MIN" },
                        trailingIcon = when (breakSelectedItem) {
                            "5MIN" -> IsSelected()
                            else -> null
                        }
                    )
                    DropdownMenuItem(text = { Text(text = "3 min") },
                        onClick = { totalTime.breakTime = 180; breakSelectedItem = "3MIN" },
                        trailingIcon = when (breakSelectedItem) {
                            "3MIN" -> IsSelected()
                            else -> null
                        }
                    )
            }
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

fun TimeDisplay(totalSeconds: Int): String  {

    val min = (totalSeconds / 60).toString().padStart(2,'0')
    val sec =  (totalSeconds % 60).toString().padStart(2,'0')

    return "$min:$sec"
}