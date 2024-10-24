package com.iriver.ak.mycompose

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iriver.ak.mycompose.data.MyScreenDepth
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iriver.ak.mycompose.data.ScreenState

@Composable
fun MyApp(viewModel: ScreenViewModel = viewModel(),
    navController: NavHostController = rememberNavController()) {

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MyScreenDepth.valueOf(
        backStackEntry?.destination?.route ?: MyScreenDepth.DEPTH2.name
    )

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        // top action bar
        topBar = {
        MyAppBar(currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() })
    },
        // bottom bar
        bottomBar = {
            MyAppBottomBar(uiState.depth)
        }
    ) {
//        val uiState by viewModel.uiState.collectAsState()
        // contents
        NavHost(
            navController = navController,
            startDestination = MyScreenDepth.DEPTH1.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            composable(route = MyScreenDepth.DEPTH1.name
            ) {
                OnScreenDepth(
                    subtotal = MyScreenDepth.DEPTH1.name,
//                    subtotal = uiState.depth,
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onNextButtonClicked = { navController.navigate(MyScreenDepth.DEPTH2.name) },
                    onChangedDepth = {viewModel.setState(it)},
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = MyScreenDepth.DEPTH2.name) {
                OnScreenDepth(
                    subtotal = MyScreenDepth.DEPTH2.name,
//                    subtotal = uiState.depth,
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onNextButtonClicked = { navController.navigate(MyScreenDepth.DEPTH3.name) },
                    onChangedDepth = {viewModel.setState(it)},
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = MyScreenDepth.DEPTH3.name) {
                OnScreenDepth(
                    subtotal = MyScreenDepth.DEPTH3.name,
//                    subtotal = uiState.depth,
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onNextButtonClicked = { navController.navigate(MyScreenDepth.DEPTH4.name) },
                    onChangedDepth = {viewModel.setState(it)},
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = MyScreenDepth.DEPTH4.name) {
                OnScreenDepthEnd(
                    subtotal = MyScreenDepth.DEPTH4.name,
//                    subtotal = uiState.depth,
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onChangedDepth = {viewModel.setState(it)},
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}

@Composable
fun MyAppBar(
    currentScreen: MyScreenDepth,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        })
}

@Composable
fun MyAppBottomBar(
    depth: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(depth) },
        modifier = modifier,
        backgroundColor = Color.Green
    )
}

@Composable
fun OnScreenDepth(
    subtotal: String,
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    onChangedDepth:(String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var visible by remember { mutableStateOf(true) }
            Text("Welcome to the Compose $subtotal")
            Row {
                visible = if (subtotal == MyScreenDepth.DEPTH1.name) !visible else visible
                AnimatedVisibility(visible) {
                    Button(
                        modifier = Modifier.padding(vertical = 24.dp, horizontal = 10.dp),
                        onClick = onCancelButtonClicked,
                    ) {
                        Text(stringResource(R.string.go_to_first))
                    }
                }
                Button(
                    modifier = Modifier.padding(vertical = 24.dp),
                    onClick = onNextButtonClicked
                ) {
                    Text(stringResource(R.string.next))
                    onChangedDepth(subtotal)
                }
            }
        }
    }
}

@Composable
fun OnScreenDepthEnd(
    subtotal: String,
    onCancelButtonClicked: () -> Unit = {},
    onChangedDepth:(String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            onChangedDepth(subtotal)

            Text("Welcome to the Compose $subtotal")

            val names: List<String> = listOf("Item1", "Item2", "Item3", "Item4", "Item5", "Item6", "Item7")
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                for (name in names) {
                    CustomArrayItem(name = name)
                }
            }

            //// NEET TO TEST
//            LazyRow(modifier = Modifier.padding(vertical = 4.dp)) {
//                items(items = names) { name ->
//                    Greeting(name = name)
//                }
//            }

//            LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
//                items(items = names) { name ->
//                    CustomArrayItem(name = name)
//                }
//            }

            Button(
                modifier = Modifier.padding(vertical = 60.dp), onClick = onCancelButtonClicked
            ) {
                Text(stringResource(id = R.string.go_to_first))
            }
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Test Compose")
            Button(
                modifier = Modifier.padding(vertical = 24.dp), onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
private fun Greetings(names: List<String> = listOf("Item1", "Item2", "Item3", "Item4", "Item5", "Item6", "Item7")) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            CustomArrayItem(name = name)
        }
    }
}

@Composable
private fun CustomArrayItem(name: String) {
    val expanded = remember { mutableStateOf(false) }
//    val extraPadding = if (expanded.value) 48.dp else 0.dp
    val extraPadding by animateDpAsState(if (expanded.value) 48.dp else 0.dp)

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .wrapContentSize()
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(onClick = { expanded.value = !expanded.value }) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

private fun cancelOrderAndNavigateToStart(viewModel: ScreenViewModel, navController: NavHostController) {
    viewModel.resetState()
    navController.popBackStack(MyScreenDepth.DEPTH1.name, inclusive = false)
}

//------------------------------------------------
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview2() {
    MaterialTheme {
//        Greetings()
        MyApp()
    }
}
//------------------------------------------------