package com.sedatkaganaktas.pipeapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.ImagePainter.State.Empty.painter
import com.sedatkaganaktas.pipeapp.R
import com.sedatkaganaktas.pipeapp.model.PipeListItem
import com.sedatkaganaktas.pipeapp.viewmodel.PipeListViewModel

@Composable
fun PipeListScreen(
    navController: NavController,
    viewModel: PipeListViewModel = hiltViewModel()
) {
    Surface(
        color = MaterialTheme.colors.secondary,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(5.dp)) // Üstteki boşluğu ayarlar

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .padding(top = 5.dp)
                    .padding(bottom = 5.dp)


            )
            Text("Pipe App",modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
                textAlign = TextAlign.Right,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.searchPipeList(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            PipeList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }
        )
        if(isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
fun PipeList(
    navController: NavController,
    viewModel: PipeListViewModel = hiltViewModel()
) {
    val cryptoList by remember { viewModel.pipeList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    PipeListView(pipes = cryptoList,navController = navController)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if(errorMessage.isNotEmpty()) {
            RetryView(error = errorMessage) {
                viewModel.loadPipes()
            }
        }
    }

}

@Composable
fun PipeListView(pipes: List<PipeListItem>, navController: NavController) {
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(pipes) { pipe ->
            PipeRow(navController = navController,pipe = pipe)


        }
    }
    // dönülecek
}






@Composable
fun PipeRow(navController: NavController, pipe: PipeListItem) {
    Box() {}
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.secondary)
        .clickable {
            navController.navigate(
                "pipe_detail_screen/${pipe.pipe}/${pipe.price}"
            )
        }
    ) {
        Text(text = pipe.pipe,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
        Text(text = pipe.price,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(2.dp),
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
fun RetryView(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}