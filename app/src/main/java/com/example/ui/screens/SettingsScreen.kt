package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TimetableData
import com.example.data.model.StudentPreferences
import com.example.ui.components.RamodaFooter
import com.example.ui.theme.Amber500
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Rose500
import com.example.util.Localization

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    preferences: StudentPreferences,
    onSectionChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit,
    onSaveElectives: (elective12: String, elective4850: String, elective4951: String) -> Unit,
    onThemeChange: (String) -> Unit,
    onPrintSchedule: () -> Unit,
    onViewValidation: () -> Unit,
    onResetDefaults: () -> Unit,
    onClearPreferences: () -> Unit
) {
    val language = preferences.language
    var isEditingPreferences by remember { mutableStateOf(false) }

    var pendingElective12 by remember(preferences.elective12) { mutableStateOf(preferences.elective12) }
    var pendingElective4850 by remember(preferences.elective4850) { mutableStateOf(preferences.elective4850) }
    var pendingElective4951 by remember(preferences.elective4951) { mutableStateOf(preferences.elective4951) }

    var showClearDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("settings_screen")
    ) {
        Text(
            text = Localization.get("settings_title", language),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Section Selector Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Class,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = Localization.get("section_preference", language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                val rows = TimetableData.sections.chunked(4)
                rows.forEach { rowSections ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowSections.forEach { sec ->
                            val isSelected = sec == preferences.section
                            FilterChip(
                                selected = isSelected,
                                onClick = { onSectionChange(sec) },
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
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Language & Theme Settings Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Language
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = Localization.get("language_preference", language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = preferences.language == "en",
                        onClick = { onLanguageChange("en") },
                        label = { Text("English") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = preferences.language == "om",
                        onClick = { onLanguageChange("om") },
                        label = { Text("Afaan Oromoo") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(16.dp))

                // Theme Mode
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Palette,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = Localization.get("theme_preference", language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = preferences.themeMode == "system",
                        onClick = { onThemeChange("system") },
                        label = { Text(Localization.get("theme_system", language), fontSize = 12.sp) },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = preferences.themeMode == "light",
                        onClick = { onThemeChange("light") },
                        label = { Text(Localization.get("theme_light", language), fontSize = 12.sp) },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = preferences.themeMode == "dark",
                        onClick = { onThemeChange("dark") },
                        label = { Text(Localization.get("theme_dark", language), fontSize = 12.sp) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Personal Preferences Card (Settings-only modification requirement 5)
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("personal_preferences_card")
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Personal Preferences",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (!isEditingPreferences) {
                        FilledTonalButton(
                            onClick = {
                                pendingElective12 = preferences.elective12
                                pendingElective4850 = preferences.elective4850
                                pendingElective4951 = preferences.elective4951
                                isEditingPreferences = true
                            },
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("edit_preferences_button")
                        ) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Edit Preferences", fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (!isEditingPreferences) {
                    // Display Current Saved Choices
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            // Category A
                            Text(
                                text = "A. Language Alternative:",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = if (preferences.elective12 == "1") "Afaan Oromoo (Tadele Olani - Code 1)" else "Amharic (Tenagne Tamiru - Code 2)",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Category B
                            Text(
                                text = "B. Health / Construction:",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = if (preferences.elective4850 == "48") "Health (Ramatulahi* - Code 48)" else "Construction (Bakkalcha - Code 50)",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Category C
                            Text(
                                text = "C. Journalism / Accounting:",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = if (preferences.elective4951 == "49") "Journalism (Giduma Tariku - Code 49)" else "Accounting (Ukasha Kemal Eeko* - Code 51)",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    // Editing Mode (Radio Buttons for all 3 categories)
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        // Category A
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "A. Language Alternative (Code 1/2)",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { pendingElective12 = "1" }
                                ) {
                                    RadioButton(selected = pendingElective12 == "1", onClick = { pendingElective12 = "1" })
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Afaan Oromoo (Tadele Olani - Code 1)", style = MaterialTheme.typography.bodyMedium)
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { pendingElective12 = "2" }
                                ) {
                                    RadioButton(selected = pendingElective12 == "2", onClick = { pendingElective12 = "2" })
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Amharic (Tenagne Tamiru - Code 2)", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }

                        // Category B
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "B. Health / Construction (Code 48/50)",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { pendingElective4850 = "48" }
                                ) {
                                    RadioButton(selected = pendingElective4850 == "48", onClick = { pendingElective4850 = "48" })
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Health — Code 48 (Ramatulahi*)", style = MaterialTheme.typography.bodyMedium)
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { pendingElective4850 = "50" }
                                ) {
                                    RadioButton(selected = pendingElective4850 == "50", onClick = { pendingElective4850 = "50" })
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Construction — Code 50 (Bakkalcha)", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }

                        // Category C
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "C. Journalism / Accounting (Code 49/51)",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { pendingElective4951 = "49" }
                                ) {
                                    RadioButton(selected = pendingElective4951 == "49", onClick = { pendingElective4951 = "49" })
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Journalism — Code 49 (Giduma Tariku)", style = MaterialTheme.typography.bodyMedium)
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { pendingElective4951 = "51" }
                                ) {
                                    RadioButton(selected = pendingElective4951 == "51", onClick = { pendingElective4951 = "51" })
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Accounting — Code 51 (Ukasha Kemal Eeko*)", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }

                        // Save Changes and Cancel Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    onSaveElectives(pendingElective12, pendingElective4850, pendingElective4951)
                                    isEditingPreferences = false
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("save_changes_button")
                            ) {
                                Text("Save Changes", fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = {
                                    // Canceling restores previous choices without change
                                    pendingElective12 = preferences.elective12
                                    pendingElective4850 = preferences.elective4850
                                    pendingElective4951 = preferences.elective4951
                                    isEditingPreferences = false
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("cancel_edit_button")
                            ) {
                                Text("Cancel")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Print & Export Schedule Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Print,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = Localization.get("print_share_title", language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${Localization.get("print_share_desc", language)} Section ${preferences.section}.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onPrintSchedule,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("settings_print_button")
                ) {
                    Icon(imageVector = Icons.Default.Print, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = Localization.get("btn_print", language))
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Official Data Policy & Validation Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = Emerald500
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = Localization.get("official_data_policy_title", language),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = Localization.get("official_data_policy_desc", language),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = onViewValidation,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Emerald500)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = Localization.get("btn_run_validation", language))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Reset & Clear Preferences Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = { showResetDialog = true },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("settings_reset_defaults_button")
            ) {
                Text(
                    text = Localization.get("btn_reset_prefs", language),
                    fontSize = 12.sp
                )
            }

            Button(
                onClick = { showClearDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Rose500),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("settings_clear_storage_button")
            ) {
                Text(
                    text = Localization.get("btn_clear_storage", language),
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Ramoda Technologies Footer
        RamodaFooter()

        Spacer(modifier = Modifier.height(60.dp))
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text(text = "Reset Preferences?", fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = "Are you sure you want to reset your section and elective subject choices to default values?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResetDialog = false
                        onResetDefaults()
                    }
                ) {
                    Text(text = "Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text(text = Localization.get("dialog_cancel", language))
                }
            }
        )
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = {
                Text(
                    text = Localization.get("clear_confirm_title", language),
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(text = Localization.get("clear_confirm_desc", language))
            },
            confirmButton = {
                Button(
                    onClick = {
                        showClearDialog = false
                        onClearPreferences()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Rose500)
                ) {
                    Text(text = Localization.get("dialog_confirm", language))
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) {
                    Text(text = Localization.get("dialog_cancel", language))
                }
            }
        )
    }
}
