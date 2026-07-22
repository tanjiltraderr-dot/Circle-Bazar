package com.example

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val searchHints = listOf("Search in Circle Bazar", "Smartphones", "Men's Sneakers", "Laptops", "Watches", "Headphones")
    var currentHintIndex by remember { mutableIntStateOf(0) }
    
    val searchHistory = remember { 
        mutableStateListOf("Sports Running Shoes", "Smart Watch", "Lenovo Earbuds", "Backpack", "Mini Fan") 
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentHintIndex = (currentHintIndex + 1) % searchHints.size
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            
            // Search Box
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .background(Color.White, RoundedCornerShape(22.dp))
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        if (searchQuery.isEmpty()) {
                            AnimatedContent(
                                targetState = searchHints[currentHintIndex],
                                transitionSpec = {
                                    slideInVertically { height -> height } + fadeIn() togetherWith slideOutVertically { height -> -height } + fadeOut()
                                },
                                label = "hint_animation"
                            ) { hint ->
                                Text(text = hint, color = Color.Gray, fontSize = 14.sp)
                            }
                        }
                        BasicTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
                            singleLine = true,
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { searchQuery = "" },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color.Gray, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            IconButton(onClick = { /* Image Search */ }, modifier = Modifier.size(36.dp)) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Image Search", tint = Color.DarkGray)
            }
            
            IconButton(onClick = { /* Voice Search */ }, modifier = Modifier.size(36.dp)) {
                Icon(Icons.Default.Mic, contentDescription = "Voice Search", tint = Color.DarkGray)
            }
        }
        
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
        
        // Search History
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent Searches", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                TextButton(onClick = { searchHistory.clear() }, contentPadding = PaddingValues(0.dp)) {
                    Text("Clear All", color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn {
                items(searchHistory.size) { index ->
                    val historyItem = searchHistory[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { searchQuery = historyItem }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.History, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(historyItem, fontSize = 14.sp, modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { searchHistory.removeAt(index) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.Gray, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}
