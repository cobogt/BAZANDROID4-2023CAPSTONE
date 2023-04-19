package com.jgt.wizelinebaz2023.presentation.components.movies

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jgt.wizelinebaz2023.core.mvi.ActivityWithViewModelStoreInterface
import com.jgt.wizelinebaz2023.domain.MoviesViewModel
import com.jgt.wizelinebaz2023.domain.models.MovieList
import com.jgt.wizelinebaz2023.storage.Resource

/** * * * * * * * * *
 * Project WLBaz2023JGT
 * Created by Jacobo G Tamayo on 10/04/23.
 * * * * * * * * * * **/
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieListComponent( category: String ) {
    // Enlace desde estado del Store y hacia Dispatcher
    val currentActivity = LocalContext.current as Activity
    val viewModel: MoviesViewModel = (currentActivity as ActivityWithViewModelStoreInterface)
        .viewModelStateStore as MoviesViewModel

    var errorMessage by remember { mutableStateOf("") }
    var isLoading    by remember { mutableStateOf( false ) }
    var movieList    by remember { mutableStateOf( MovieList() ) }

    LaunchedEffect(key1 = Unit ) {
        viewModel.getMoviesByCategory( category )
            .run {
                consumeAsResource().collect {
                    Log.d("MovieListComponent", it.toString())

                    if( it is Resource.Success )
                        it.data?.also { list ->
                            movieList = list
                        }
                }
                consumeAsResource()
                    .collect {movieListResource ->
                        Log.d("MovieListComponent", movieListResource.toString())
                        when( movieListResource ) {
                            is Resource.Error   -> errorMessage =
                                "Error al obtener la lista de elementos: " +
                                        "${movieListResource.exception.message}"

                            is Resource.Loading -> isLoading = true

                            is Resource.Success -> movieList =
                                movieListResource.data ?: MovieList()
                        }
                    }
            }
    }

    Column(
        Modifier.fillMaxHeight(.9F)
    ) {
        Text(text = "MovieListComponent")
        Spacer(modifier = Modifier.height(5.dp))
        if( errorMessage.isNotEmpty() )
            Text(text = errorMessage)

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            items(movieList.movies.count()) { movieIndex ->
                Box() {
                    movieList.movies.getOrNull(movieIndex)?.also { movie ->
                        Text(text = movie.name)
                        Text(text = movie.imageUrl)
                        GlideImage(model = "https://image.tmdb.org/t/p/original/${movie.imageUrl}",
                            contentDescription = movie.name)
                    }
                }

            }
        }
    }
}