## About the game
"Save the words" aka "Falling words" is a small language learning game for Android. As soon as the game starts, an English word will be
shown at the bottom of the screen. A word from another language (For ex: Spanish) falls down from the top. The player should tap 
the green correct button if the falling word is a correct translation of the English word or the red wrong button if the falling word is an
incorrect translation of the English word.

The fun part is that the speed of the falling words will increase dynamically through the game as the player scores more points.
In a gaming session, the player will be given a maximum of 3 lives. The game ends when all the lives are exhausted or when all the
words are exhausted.

#### Note
The UI of the app is not polished. I'm still working on it.

## Time Spent (7.5 hours)
- 3 hours
  - Game Screen Development
  - UI logic including Animations
  - ViewModel logic
  - Live Score & Life(s) updates
- 1 hour
  - Setting up DI
  - Setting up Retrofit
  - Refactoring modules
  - Refactoring packages
- 1 hour
  - Writing Data layer code(Repository)
  - Writing Domain layer(Use case)
- 1 hour
  - Writing unit tests
  - Writing UI tests
- 30 minutes
  - Creating welcome screen
  - Creating game result screen
- 30 minutes
  - Resource refactoring - Using standard styles
  - Optimizing layout code
- 30 minutes
  - Troubleshooting issues
  - Fixing bugs found during self-testing

## If I had more time
- I would improve the UI of the app. Currently, it's just a skeleton of the UI.
- Complete the TODOs I added in the codebase - Error handling, Add more test cases.
- Improve the visual feedback - Subtle animations, Material effects.
- Improve the UX of the app - Adding transitions between screens.
- Add support for more languages.
- Add persistent storage(Ex: Room DB) support to record high scores, store the words locally.
- Add notifications to encourage users to play regularly.
- Add audio feedback to the game.

## Stack
#### Frameworks/Libraries Used
- [Coroutines](https://developer.android.com/kotlin/coroutines) - Performing asynchronous code with
  sequential manner.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - The DI framework.
- [Flow](https://developer.android.com/kotlin/flow) - Reactive streams.
- [Fragment](https://developer.android.com/guide/fragments) - The UI host.
- [Lifecycle](https://developer.android.com/topic/libraries/architecture/coroutines) - Android's
  component lifecycle teams up with coroutines.
- [View Binding](https://developer.android.com/topic/libraries/view-binding) - Providing safe access
  to view.
- [Data Binding](https://developer.android.com/topic/libraries/data-binding) - Enables binding UI
  components in layouts to data sources using a declarative format.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Presenter with
  its semi data persistence behavior and consisting of UI logic.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for making network calls.
- [Espresso](https://developer.android.com/training/testing/espresso) - Android UI testing framework.

## To install the app
[APK Link](https://drive.google.com/file/d/11tdq-9RGFUigxm9AeGv50QXyBZpaNts6/view?usp=sharing)

## App Demo
[Demo Link](https://drive.google.com/file/d/17YJcxFs1llRRpZ8GQOXOSCO3-z2YbnVu/view?usp=sharing)

