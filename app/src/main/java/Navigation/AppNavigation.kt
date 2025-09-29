package Navigation

import ChatScreen
import PersonalChatScreen
import View.LogInScreen
import View.RiskCodeScreen
import View.SignInScreen
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.main.CompReusable.CustomAlert
import com.example.main.Gallery_actions.GalleyScreen
import com.example.main.Location_Actions.GPSScreen
import com.example.main.Location_Actions.LocationScreen
import com.example.main.Location_Actions.LocationViewModel
import com.example.main.Screens.Menu.AddRiskZone
import com.example.main.Screens.HomeScreen
import com.example.main.Screens.Menu.ProfileScreen
import com.example.main.Screens.RecoverPassword.ChangePassWordScreen
import com.example.main.Screens.RecoverPassword.CodeVerificationScreen
import com.example.main.Screens.RecoverPassword.EmailVerificationScreen

@SuppressLint("ViewModelConstructorInComposable")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val locationViewModel = LocationViewModel()
    NavHost(navController= navController, startDestination = AppScreens.LogInScreen.name){



        composable(route = AppScreens.GPSScreen.name){
            GPSScreen(locationViewModel)
        }
        composable(route = AppScreens.LogInScreen.name){
            LogInScreen(navController)
        }
        composable(route = AppScreens.SignInScreen.name){
            SignInScreen(navController)
        }
        composable(route = AppScreens.RiskCodeScreen.name){
            RiskCodeScreen(navController)
        }
        composable(route = AppScreens.EmailVerificationScreen.name){
            EmailVerificationScreen(navController)
        }
        composable(route = AppScreens.HomeScreen.name){
            HomeScreen(navController)
        }
        composable(route = AppScreens.AddZoneRisk.name){
            AddRiskZone(navController)
        }
        composable(route = AppScreens.CodeVerificationScreen.name){
            CodeVerificationScreen(navController)
        }
        composable(route = AppScreens.ChangePassWordScreen.name){
            ChangePassWordScreen(navController)
        }
        composable(route = AppScreens.ProfileScreen.name){
            ProfileScreen(navController)
        }
        composable(route = AppScreens.ChatScreen.name){
            ChatScreen(navController)
        }
        composable(route = AppScreens.RiskZones.name){
            AddRiskZone(navController)
        }
        composable(route = AppScreens.PersonalChatScreen.name){
            PersonalChatScreen(navController)
        }

    }
}

