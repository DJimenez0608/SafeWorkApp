import Navigation.AppScreens
import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.main.utils.theme.Orange

@Composable
fun ChatScreen(navController: NavController) {
    val chatList = listOf(
        ChatItem("Diego Ruiz", notifications = true),
        ChatItem("Alejandro Cortez", notifications = false),
        ChatItem("Arantxa Hernandez", notifications = false),
        ChatItem("Sofia Campos", notifications = false)
    )

    var search by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
                .padding(innerPadding)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        Modifier.clickable { navController.popBackStack() }
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "Chats",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row {
                    IconButton(onClick = { /* notificaciones */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notificaciones"
                        )
                    }
                    IconButton(onClick = { /* menú */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Barra de búsqueda
            OutlinedTextField(

                value = search,
                onValueChange = { search = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                placeholder = { Text("Buscar...") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color(0xff5CBBFF)
                    )
                },
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Orange,
                    unfocusedBorderColor = Orange
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Lista de chats
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                chatList.forEach { chat ->
                    CustomCard(
                        name = chat.name,
                        notifications = chat.notifications,
                        onClick = {
                            navController.navigate(AppScreens.PersonalChatScreen.name)
                        }
                    )
                }
            }
        }
    }
}

data class ChatItem(
    val name: String,
    val notifications: Boolean
)

@Composable
fun CustomCard(
    name: String,
    notifications: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xffF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono de usuario
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xffDDDDDD), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Usuario",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            // Nombre + notificación
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = name)

                if (notifications) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .background(Color(0xffFF4B4B), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "2",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
