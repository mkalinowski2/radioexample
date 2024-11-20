@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package pl.fewbits.radioexample.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import pl.fewbits.radioexample.android.appnavigator.AppNavigator
import pl.fewbits.radioexample.android.appnavigator.MockAppNavigator
import pl.fewbits.radioexample.android.ui.R
import pl.fewbits.radioexample.android.ui.Screen
import pl.fewbits.radioexample.android.ui.components.BottomBar
import pl.fewbits.radioexample.android.ui.components.CircleLoader
import pl.fewbits.radioexample.android.ui.components.TopBar
import pl.fewbits.radioexample.android.ui.theme.RadioExampleTheme
import pl.fewbits.radioexample.android.ui.viewmodel.runOnInit
import pl.fewbits.radioexample.core.home.domain.RadioStation
import pl.fewbits.radioexample.feature.home.state.HomeState
import pl.fewbits.radioexample.feature.home.state.HomeStateItem

object HomeScreen : Screen {

    @Composable
    fun Content(appNavigator: AppNavigator) {
        val homeViewModel = koinViewModel<HomeViewModel>()
        HomeScreenView(homeViewModel, appNavigator)
    }
}

@Composable
fun HomeScreenView(viewModel: HomeViewModel, appNavigator: AppNavigator) {
    viewModel.runOnInit { it.loadContent() }
    val homeState = viewModel.getScreenStateFlow().collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(title = stringResource(R.string.screen_home_title), null) },
        bottomBar = { BottomBar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = homeState.value) {
                is HomeState.Content -> HomeScreenViewContent(state, appNavigator)
                HomeState.Loading -> Loader()
                HomeState.Error -> ErrorContent({ viewModel.loadContent() })
            }
        }
    }
}

@ExperimentalLayoutApi
@Composable
fun HomeScreenViewContent(state: HomeState.Content, appNavigator: AppNavigator) {
    LazyColumn {
        items(state.items) { station ->
            RadioStationItem(station.radioStation) { appNavigator.openRadioDetails(it) }
        }
    }
}

@ExperimentalLayoutApi
@Composable
fun RadioStationItem(
    station: RadioStation,
    onStationClick: (RadioStation) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onStationClick(station) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = station.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = station.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                FlowRow {
                    station.tags.forEach { tag ->
                        Surface(
                            modifier = Modifier.padding(end = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = tag,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_signal_12),
                        contentDescription = stringResource(R.string.reliability),
                        tint = station.reliability.getColorReliability()
                    )

                    station.popularity?.let {
                        Text(
                            text = "★ $it",
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            AsyncImage(
                model = station.imageUrl,
                contentDescription = "${station.name} logo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
fun Loader() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircleLoader(
            color = Color(0xFF1F79FF),
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center),
            isVisible = true
        )
    }
}

@Composable
fun ErrorContent(
    onTryAgainClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = stringResource(R.string.error),
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.error_generic_oops),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.error_generic_unknown),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onTryAgainClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(
                text = stringResource(R.string.try_again),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

private fun Int.getColorReliability() = when {
    this >= 80 -> Color.Green
    this >= 50 -> Color.Yellow
    else -> Color.Red
}

@Preview(showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    val items = listOf(
        RadioStation(
            "s135217",
            "Only Mozart Music",
            "Radio Mozart",
            "https://cdn-radiotime-logos.tunein.com/s135217d.png",
            "https://streamingv2.shoutcast.com/Radio-Mozart",
            68,
            2.9,
            listOf("music", "classical")
        ),
        RadioStation(
            "s135217",
            "Only Mozart Music",
            "Radio Mozart",
            "https://cdn-radiotime-logos.tunein.com/s135217d.png",
            "https://streamingv2.shoutcast.com/Radio-Mozart",
            68,
            2.9,
            listOf("music", "classical")
        ),
        RadioStation(
            "s135217",
            "Only Mozart Music",
            "Radio Mozart",
            "https://cdn-radiotime-logos.tunein.com/s135217d.png",
            "https://streamingv2.shoutcast.com/Radio-Mozart",
            68,
            2.9,
            listOf("music", "classical")
        )
    ).map { HomeStateItem(it) }

    RadioExampleTheme {
        HomeScreenView(viewModel = HomeViewModelMock(HomeState.Content(items)), MockAppNavigator)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLoadingPreview() {
    RadioExampleTheme {
        HomeScreenView(viewModel = HomeViewModelMock(HomeState.Loading), MockAppNavigator)
    }
}
