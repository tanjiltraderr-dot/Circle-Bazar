package com.example

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Categories", fontWeight = FontWeight.Bold) })
        }
    ) { padding ->
        Row(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Sidebar for main categories
            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                val cats = listOf("For You", "Electronics", "Fashion", "Home", "Beauty", "Sports")
                cats.forEachIndexed { index, name ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (index == 1) MaterialTheme.colorScheme.background else Color.Transparent)
                            .clickable { }
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            name,
                            color = if (index == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (index == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
            // Subcategories
            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                Text("Electronics", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                // Grid of items mocked
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    CategoryItem("Smartphones", Icons.Default.PhoneAndroid)
                    CategoryItem("Laptops", Icons.Default.Laptop)
                    CategoryItem("Watches", Icons.Default.Watch)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    CategoryItem("Audio", Icons.Default.Headphones)
                    CategoryItem("Cameras", Icons.Default.CameraAlt)
                    CategoryItem("Gaming", Icons.Default.VideogameAsset)
                }
            }
        }
    }
}

@Composable
fun CategoryItem(name: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { }) {
        Box(
            modifier = Modifier.size(64.dp).background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(name, fontSize = 12.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Cart", fontWeight = FontWeight.Bold) })
        },
        bottomBar = {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface).padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total (2 items)", color = Color.Gray)
                    Text("$295.00", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Proceed to Checkout", fontSize = 16.sp)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item { CartItem(R.drawable.img_product_shoe_1784718089233, "Premium Urban Sneaker", "$120.00", 1) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CartItem(R.drawable.img_product_watch_1784718100549, "Luxury Smartwatch", "$175.00", 1) }
        }
    }
}

@Composable
fun CartItem(imageRes: Int, title: String, price: String, qty: Int) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, maxLines = 1)
                Spacer(modifier = Modifier.height(4.dp))
                Text(price, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {}, modifier = Modifier.size(24.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)) {
                        Icon(Icons.Default.Remove, contentDescription = "-", modifier = Modifier.size(16.dp))
                    }
                    Text(qty.toString(), modifier = Modifier.padding(horizontal = 16.dp), fontWeight = FontWeight.Bold)
                    IconButton(onClick = {}, modifier = Modifier.size(24.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape)) {
                        Icon(Icons.Default.Add, contentDescription = "+", modifier = Modifier.size(16.dp))
                    }
                }
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.DeleteOutline, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("My Orders", fontWeight = FontWeight.Bold) }) }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp)) {
            item { OrderItem("ORD-892348", "Delivered", "$120.00", "Oct 12, 2023", true) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { OrderItem("ORD-892349", "Processing", "$240.00", "Oct 15, 2023", false) }
        }
    }
}

@Composable
fun OrderItem(id: String, status: String, total: String, date: String, isDelivered: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(id, fontWeight = FontWeight.Bold)
                Text(
                    status,
                    color = if (isDelivered) MaterialTheme.colorScheme.primary else Color(0xFFF57C00),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Date: $date", color = Color.Gray, fontSize = 14.sp)
                Text("Total: $total", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                Text("Track Order")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_avatar_1784718110969),
                        contentDescription = "Avatar",
                        modifier = Modifier.size(80.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Sarah Jenkins", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("sarah.jenkins@example.com", color = Color.White.copy(alpha = 0.8f))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Menu Items
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileMenuItem(Icons.Default.ShoppingBag, "My Orders")
                ProfileMenuItem(Icons.Default.FavoriteBorder, "Wishlist")
                ProfileMenuItem(Icons.Default.AccountBalanceWallet, "Wallet & Payments")
                ProfileMenuItem(Icons.Default.LocalOffer, "Coupons & Offers")
                ProfileMenuItem(Icons.Default.LocationOn, "Shipping Addresses")
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                ProfileMenuItem(Icons.Default.Settings, "Settings")
                ProfileMenuItem(Icons.Default.HelpOutline, "Help & Support")
                
                Spacer(modifier = Modifier.height(24.dp))
                
                TextButton(
                    onClick = {
                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout", color = MaterialTheme.colorScheme.error, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
    }
}
