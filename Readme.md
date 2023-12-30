# Pomodoro Time Keeper
#### Video Demo:  <URL HERE>
#### Description:

Pomodoro Time Keeper is a app based on the Pomodoro focus technique, 
to keep track of your pomodoros.
The Pomodoro technique consists of intervals of work, typically 30 minutes in length,
separated by short breaks, between 5 and 10 minutes.

Pomodoro Time Keeper allows you to keep track of the time in your interval while you do your tasks.
You can change the time of the work interval between 25, 30 and 45 minutes, the most common size
of intervals, also said to be the longest time a human can focus continuously.
You can also change the break time from 3,5 and 10 minutes, max at 10 minutes so that the temptation
of a long break is not there.
You can reset, skip and stop the current timer.

In MainActivity.kt we define the logic of the application, using the JetPack Compose framework for
android we build the app with @Composable functions which allows us to make our UI in a declarative
way. For this i had to learn the Kotlin language and Jetpack Compose framework, i made use of the
learning material from Android training courses (https://developer.android.com/courses).

#### A basic overview

A Composable function is a section of your UI which itself is made of other composable functions
with more specific functions such as: 
```
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    
}
```

Text() which displays text on the screen
```
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Text(
        text = "Hello World"
    )
}
```



Column() which arranges components within itself in a column
```
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Column() {
        Text(
            text = "Hello"
        )
        Text(
            text = "Hello"
        )
    }
}
```

Button() which provides a button that executes a piece of code when clicked
```
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Column() {
        Text(
            text = "Hello"
        )
        Text(
            text = "Hello"
        )
    }
}
```

#### A more in depth view

The timer is made by calling LaunchEffect()
```
var timeSeconds by remember { mutableStateOf(totalTime.pomodoroTime) }
        if (timeSeconds == 0) {
            timePause = true
            currentPomodoro = false
        }

        LaunchedEffect(key1 = timeSeconds, key2 = timePause) {
            while (timeSeconds > 0 && !timePause) {
                delay(1000L)
                timeSeconds--
            }
        }
```
Usualy the code to define the UI is called to executed only once, until some variable being tracked
by the UI is changed (like when you press a letter key, it reloads the UI to show the press key)
LaunchedEffect() however provides a way to launch a side-effect in compose in a block called corout-
ine that does not affect the recomposition of the UI. The total number of seconds is defined in
`timeSeconds` and each second we subtract on from it, giving us ouw timer.

Below will be a simplified version of the code to display the timer and it's controls buttons
for Start/Stop Reset and Skip. Note the `Column()` function wich will display the elements inside it
in a vertical fashion.

```
Column() {
            Text(text = "Work")
            
            Text(
                text = TimeDisplay(timeSeconds),
                fontSize = 100.sp,
                fontFamily = FontFamily.Monospace
            )
            
            Spacer(modifier = modifier.height(100.dp))
            
            ElevatedButton(
                onClick = { timePause = !timePause },
            ) {
                Text(
                    text = if (timePause) "Start" else "Stop"
                )
            }
            
            ElevatedButton(
                onClick = {
                    timeSeconds = totalTime.pomodoroTime
                    timePause = true
                }
            ) {
                Text(
                    text = "Reset"
                )
            }
            
            OutlinedButton(
                onClick = {
                    currentPomodoro = !currentPomodoro
                }
            ) {
                Text(
                    text = "Skip"
                )
            }
        }
```
A `when` statement is used to switch between work mode and break mode, they contain the same logic
but reference different `timeSeconds`.
```
when (mode) { 
    "WORK" -> // work UI and logic
    "BREAK" -> // break UI and logic
}
```
Next is the menu to select the time of the work and break sections. Each menu is a button that calls
a DropdownMenu()(a function provided with jetpack compose). `expanded = viewPomodoroMenu` is a Bool-
ean value, when it's true the menu becomes visible when you click outside of the menu it becomes fa-
lse and the menu disappears. 
```
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
```
For `each DropdownMenuItem()` the `onClick` calls a piece of code inside when it's clicked.
It changes the value of the data class instance `totalTime.pomodoroTime`, setting each to the corre-
spondent amount of seconds according to the option. It also sets which selected item was clicked to
render a check mark trailing icon on the clicked option.
The same thing is done for the Break section time options.

The `data class TotalTime` takes the data changed in TimeSelectMenus() and give it to TimerAndButton()
```
data class TotalTime(
    var pomodoroTime: Int = 30*60,
    var breakTime: Int = 5*60
)
```