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

A Composable function is a section of your UI which itself is made of other composable functions
with more specific functions such as: Text() which displays text on the screen
Column() which arranges components within itself in a column
and
Button() which provides a button that executes a piece of code when clicked

The timer is made by calling LaunchEffect() TODO:
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
The Data Class TotalTime is used to take the data from Menus and give it to Timer TODO: