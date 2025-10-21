package View

import Navigation.AppScreens
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.main.CompReusable.ReusableButton
import com.example.main.ViewModel.UserAuthViewModel
import com.example.main.auth
import com.example.main.utils.theme.LightOrange
import com.example.practica.R

@Composable
fun LogInScreen (controller: NavController, viewModel: UserAuthViewModel = viewModel ()){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val userState by viewModel.user.collectAsState()
    val context = LocalContext.current

    Scaffold (
        modifier = Modifier.padding()
    ){ innerPadding ->

        Column (

            modifier = Modifier
                .padding(horizontal = 22.dp)
                .padding(innerPadding)
                .clickable (
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }

                ){
                    focusManager.clearFocus()
                },

            horizontalAlignment = Alignment.CenterHorizontally

        ){

            Image(
                modifier = Modifier.padding(top = 63.dp ).width(244.dp).height(279.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de safety life",
            )
            Text(
                "SafeWorkApp",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                )
            )
            Spacer(Modifier.height(60.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Userrname") },
                value = username,
                onValueChange = {username = it},
                supportingText = {
                    Text(userState.emailError, color = Color.Red)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),

            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                value = password,
                onValueChange = {password = it},
                supportingText = {
                    Text(userState.passError, color = Color.Red)
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),



            )
            Text(
                "Olvide la contraseña",

                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 11.dp)
                    .clickable {
                        controller.navigate(AppScreens.EmailVerificationScreen.name)
                    },
                style =TextStyle(
                    color = LightOrange,
                    fontSize = 12.sp,
                )

            )
            Spacer(Modifier.height(20.dp))
            ReusableButton(
                label = "Entrar",
                onClick = {
                    login(username, password, controller, context, viewModel)
                }
            )

            Spacer(Modifier.height(10.dp))
            Row {
                Text(
                    "¿No tiene cuenta? ",
                    style = TextStyle(
                        fontSize = 12.sp,
                    )
                )
                Text(
                    "Registrarme",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = LightOrange
                    ),
                    modifier = Modifier
                        .clickable {
                            controller.navigate(AppScreens.SignInScreen.name)
                        }
                )
            }

        }

    }
}

fun validateForm(model: UserAuthViewModel, email:String, password:String):Boolean {
    if (email.isEmpty()) {
        model.updateEmailError("Email is empty")
        return false
    } else {
        model.updateEmailError("")
    }
    if (!validEmailAddress(email)) {
        model.updateEmailError("Not a valid address")
        return false
    } else {
        model.updateEmailError("")
    }
    if (password.isEmpty()) {
        model.updatePassError("Password is empty")
        return false
    } else {
        model.updatePassError("")
    }
    if (password.length < 6) {
        model.updatePassError("Password is too short")
        return false
    } else {
        model.updatePassError("")
    }
    return true

}
 fun validEmailAddress(email:String):Boolean{
    val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return email.matches(regex.toRegex())
}
fun login(email:String, password:String, controller: NavController, context: Context, viewModel: UserAuthViewModel){
    if(validateForm(viewModel,email, password)){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                controller.navigate(AppScreens.RiskCodeScreen.name)
            }else{
                Toast.makeText(context, "Login error ${it.exception.toString()}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}
