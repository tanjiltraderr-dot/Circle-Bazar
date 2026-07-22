package com.example

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.animation.togetherWith
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import kotlinx.coroutines.delay
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState(pageCount = { 3 })
    val categoryState = rememberLazyListState()
    
    val searchHints = listOf("Search in Circle Bazar", "Smartphones", "Men's Sneakers", "Laptops", "Watches")
    var currentHintIndex by remember { mutableIntStateOf(0) }
    var remainingSeconds by remember { mutableIntStateOf(2 * 3600 + 15 * 60 + 30) }

    LaunchedEffect(Unit) {
        while (remainingSeconds > 0) {
            delay(1000)
            remainingSeconds--
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentHintIndex = (currentHintIndex + 1) % searchHints.size
        }
    }

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4000)
            if (!pagerState.isScrollInProgress) {
                val nextPage = (pagerState.currentPage + 1) % 3
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }
    
    LaunchedEffect(categoryState) {
        while (true) {
            delay(3000)
            if (!categoryState.isScrollInProgress) {
                val nextIndex = (categoryState.firstVisibleItemIndex + 1) % 5
                categoryState.animateScrollToItem(nextIndex)
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Banner & Search Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                // Banner spans full width and height
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    Image(
                        painter = painterResource(id = R.drawable.img_banner_1784718078976),
                        contentDescription = "Promotional Banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                
                // Dark gradient overlay at top to make status bar icons readable
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.5f), Color.Transparent)
                            )
                        )
                )

                // Search Bar placed inside the box, below status bar
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .clickable { navController.navigate("search") },
                        shape = RoundedCornerShape(22.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Gray)
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            AnimatedContent(
                                targetState = searchHints[currentHintIndex],
                                transitionSpec = {
                                    slideInVertically { height -> height } + fadeIn() togetherWith slideOutVertically { height -> -height } + fadeOut()
                                },
                                modifier = Modifier.weight(1f)
                            ) { hint ->
                                Text(text = hint, color = Color.Gray, fontSize = 14.sp)
                            }
                            
                            Icon(Icons.Default.CameraAlt, contentDescription = "Image Search", modifier = Modifier.size(20.dp), tint = Color.Gray)
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(Icons.Default.Mic, contentDescription = "Voice Search", modifier = Modifier.size(20.dp), tint = Color.Gray)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Categories
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Categories", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                Text(
                    "See All", 
                    color = MaterialTheme.colorScheme.primary, 
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { navController.navigate("category") }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                state = categoryState,
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val categories = listOf(
                    "Electronics" to Icons.Default.PhoneAndroid,
                    "Fashion" to Icons.Default.Checkroom,
                    "Beauty" to Icons.Default.Face,
                    "Home" to Icons.Default.Chair,
                    "Sports" to Icons.Default.SportsBasketball
                )
                items(categories.size) { index ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                categories[index].second,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(categories[index].first, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Circle Deals
            val circleDeals = remember { 
                List(5) { 
                    val isShoe = java.lang.Math.random() > 0.5
                    val left = (1..9).random()
                    val total = 10
                    val price = if (isShoe) "1,899" else "1,499"
                    val oldPrice = if (isShoe) "2,299" else "1,999"
                    val name = if (isShoe) "Sports Running Shoes" else "Haylou LS02 Smart Watch"
                    val discount = if (isShoe) "-25%" else "-30%"
                    CircleDealItem(isShoe, left, total, price, oldPrice, name, discount) 
                } 
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val infiniteTransition = rememberInfiniteTransition(label = "shimmer_transition")
                    val translateAnim by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 1000f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2000, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        ),
                        label = "shimmer"
                    )
                    val shimmerBrush = Brush.linearGradient(
                        colors = listOf(Color(0xFF2E7D32), Color(0xFFA5D6A7), Color(0xFF2E7D32)),
                        start = Offset(translateAnim - 200f, translateAnim - 200f),
                        end = Offset(translateAnim, translateAnim)
                    )
                    Text("CIRCLE DEALS", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, brush = shimmerBrush))
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    val h = remainingSeconds / 3600
                    val m = (remainingSeconds % 3600) / 60
                    val s = remainingSeconds % 60
                    val hStr = h.toString().padStart(2, '0')
                    val mStr = m.toString().padStart(2, '0')
                    val sStr = s.toString().padStart(2, '0')
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFFC107), RoundedCornerShape(4.dp))
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(hStr, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Text(":", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFFC107), RoundedCornerShape(4.dp))
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(mStr, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Text(":", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFFC107), RoundedCornerShape(4.dp))
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(sStr, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
                Text(
                    "Shop More", 
                    color = MaterialTheme.colorScheme.primary, 
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { navController.navigate("circle_deals") }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(circleDeals.size) { index ->
                    CircleDealProductCard(
                        item = circleDeals[index],
                        modifier = Modifier.width(140.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            // Just For You
            Text(
                "Just For You",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                 Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ProductCard(isShoe = false, modifier = Modifier.weight(1f))
                    ProductCard(isShoe = true, modifier = Modifier.weight(1f))
                 }
                 Spacer(modifier = Modifier.height(8.dp))
                 Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ProductCard(isShoe = true, modifier = Modifier.weight(1f))
                    ProductCard(isShoe = false, modifier = Modifier.weight(1f))
                 }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Animated Header that appears on scroll
        val showHeader by remember { derivedStateOf { scrollState.value > 10 } }
        AnimatedVisibility(
            visible = showHeader,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .height(48.dp)
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_app_logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Circle Bazar", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.weight(1f))
                    
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(isShoe: Boolean, modifier: Modifier = Modifier) {
    val hasDiscount = isShoe
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isShoe) R.drawable.img_product_shoe_1784718089233 
                                 else R.drawable.img_product_watch_1784718100549
                        ),
                        contentDescription = "Product",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                
                Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
                    Text(
                        text = if (isShoe) "Sports Running Shoes" else "Haylou LS02 Smart Watch",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                        minLines = 2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(if (isShoe) "৳ 1,899" else "৳ 1,499", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        if (hasDiscount) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("৳ 2,299", color = Color.Gray, fontSize = 12.sp, textDecoration = TextDecoration.LineThrough)
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
                            Text(if (isShoe) "4.7" else "4.5", fontSize = 10.sp, modifier = Modifier.padding(start = 2.dp), fontWeight = FontWeight.Bold, color = Color.Black)
                            Text(if (isShoe) "(150)" else "(320)", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(start = 2.dp, end = 2.dp))
                            Text("|", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(end = 2.dp))
                            Text(if (isShoe) "Sold 700+" else "Sold 100k+", fontSize = 10.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        
                        Spacer(modifier = Modifier.width(4.dp))
                        
                        // Cart Button
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Outlined.ShoppingCart,
                                contentDescription = "Add to Cart",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
            
            // Discount Badge
            if (hasDiscount) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(topStart = 8.dp, bottomEnd = 8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("-25%", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
            
            // Favorite Icon
            var isFavorite by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .background(Color.White.copy(alpha = 0.4f), CircleShape)
                    .clickable { isFavorite = !isFavorite },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

data class CircleDealItem(
    val isShoe: Boolean,
    val left: Int,
    val total: Int,
    val price: String,
    val oldPrice: String,
    val name: String,
    val discount: String
)

@Composable
fun CircleDealProductCard(item: CircleDealItem, modifier: Modifier = Modifier, showExtraDetails: Boolean = false) {
    var isFavorite by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                ) {
                    Image(
                        painter = painterResource(
                            id = if (item.isShoe) R.drawable.img_product_shoe_1784718089233 
                                 else R.drawable.img_product_watch_1784718100549
                        ),
                        contentDescription = "Product",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                
                Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 6.dp)) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                        minLines = 2,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("৳ ${item.price}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("৳ ${item.oldPrice}", color = Color.Gray, fontSize = 12.sp, textDecoration = TextDecoration.LineThrough)
                    }
                    
                    if (showExtraDetails) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(14.dp))
                                Text("4.7", fontSize = 10.sp, modifier = Modifier.padding(start = 2.dp), fontWeight = FontWeight.Bold, color = Color.Black)
                                Text("(150)", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(start = 2.dp, end = 2.dp))
                                Text("|", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(end = 2.dp))
                                Text("Sold 700+", fontSize = 10.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            
                            Spacer(modifier = Modifier.width(4.dp))
                            
                            // Cart Button
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Outlined.ShoppingCart,
                                    contentDescription = "Add to Cart",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(if (showExtraDetails) 4.dp else 6.dp))
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(Color(0xFFC8E6C9), RoundedCornerShape(2.dp))
                    )
                    
                    Spacer(modifier = Modifier.height(2.dp))
                    
                    Text(
                        "Only ${item.left} left",
                        color = Color(0xFF4CAF50),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
            }
            
            // Discount Badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(topStart = 8.dp, bottomEnd = 8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(item.discount, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            
            // Favorite Icon
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
                    .background(Color.White.copy(alpha = 0.4f), CircleShape)
                    .clickable { isFavorite = !isFavorite },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
