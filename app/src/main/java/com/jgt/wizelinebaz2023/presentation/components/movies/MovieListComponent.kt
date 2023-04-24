package com.jgt.wizelinebaz2023.presentation.components.movies

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jgt.wizelinebaz2023.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.wizelinebaz2023.core.sharedActions.NavigationActions
import com.jgt.wizelinebaz2023.domain.MoviesViewModel
import com.jgt.wizelinebaz2023.domain.models.MovieList
import com.jgt.wizelinebaz2023.storage.Resource
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

    val haptic = LocalHapticFeedback.current

    var errorMessage    by remember { mutableStateOf("") }
    var movieList       by remember { mutableStateOf( MovieList() ) }
    var isRefreshing    by remember { mutableStateOf(false) }
    var runCoroutineInt by remember { mutableStateOf( 0 ) }
    val moviesRepository = remember { viewModel.getMoviesByCategory( category ) }
    val coroutineScope   = rememberCoroutineScope()

    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                try { moviesRepository.fetch() }
                catch (e: Exception) {
                    errorMessage = "${e.message}"
                    isRefreshing   = false
                }

                runCoroutineInt += 1
            }
        },
        refreshThreshold = 50.dp,
        refreshingOffset = 50.dp
    )

    LaunchedEffect(key1 = runCoroutineInt) {
        coroutineScope.launch {
            moviesRepository
                .consumeAsResource()
                .collect { moviesResource ->
                    isRefreshing = false
                    errorMessage = ""

                    when( moviesResource ) {
                        is Resource.Error   -> errorMessage = "${moviesResource.exception.message}"
                        is Resource.Loading -> isRefreshing = true
                        is Resource.Success -> movieList    = moviesResource.data ?: MovieList()
                        is Resource.Cache   -> {
                            movieList    = moviesResource.data ?: MovieList()
                            errorMessage = "${moviesResource.exception.message}"
                        }
                    }
                }
        }
    }
    MaterialTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .pullRefresh(state = refreshState, true)
        ) {
            if (errorMessage.isNotEmpty())
                Text(text = "Error al obtener los datos: $errorMessage",
                    Modifier
                        .padding(15.dp)
                        .background(Color(red = 0F, green = 0F, blue = 0F, alpha = 0.05F)),
                    textAlign = TextAlign.Center)

            Box(
                Modifier
                    .fillMaxWidth()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(
                        if (movieList.movies.isEmpty())
                            LocalConfiguration.current.screenWidthDp.dp
                        else
                            128.dp
                    )
                ) {
                    if (errorMessage.isNotEmpty() && movieList.movies.isEmpty())
                        item {
                            Spacer(
                                Modifier
                                    .padding(15.dp)
                                    .fillMaxWidth()
                                    .height(
                                        (LocalConfiguration.current.screenHeightDp / 2).dp
                                    )
                                    .padding(1.dp)
                            )
                        }
                    items(movieList.movies.count()) { movieIndex ->
                        movieList.movies.getOrNull(movieIndex)?.also { movie ->
                            Box(
                                Modifier.clickable {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.dispatch(
                                        NavigationActions.NavigateToCompose(
                                            "/movies/detail/${movie.id}"
                                        )
                                    )
                                }
                            ){
                                Text(text = movie.name)
                                GlideImage(
                                    model = "https://image.tmdb.org/t/p/original/${movie.imageUrl}",
                                    contentDescription = movie.name,
                                    contentScale = ContentScale.Fit,
                                )
                            }
                        }
                    }
                }

                PullRefreshIndicator(
                    isRefreshing,
                    refreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}
