package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.ui.theme.*
import kotlinx.coroutines.delay

const val RAMODA_LOGO_REMOTE_URL = "https://i.postimg.cc/L8XpTy0H/file-00000000f02472438f295169e929f395.png"

@Composable
fun RamodaSplashScreen(
    onTimeout: () -> Unit
) {
    // 5-second countdown timer for opening splash screen
    LaunchedEffect(Unit) {
        delay(5000L)
        onTimeout()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "splash_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.65f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Navy950,
                        Navy900,
                        Color(0xFF0C1322)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("ramoda_splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Subtle glowing backdrop for logo
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(140.dp)
                    .scale(pulseScale)
            ) {
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    CyanAccent.copy(alpha = glowAlpha),
                                    Indigo600.copy(alpha = glowAlpha * 0.5f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Ramoda Logo with offline local drawable & remote fallback
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Navy900,
                    tonalElevation = 8.dp,
                    shadowElevation = 12.dp,
                    modifier = Modifier.size(108.dp)
                ) {
                    RamodaLogoImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // School & App Title
            Text(
                text = "Chercher Secondary School",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Smart Timetable",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = CyanAccent,
                letterSpacing = 0.25.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Subtle Loading Animation
            CircularProgressIndicator(
                modifier = Modifier.size(28.dp),
                color = CyanAccent,
                strokeWidth = 2.5.dp,
                trackColor = Navy800
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Developer Attribution
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Navy850.copy(alpha = 0.85f),
                border = androidx.compose.foundation.BorderStroke(1.dp, Navy700.copy(alpha = 0.5f))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Developed by ",
                        style = MaterialTheme.typography.bodySmall,
                        color = Navy400,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Ramoda Technologies",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RamodaLogoImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ramoda_logo),
        contentDescription = "Ramoda Technologies Logo",
        contentScale = ContentScale.Fit,
        modifier = modifier
    )
}

@Composable
fun RamodaFooter(
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.Transparent,
        modifier = modifier
            .fillMaxWidth()
            .testTag("ramoda_footer")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(24.dp)
            ) {
                RamodaLogoImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Developed by ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
                fontSize = 12.sp
            )
            Text(
                text = "Ramoda Technologies",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )
        }
    }
}
