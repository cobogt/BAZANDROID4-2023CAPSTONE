package com.jgt.wizelinebaz2023.presentation.components.movies

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jgt.wizelinebaz2023.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.wizelinebaz2023.domain.MoviesViewModel
import com.jgt.wizelinebaz2023.domain.models.MovieList
import com.jgt.wizelinebaz2023.storage.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun MovieListComponent( category: String ) {
    // Enlace desde estado del Store y hacia Dispatcher
    val currentActivity = LocalContext.current as Activity
    val viewModel: MoviesViewModel = (currentActivity as ActivityWithViewModelStoreInterface)
        .viewModelStateStore as MoviesViewModel

    var errorMessage    by remember { mutableStateOf("") }
    var movieList       by remember { mutableStateOf( MovieList() ) }
    var refreshing      by remember { mutableStateOf(false) }
    var runCoroutineInt by remember { mutableStateOf( 0 ) }
    val moviesRepository = remember { viewModel.getMoviesByCategory( category ) }
    val coroutineScope   = rememberCoroutineScope()

    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        coroutineScope.launch {
            try { moviesRepository.fetch() }
            catch (e: Exception) { errorMessage = "${e.message}" }

            runCoroutineInt += 1
        }
    })

    LaunchedEffect(key1 = runCoroutineInt) {
        coroutineScope.launch {
            moviesRepository
                .consumeAsResource()
                .collect { moviesResource ->
                    refreshing = false
                    Log.w("AAA", "Refreshing $refreshing, Resource: $moviesResource")
                    when( moviesResource ) {
                        is Resource.Error   -> errorMessage = "${moviesResource.exception.message}"
                        is Resource.Loading -> refreshing = true
                        is Resource.Success -> movieList = moviesResource.data ?: MovieList()
                    }
                }
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .pullRefresh(state = refreshState)
    ) {
        if( errorMessage.isNotEmpty() )
            Text(text = "Error al obtener la lista de elementos: $errorMessage",
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .padding(1.dp))
        Box(
            Modifier
                .padding(top = 10.dp)
                .background(Color.Cyan)
                .fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                Modifier
                    .background(Color.Blue)
                    .fillMaxHeight()
            ) {
                items(movieList.movies.count()) { movieIndex ->
                    Box {
                        movieList.movies.getOrNull(movieIndex)?.also { movie ->
                            Text(text = movie.name)
                            GlideImage(model = "https://image.tmdb.org/t/p/original/${movie.imageUrl}",
                                contentDescription = movie.name)
                        }
                    }
                }
            }

            PullRefreshIndicator(refreshing, refreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}