package com.codesui.wallpaperapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codesui.wallpaperapp.ads.AdmobBanner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.chromium.support_lib_boundary.ProcessGlobalConfigConstants.ProcessGlobalConfigMapKey

@Composable
fun NavDrawer(navController: NavController, runAds: () -> Unit, openAds: () -> Unit, rewardedAds: () -> Unit) {
    val ctx = LocalContext.current
    val rateManager = RateManager()
    val drawerItems = listOf(
        DrawerItems(Icons.Rounded.Star, "Rate Us",  clickFunction ={
            rateManager.rateApp(ctx)
        }) ,
        DrawerItems(Icons.Rounded.Share, "Share With Friends", clickFunction = {
            ShareManager(ctx)
        }),
        DrawerItems(Icons.Rounded.MoreVert, "More Apps", clickFunction = {
            UrlManager(ctx.getString(R.string.more_apps_url), ctx)
        })
    )

    var selectedItem by remember {
        mutableStateOf(drawerItems[0])
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column (
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.onPrimary,
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        IconButton(
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .padding(5.dp)
                                .align(alignment = Alignment.TopEnd),
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                                openAds.invoke()
                            }) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Drawer Icon",
                                modifier = Modifier
                                    .fillMaxSize(),
                                tint = Color.White
                            )
                        }
                        Column (
                            Modifier.wrapContentSize(),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(130.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.FillBounds,
                                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                                contentDescription = stringResource(id = R.string.app_name)
                            )
                            Text(
                                text = stringResource(id = R.string.app_name),
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Divider(
                            Modifier.align(Alignment.BottomCenter),
                            thickness = 1.dp,
                            Color.DarkGray
                        )
                    }

                    drawerItems.forEach {
                        NavigationDrawerItem(
                            label = { Text(text = it.text) },
                            selected = it  == selectedItem,
                            onClick = {
                                selectedItem = it
                                scope.launch {
                                    drawerState.close()
                                }

                                it.clickFunction?.invoke()
                            },
                            modifier = Modifier.padding(horizontal = 16.dp),
                            icon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = it.text
                                )
                            },
                        )
                    }
                }
            }

        }, drawerState = drawerState) {
        Column {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.onPrimary,
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ){
                IconButton(
                    modifier =
                    Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .padding(5.dp)
                        .align(alignment = Alignment.TopStart),
                    onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                        runAds.invoke()
                    }) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Drawer Icon",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = "Aura Backgrounds",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                )
            }
            PhotosScreen(navController,runAds, rewardedAds)
        }
    }
}

data class DrawerItems(
    val icon: ImageVector,
    val text: String,
    val clickFunction: (() -> Unit)? = null
)