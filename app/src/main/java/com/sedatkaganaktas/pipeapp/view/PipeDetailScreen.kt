package com.sedatkaganaktas.pipeapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.sedatkaganaktas.pipeapp.model.Pipe
import com.sedatkaganaktas.pipeapp.util.Resource
import com.sedatkaganaktas.pipeapp.viewmodel.PipeDetailViewModel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PipeDetailScreen(
    id:String,
    price:String,
    navController: NavController,
    viewModel: PipeDetailViewModel = hiltViewModel()
) {
    /*

    //Step 1 -> Wrong

       val scope = rememberCoroutineScope()
       //Stateless
       //var cryptoItem : Resource<Crypto> = Resource.Loading()

       //Stateful
       var cryptoItem by remember { mutableStateOf<Resource<Crypto>>(Resource.Loading())}

         scope.launch {
             cryptoItem = viewModel.getCrypto(id)
             println(cryptoItem.data)
         }

      */

    /*
       //Step 2 -> Better

       //Stateless
          //var cryptoItem : Resource<Crypto> = Resource.Loading()

          //Stateful
          var cryptoItem by remember { mutableStateOf<Resource<Crypto>>(Resource.Loading())}

            LaunchedEffect(key1 = Unit) {
                cryptoItem = viewModel.getCrypto(id)
                println(cryptoItem.data)
            }
    */

    //Step 3 -> Best

    val pipeItem = produceState<Resource<Pipe>>(initialValue = Resource.Loading()) {
        value = viewModel.getPipe(id)
    }.value


    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.secondary),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when(pipeItem) {

                is Resource.Success -> {
                    val selectedCrypto = pipeItem.data!![0]
                    Text(text = selectedCrypto.name,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center
                    )

                    Image(painter = rememberImagePainter(data = selectedCrypto.logo_url),
                        contentDescription = selectedCrypto.name,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(200.dp, 200.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )

                    Text(text = price,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primaryVariant,
                        textAlign = TextAlign.Center

                    )

                }

                is Resource.Error -> {
                    Text(pipeItem.message!!)
                }

                is Resource.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                }

            }

        }
    }
}