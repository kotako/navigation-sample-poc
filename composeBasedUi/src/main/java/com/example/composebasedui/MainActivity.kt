package com.example.composebasedui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.composebasedui.ui.theme.NavigationSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationSampleApp()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavigationSampleTheme {
        Greeting("Android")
    }
}

// NavHost(NavGraph) を管理
@Composable
fun NavigationSampleApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                onClick = {
                    navController.navigate("sub")
                }
            )
        }
        composable("sub", deepLinks = listOf(navDeepLink { uriPattern = "myapp://sub_compose" })) {
            SubScreen(
                onClick = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("myapp://main_activity")))
                }
            )
        }
    }
}

@Composable
fun MainScreen(onClick: () -> Unit) {
    NavigationSampleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.wrapContentSize()) {
                Greeting("Android")
                Button(onClick = onClick) {
                    Text("Navigate to SubScreen")
                }
            }
        }
    }
}

@Composable
fun SubScreen(onClick: () -> Unit) {
    NavigationSampleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.wrapContentSize()) {
                Greeting("Android")
                Greeting("SubScreen")
                Button(onClick = onClick) {
                    Text("Navigate to MainActivity(Activity)")
                }
            }
        }
    }
}