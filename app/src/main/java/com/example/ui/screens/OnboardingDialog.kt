package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.PreferencesManager
import com.example.data.TimetableData
import com.example.ui.components.RamodaLogoImage
import com.example.ui.theme.Amber500
import com.example.util.Localization

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingDialog(
    initialSection: String,
    initialLanguage: String,
    initialElective12: String,
    initialElective4850: String,
    initialElective4951: String,
    onSave: (section: String, language: String, elective12: String, elective4850: String, elective4951: String) -> Unit
) {
    var selectedSection by remember { mutableStateOf(initialSection) }
    var selectedLanguage by remember { mutableStateOf(initialLanguage) }
    var selectedElective12 by remember { mutableStateOf(initialElective12) }
    var selectedElective4850 by remember { mutableStateOf(initialElective4850) }
    var selectedElective4951 by remember { mutableStateOf(initialElective4951) }

    Dialog(
        onDismissRequest = { /* Force completion on first run */ },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.92f)
                .testTag("onboarding_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Header with Developer & School Branding
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.school_hero_banner),
                        contentDescription = "Chercher Secondary School",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(34.dp)
                        ) {
                            RamodaLogoImage(modifier = Modifier.fillMaxSize().padding(4.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Chercher Secondary School",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Smart Timetable • Setup",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = Localization.get("setup_welcome", selectedLanguage),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Please configure your section and personal elective subjects. Your choices will be saved permanently on this device.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Section Selection
                Text(
                    text = Localization.get("setup_section_label", selectedLanguage),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    val rows = TimetableData.sections.chunked(4)
                    rows.forEach { rowSections ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowSections.forEach { sec ->
                                val isSelected = sec == selectedSection
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedSection = sec },
                                    label = {
                                        Text(
                                            text = sec,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (rowSections.size < 4) {
                                repeat(4 - rowSections.size) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Language Selection
                Text(
                    text = Localization.get("setup_language_label", selectedLanguage),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FilterChip(
                        selected = selectedLanguage == "en",
                        onClick = { selectedLanguage = "en" },
                        label = { Text("English") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = selectedLanguage == "om",
                        onClick = { selectedLanguage = "om" },
                        label = { Text("Afaan Oromoo") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Category A: Language Alternative (1/2)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "A. Language Alternative (Code 1/2)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedElective12 = "1" }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedElective12 == "1",
                                onClick = { selectedElective12 = "1" }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Afaan Oromoo (Tadele Olani - Code 1)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedElective12 = "2" }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedElective12 == "2",
                                onClick = { selectedElective12 = "2" }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Amharic (Tenagne Tamiru - Code 2)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Category B: Health / Construction (48/50)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "B. Health / Construction (Code 48/50)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedElective4850 = "48" }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedElective4850 == "48",
                                onClick = { selectedElective4850 = "48" }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Health — Teacher code 48 (Ramatulahi*)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedElective4850 = "50" }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedElective4850 == "50",
                                onClick = { selectedElective4850 = "50" }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Construction — Teacher code 50 (Bakkalcha)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Category C: Journalism / Accounting (49/51)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "C. Journalism / Accounting (Code 49/51)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedElective4951 = "49" }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedElective4951 == "49",
                                onClick = { selectedElective4951 = "49" }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Journalism — Teacher code 49 (Giduma Tariku)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedElective4951 = "51" }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedElective4951 == "51",
                                onClick = { selectedElective4951 = "51" }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Accounting — Teacher code 51 (Ukasha Kemal Eeko*)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Storage Notice
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Amber500.copy(alpha = 0.12f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Amber500,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (selectedLanguage == "om") PreferencesManager.STORAGE_DISCLAIMER_OM else PreferencesManager.STORAGE_DISCLAIMER_EN,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Save Button
                Button(
                    onClick = {
                        onSave(selectedSection, selectedLanguage, selectedElective12, selectedElective4850, selectedElective4951)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("onboarding_save_button")
                ) {
                    Text(
                        text = Localization.get("setup_btn_save", selectedLanguage),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
