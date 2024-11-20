@file:OptIn(ExperimentalLayoutApi::class)

package pl.fewbits.radioexample.feature.radiodetails

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pl.fewbits.radioexample.android.appnavigator.AppNavigator
import pl.fewbits.radioexample.android.ui.Screen
import pl.fewbits.radioexample.android.ui.components.TopBar
import pl.fewbits.radioexample.android.ui.theme.RadioExampleTheme
import pl.fewbits.radioexample.core.home.domain.RadioStation

object RadioDetailsScreen : Screen {

    @Composable
    fun Content(radioStation: RadioStation, appNavigator: AppNavigator) {
        RadioDetailsScreenView(radioStation, appNavigator)
    }
}

@Composable
fun RadioDetailsScreenView(radioStation: RadioStation, appNavigator: AppNavigator) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(title = "Radio", { appNavigator.back() }) },
        bottomBar = { }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            RadioDetailsScreenContent(radioStation, {})
        }
    }
}

@Composable
fun RadioDetailsScreenContent(
    radioStation: RadioStation,
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header with image and basic info
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Station image
            AsyncImage(
                model = radioStation.imageUrl,
                contentDescription = "${radioStation.name} logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            // Basic station info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = radioStation.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Reliability",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${radioStation.reliability}%",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                radioStation.popularity?.let { popularity ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Popularity",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "%.1f".format(popularity),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        // Description
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = radioStation.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Tags
        if (radioStation.tags.isNotEmpty()) {
            Text(
                text = "Tags",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = Int.MAX_VALUE
            ) {
                radioStation.tags.forEach { tag ->
                    SuggestionChip(
                        onClick = { },
                        label = { Text(tag) }
                    )
                }
            }
        }

        // Play button
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onPlayClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Play Station",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun Preview() {
    val sampleStation = RadioStation(
        id = "1",
        name = "Cool FM",
        description = "The best hits from the 80s, 90s, and today! We play non-stop music 24/7 with the greatest selection of popular tracks.",
        imageUrl = "https://example.com/image.jpg",
        streamUrl = "https://example.com/stream",
        reliability = 98,
        popularity = 4.5,
        tags = listOf("Pop", "Rock", "80s", "90s", "Hits")
    )

    RadioExampleTheme {
        RadioDetailsScreenContent(
            radioStation = sampleStation,
            onPlayClick = {}
        )
    }
}