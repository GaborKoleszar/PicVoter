package com.example.picvoter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.picvoter.R
import com.example.picvoter.data.PexelsApi
import com.example.picvoter.presentation.theme.PicVoterTheme
import com.example.picvoter.presentation.viewmodel.ImageGridViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageListActivity : ComponentActivity() {

    @Inject
    lateinit var pexelsApi: PexelsApi

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PicVoterTheme {
                var searchFieldVisible by rememberSaveable { mutableStateOf(true) }
                var searchFieldValue by rememberSaveable { mutableStateOf("") }
                val viewModel: ImageGridViewModel = hiltViewModel()
                val imageGridState by remember { viewModel.state }
                val scaffoldState = rememberScaffoldState()


                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomAppBar(
                            cutoutShape = MaterialTheme.shapes.small.copy(
                                CornerSize(percent = 50)
                            )
                        ) {
                            BottomButtons(modifier = Modifier)
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = getString(R.string.fab_content_description),
                                    tint = Color.White
                                )
                            },
                            onClick = { searchFieldVisible = !searchFieldVisible }
                        )
                    },
                    isFloatingActionButtonDocked = true,
                    floatingActionButtonPosition = FabPosition.Center


                ) {
                    val focusManager = LocalFocusManager.current

                    //Loading box
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        if (imageGridState.isLoading) CircularProgressIndicator()
                        else if (imageGridState.imageSearchResult == null) Text(text = "Please enter a search parameter.")
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(128.dp),
                            contentPadding = PaddingValues(
                                start = 12.dp,
                                top = 16.dp,
                                end = 12.dp,
                                bottom = 16.dp
                            )
                        ) {
                            imageGridState.imageSearchResult?.let { it ->
                                val photos = it.photos
                                items(photos.size) { scope ->
                                    SubcomposeAsyncImage(
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .fillMaxWidth()
                                            .height(170.dp),
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(photos[scope].src.medium)
                                            .crossfade(true)
                                            .transformations(RoundedCornersTransformation(20f))
                                            .build(),
                                        loading = {
                                            CircularProgressIndicator(
                                                modifier = Modifier.scale(0.20f),
                                                strokeWidth = 16.dp
                                            )
                                        },
                                        contentDescription = photos[scope].alt,
                                        filterQuality = FilterQuality.Medium
                                    )
                                }
                            }
                        }
                        AnimatedVisibility(
                            visible = searchFieldVisible,
                            enter = slideInVertically() + fadeIn(initialAlpha = 0f),
                            exit = slideOutVertically() + shrinkVertically() + fadeOut()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .shadow(10.dp),
                                    value = searchFieldValue,
                                    onValueChange = { searchFieldValue = it },
                                    placeholder = { Text(text = getString(R.string.searchfield_hint)) },
                                    label = { Text(text = getString(R.string.searchfield_label)) },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Search,
                                        capitalization = KeyboardCapitalization.Sentences
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            viewModel.onSearchImage(searchFieldValue, 40)
                                            focusManager.clearFocus()
                                            searchFieldVisible = !searchFieldVisible
                                            searchFieldValue = ""
                                        }
                                    ),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        backgroundColor = MaterialTheme.colors.secondaryVariant
                                    )
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}