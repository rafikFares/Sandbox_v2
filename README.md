# Sandbox v2
## Branches :
###### Master : 
- **Realm** Version with the implementation of PagingSource
###### Room :
- **Room** Version with the default(from room) implementation of PagingSource
## Patterns and Libs : 
- The project use Gradle Version catalog FeaturePreview versions and dependencies are in `../gradle/libs.versions.tol`
- 95% of Gradle Scripts and Plugins are in `.kts`, all Plugins and Scripts are available in `build-logic` subModule
- Clean Architecture
- MVVM pattern
- Data Binding/ViewBinding
- Koin for dependency injection
- Retrofit for REST
- OkHttp as Client
- DataStore for Preferences
- Realm Kotlin SDK for local cache
- Room SDK for local cache (in `Room` branch)
- Material components for styling
- StateFlows for ui updates (and SharedFlows + LiveData)
- Coroutines everywhere
- Jetpack navigation
- Picasso 🖼
- UseCase Pattern for data management
- PagingSource/PagingAdapter/PagingData
- BottomSheet and RightSheet
- ~~LeakCanary~~ (disabled because PagingSource..)
- KSP for annotation processor
- Kotlin Serialization for serialization
- Jetpack SplashScreen api
- Lottie
- Night mode auto
- Tests : junit / mockk / robolectric / kluent 😍 / turbine / jetpack scenarios / espresso / custom rules / .....
- KtLint
- Jacoco
- Github Actions
- Maestro for more Instrumental tests 😍
- many other things ....
## Infos :
- `uiBox` module is used as a custom views module for the app
- `uiBox` module is used as a custom views module for the app
- ***USERNAME*** : `admin`
- ***PASSWORD*** : `admin`

## Coming next :
- Compose [Branch]
- ~~Hilt instead of Koin~~ [Branch] (won't use Hilt since it is always in Java)

## Todo :
- Replace loading by Facebook Shimmer
- maybe show more information in item detail view ?
- convert project to compose 🤩

## Api :
--

## Copyrights :
###### RightSheetBehavior :

https://github.com/OKatrych/RightSheetBehavior

###### Icon and SplashScreen icon :

https://developer.android.com/guide/topics/ui/splash-screen

###### Lotties :

[https://lottiefiles.com/97930-loading](https://lottiefiles.com/77359-refresh-update-reload-restart-sync-load)

[https://lottiefiles.com/112545-wumpus-hi](https://lottiefiles.com/112545-wumpus-hi)
