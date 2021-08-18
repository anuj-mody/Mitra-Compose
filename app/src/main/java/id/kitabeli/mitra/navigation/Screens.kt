package id.kitabeli.mitra.navigation

sealed class Screens(open val route:String=""){
    object Splash:Screens("splash")
    object Login:Screens("login")
    object Home:Screens("home")
}
