import Navigation.AppScreens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.main.utils.theme.BoldOrange
import com.example.main.utils.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalChatScreen( navController: NavController) {
    var message by remember { mutableStateOf(TextFieldValue("")) }
    val mensajes = remember { mutableStateListOf("Tengo una emergencia Emanuel...") }

    Scaffold(
        topBar = {

                TopAppBar(
                    colors = TopAppBarColors(
                        containerColor = BoldOrange,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        scrolledContainerColor = Color.White,
                        actionIconContentColor = Color.White
                    ),

                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(Color(0xFFDDDDDD), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = "Persona", tint = Color.Black)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Diego Ruiz", color = Color.White, fontSize = 18.sp)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Call, contentDescription = "Llamar", tint = Color.White)
                        }
                    },
                )


        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Escribe un mensaje...") },
                    leadingIcon = {
                        Icon(Icons.Default.Add, contentDescription = "Añadir", tint = Orange)
                    },
                    trailingIcon = {
                        Icon(Icons.Default.LocationOn, contentDescription = "Ubicación", tint = Orange)
                    },
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Orange, CircleShape)
                        .clickable {
                            if (message.text.isNotBlank()) {
                                mensajes.add(message.text)
                                message = TextFieldValue("")
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Enviar", tint = Color.White)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(17.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                mensajes.forEach { msg ->
                    ChatBubble(text = msg)
                }
            }
        }
    }
}

@Composable
fun ChatBubble(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 80.dp, top = 8.dp, bottom = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFDDDDDD), RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            Text(text, color = Color.Black)
        }
    }
}
