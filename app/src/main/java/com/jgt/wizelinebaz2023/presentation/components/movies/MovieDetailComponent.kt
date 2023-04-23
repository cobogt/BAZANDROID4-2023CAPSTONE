package com.jgt.wizelinebaz2023.presentation.components.movies

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jgt.wizelinebaz2023.R
import com.jgt.wizelinebaz2023.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.wizelinebaz2023.domain.MoviesViewModel
import com.jgt.wizelinebaz2023.domain.models.MovieDetail
import com.jgt.wizelinebaz2023.storage.Resource
import kotlinx.coroutines.launch

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun MovieDetailComponent( movieId: Int ) {
    // Enlace desde estado del Store y hacia Dispatcher
    val currentActivity = LocalContext.current as Activity
    val viewModel: MoviesViewModel = (currentActivity as ActivityWithViewModelStoreInterface)
        .viewModelStateStore as MoviesViewModel

    val movieDetailRepository = remember { viewModel.getMovieDetailById( movieId ) }
    val coroutineScope        = rememberCoroutineScope()
    var errorMessage         by remember { mutableStateOf("") }
    var isRefreshing         by remember { mutableStateOf(false) }
    var runCoroutineInt      by remember { mutableStateOf( 0 ) }
    var movieDetail          by remember { mutableStateOf( MovieDetail() ) }
    val detailScrollState    = rememberScrollState()
    val labelsScrollState    = rememberScrollState()


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                try { movieDetailRepository.fetch() }
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
            movieDetailRepository
                .consumeAsResource()
                .collect { movieResource ->
                    isRefreshing = false
                    errorMessage = ""

                    when( movieResource ) {
                        is Resource.Error   -> errorMessage = "${movieResource.exception.message}"
                        is Resource.Loading -> isRefreshing = true
                        is Resource.Success -> movieDetail  = movieResource.data ?: MovieDetail()
                    }
                }
        }
    }
    
    MaterialTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(detailScrollState)
                .pullRefresh(refreshState)
        ) {
            if (errorMessage.isNotEmpty())
                Text(text = "Error al obtener los datos: $errorMessage",
                    Modifier
                        .padding(15.dp)
                        .background(Color(red = 0F, green = 0F, blue = 0F, alpha = 0.05F)),
                    textAlign = TextAlign.Center)

            Text(text = "Movie ID: $movieId",
                Modifier.padding(start = 15.dp))
            Text(text = "Nombre: ${movieDetail.name}",
                Modifier.padding(start = 15.dp))
            Text(text = "Estado actual: ${movieDetail.status}",
                Modifier.padding(start = 15.dp))
            Text(text = "Año de lanzamiento: ${movieDetail.releaseDate}",
                Modifier.padding(start = 15.dp))

            if( movieDetail.budget > 0 )
                Text(text = "Presupuesto:     ${movieDetail.budget} USD", Modifier.padding(start = 15.dp))

            if( movieDetail.adult ) {
                Row(Modifier.padding(start = 15.dp)) {
                    Text(stringResource(id = R.string.for_adults_advice))
                    Icon(imageVector = Icons.Filled.Block, contentDescription = "For adults")
                }
            }

            Text("Posters: ${movieDetail.images.posters.size}",
                Modifier.padding(start = 15.dp))


            LazyRow(Modifier.fillMaxWidth()) {
                if( movieDetail.imageUrl.isNotEmpty() )
                    item {
                        Box(modifier = Modifier
                            .fillParentMaxHeight()
                            .fillParentMaxWidth()) {
                            GlideImage(
                                model = "https://image.tmdb.org/t/p/original/${movieDetail.imageUrl}",
                                contentDescription = movieDetail.name
                            )
                        }
                    }
                items(movieDetail.images.posters.size) {
                    movieDetail.images.posters.forEach {
                        Box(modifier = Modifier
                            .fillParentMaxHeight()
                            .fillParentMaxWidth()) {
                            GlideImage(
                                model = "https://image.tmdb.org/t/p/original/${it.path}",
                                contentDescription = movieDetail.name,
                                contentScale = ContentScale.Fit,
                            )
                        }
                    }
                }
                items(movieDetail.images.backdrops.size) {
                    movieDetail.images.backdrops.forEach {
                        Box(modifier = Modifier
                            .fillParentMaxHeight()
                            .fillParentMaxWidth()) {
                            GlideImage(
                                model = "https://image.tmdb.org/t/p/original/${it.path}",
                                contentDescription = movieDetail.name,
                                contentScale = ContentScale.Fit,
                            )
                        }
                    }
                }
            }

            Text(text = "Etiquetas: (${movieDetail.keywords.size})", Modifier.padding(start = 15.dp))

            Row(Modifier.horizontalScroll( labelsScrollState )) {
                movieDetail.keywords.forEach {
                    Box(modifier = Modifier
                        .padding(2.dp)
                        .background(Color.LightGray)) {
                        Icon(imageVector = Icons.Filled.Tag, contentDescription = it.word)
                        Text(text = it.word, Modifier.padding(start = 25.dp, end = 5.dp))
                    }
                }
            }

            Text("Backdrops: ${movieDetail.images.backdrops.size}",
                Modifier.padding(start = 15.dp, top = 25.dp))

            LazyRow(Modifier.fillMaxWidth()) {
                items(movieDetail.images.backdrops.size) {
                    movieDetail.images.backdrops.forEach {
                        Box(modifier = Modifier
                            .fillParentMaxHeight()
                            .fillParentMaxWidth()) {
                            GlideImage(
                                model = "https://image.tmdb.org/t/p/original/${it.path}",
                                contentDescription = movieDetail.name,
                                contentScale = ContentScale.Fit,
                            )
                        }
                    }
                }
            }

            PullRefreshIndicator(refreshing = isRefreshing, state = refreshState)
        }
    }
}
