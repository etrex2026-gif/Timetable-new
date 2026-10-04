package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.TimetableData
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.util.Localization
import com.example.util.PrintHelper

@Composable
fun MainApp(viewModel: MainViewModel) {
    val context = LocalContext.current
    val preferences by viewModel.preferences.collectAsStateWithLifecycle()
    val showSplash by viewModel.showSplash.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val browsingSection by viewModel.browsingSection.collectAsStateWithLifecycle()
    val selectedDay by viewModel.selectedDay.collectAsStateWithLifecycle()
    val todayPeriods by viewModel.todayPeriods.collectAsStateWithLifecycle()
    val weeklySchedule by viewModel.weeklySchedule.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchAllSections by viewModel.searchAllSections.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val teacherSearch by viewModel.teacherSearch.collectAsStateWithLifecycle()
    val teacherSubjectFilter by viewModel.teacherSubjectFilter.collectAsStateWithLifecycle()
    val filteredTeachers by viewModel.filteredTeachers.collectAsStateWithLifecycle()
    val selectedPeriodDetail by viewModel.selectedPeriodDetail.collectAsStateWithLifecycle()
    val selectedTeacherDetail by viewModel.selectedTeacherDetail.collectAsStateWithLifecycle()
    val showValidationDialog by viewModel.showValidationDialog.collectAsStateWithLifecycle()
    val showSetupWizard by viewModel.showSetupWizard.collectAsStateWithLifecycle()

    var showStatusDisclaimer by remember { mutableStateOf(false) }

    val lang = preferences.language

    // 5-Second Opening Splash Screen
    if (showSplash) {
        RamodaSplashScreen(
            onTimeout = { viewModel.dismissSplash() }
        )
        return
    }

    // Handle Back Press when sub-dialogs or sub-tabs are open
    BackHandler(enabled = selectedPeriodDetail != null || selectedTeacherDetail != null || currentTab != NavigationTab.TODAY) {
        when {
            selectedPeriodDetail != null -> viewModel.closePeriodDetail()
            selectedTeacherDetail != null -> viewModel.closeTeacherDetail()
            currentTab != NavigationTab.TODAY -> viewModel.setTab(NavigationTab.TODAY)
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = maxWidth > 600.dp

        Row(modifier = Modifier.fillMaxSize()) {
            // Adaptive Navigation Rail for Tablets / Wide screens
            if (isWideScreen) {
                NavigationRail(
                    modifier = Modifier.fillMaxHeight(),
                    containerColor = MaterialTheme.colorScheme.surface,
                    header = {
                        IconButton(onClick = { viewModel.setTab(NavigationTab.TODAY) }) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = "Home",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                ) {
                    NavigationRailItem(
                        selected = currentTab == NavigationTab.TODAY,
                        onClick = { viewModel.setTab(NavigationTab.TODAY) },
                        icon = { Icon(Icons.Default.Today, contentDescription = "Today") },
                        label = { Text(Localization.get("tab_today", lang)) },
                        modifier = Modifier.testTag("nav_rail_today")
                    )
                    NavigationRailItem(
                        selected = currentTab == NavigationTab.WEEKLY,
                        onClick = { viewModel.setTab(NavigationTab.WEEKLY) },
                        icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "Weekly") },
                        label = { Text(Localization.get("tab_weekly", lang)) },
                        modifier = Modifier.testTag("nav_rail_weekly")
                    )
                    NavigationRailItem(
                        selected = currentTab == NavigationTab.TEACHERS,
                        onClick = { viewModel.setTab(NavigationTab.TEACHERS) },
                        icon = { Icon(Icons.Default.Groups, contentDescription = "Teachers") },
                        label = { Text(Localization.get("tab_teachers", lang)) },
                        modifier = Modifier.testTag("nav_rail_teachers")
                    )
                    NavigationRailItem(
                        selected = currentTab == NavigationTab.SEARCH,
                        onClick = { viewModel.setTab(NavigationTab.SEARCH) },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        label = { Text(Localization.get("tab_search", lang)) },
                        modifier = Modifier.testTag("nav_rail_search")
                    )
                    NavigationRailItem(
                        selected = currentTab == NavigationTab.SETTINGS,
                        onClick = { viewModel.setTab(NavigationTab.SETTINGS) },
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        label = { Text(Localization.get("tab_settings", lang)) },
                        modifier = Modifier.testTag("nav_rail_settings")
                    )
                }
            }

            // Main Content Area
            Scaffold(
                topBar = {
                    ChercherTopBar(
                        currentSection = browsingSection,
                        onSectionChange = { viewModel.setBrowsingSection(it) },
                        currentLanguage = lang,
                        onLanguageToggle = {
                            viewModel.updateLanguagePref(if (lang == "en") "om" else "en")
                        },
                        onStatusClick = { showStatusDisclaimer = true }
                    )
                },
                bottomBar = {
                    if (!isWideScreen) {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            tonalElevation = 6.dp,
                            windowInsets = WindowInsets.navigationBars,
                            modifier = Modifier.testTag("bottom_nav_bar")
                        ) {
                            NavigationBarItem(
                                selected = currentTab == NavigationTab.TODAY,
                                onClick = { viewModel.setTab(NavigationTab.TODAY) },
                                icon = {
                                    Icon(
                                        imageVector = if (currentTab == NavigationTab.TODAY) Icons.Filled.Today else Icons.Outlined.Today,
                                        contentDescription = "Today"
                                    )
                                },
                                label = { Text(Localization.get("tab_today", lang), fontSize = 11.sp) },
                                modifier = Modifier.testTag("nav_item_today")
                            )

                            NavigationBarItem(
                                selected = currentTab == NavigationTab.WEEKLY,
                                onClick = { viewModel.setTab(NavigationTab.WEEKLY) },
                                icon = {
                                    Icon(
                                        imageVector = if (currentTab == NavigationTab.WEEKLY) Icons.Filled.CalendarMonth else Icons.Outlined.CalendarMonth,
                                        contentDescription = "Weekly"
                                    )
                                },
                                label = { Text(Localization.get("tab_weekly", lang), fontSize = 11.sp) },
                                modifier = Modifier.testTag("nav_item_weekly")
                            )

                            NavigationBarItem(
                                selected = currentTab == NavigationTab.TEACHERS,
                                onClick = { viewModel.setTab(NavigationTab.TEACHERS) },
                                icon = {
                                    Icon(
                                        imageVector = if (currentTab == NavigationTab.TEACHERS) Icons.Filled.Groups else Icons.Outlined.Groups,
                                        contentDescription = "Teachers"
                                    )
                                },
                                label = { Text(Localization.get("tab_teachers", lang), fontSize = 11.sp) },
                                modifier = Modifier.testTag("nav_item_teachers")
                            )

                            NavigationBarItem(
                                selected = currentTab == NavigationTab.SEARCH,
                                onClick = { viewModel.setTab(NavigationTab.SEARCH) },
                                icon = {
                                    Icon(
                                        imageVector = if (currentTab == NavigationTab.SEARCH) Icons.Filled.Search else Icons.Outlined.Search,
                                        contentDescription = "Search"
                                    )
                                },
                                label = { Text(Localization.get("tab_search", lang), fontSize = 11.sp) },
                                modifier = Modifier.testTag("nav_item_search")
                            )

                            NavigationBarItem(
                                selected = currentTab == NavigationTab.SETTINGS,
                                onClick = { viewModel.setTab(NavigationTab.SETTINGS) },
                                icon = {
                                    Icon(
                                        imageVector = if (currentTab == NavigationTab.SETTINGS) Icons.Filled.Settings else Icons.Outlined.Settings,
                                        contentDescription = "Settings"
                                    )
                                },
                                label = { Text(Localization.get("tab_settings", lang), fontSize = 11.sp) },
                                modifier = Modifier.testTag("nav_item_settings")
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentTab) {
                        NavigationTab.TODAY -> {
                            TodayScreen(
                                currentSection = browsingSection,
                                selectedDay = selectedDay,
                                onDaySelect = { viewModel.setSelectedDay(it) },
                                periods = todayPeriods,
                                language = lang,
                                onPeriodClick = { viewModel.openPeriodDetail(it) },
                                onShareClick = {
                                    PrintHelper.shareSectionSchedule(context, browsingSection, preferences)
                                }
                            )
                        }
                        NavigationTab.WEEKLY -> {
                            WeeklyScreen(
                                currentSection = browsingSection,
                                weeklySchedule = weeklySchedule,
                                language = lang,
                                onPeriodClick = { viewModel.openPeriodDetail(it) },
                                onPrintClick = {
                                    PrintHelper.printSectionSchedule(context, browsingSection, preferences)
                                }
                            )
                        }
                        NavigationTab.TEACHERS -> {
                            TeacherDirectoryScreen(
                                teachers = filteredTeachers,
                                searchQuery = teacherSearch,
                                onSearchChange = { viewModel.setTeacherSearch(it) },
                                activeSubjectFilter = teacherSubjectFilter,
                                onSubjectFilterChange = { viewModel.setTeacherSubjectFilter(it) },
                                language = lang,
                                onTeacherClick = { viewModel.openTeacherDetail(it) }
                            )
                        }
                        NavigationTab.SEARCH -> {
                            SearchScreen(
                                currentSection = browsingSection,
                                searchQuery = searchQuery,
                                onSearchChange = { viewModel.setSearchQuery(it) },
                                searchAllSections = searchAllSections,
                                onSearchAllChange = { viewModel.setSearchAllSections(it) },
                                results = searchResults,
                                language = lang,
                                onPeriodClick = { viewModel.openPeriodDetail(it) }
                            )
                        }
                        NavigationTab.SETTINGS -> {
                            SettingsScreen(
                                preferences = preferences,
                                onSectionChange = { viewModel.updateSectionPref(it) },
                                onLanguageChange = { viewModel.updateLanguagePref(it) },
                                onSaveElectives = { e12, e4850, e4951 ->
                                    viewModel.saveElectives(e12, e4850, e4951)
                                },
                                onThemeChange = { viewModel.updateThemeMode(it) },
                                onPrintSchedule = {
                                    PrintHelper.printSectionSchedule(context, browsingSection, preferences)
                                },
                                onViewValidation = { viewModel.setValidationDialogVisible(true) },
                                onResetDefaults = { viewModel.resetPreferences() },
                                onClearPreferences = { viewModel.clearPreferences() }
                            )
                        }
                    }
                }
            }
        }
    }

    // Detail Dialogs & Sheets
    PeriodDetailDialog(
        period = selectedPeriodDetail,
        language = lang,
        onDismiss = { viewModel.closePeriodDetail() }
    )

    val teacherDetail = selectedTeacherDetail
    if (teacherDetail != null) {
        val teacherPeriods = viewModel.getTeacherPeriods(teacherDetail.code)
        TeacherDetailDialog(
            teacher = teacherDetail,
            teacherPeriods = teacherPeriods,
            language = lang,
            onPeriodClick = { viewModel.openPeriodDetail(it) },
            onDismiss = { viewModel.closeTeacherDetail() }
        )
    }

    if (showValidationDialog) {
        ValidationDialog(
            language = lang,
            onDismiss = { viewModel.setValidationDialogVisible(false) }
        )
    }

    if (showSetupWizard) {
        OnboardingDialog(
            initialSection = preferences.section,
            initialLanguage = preferences.language,
            initialElective12 = preferences.elective12,
            initialElective4850 = preferences.elective4850,
            initialElective4951 = preferences.elective4951,
            onSave = { sec, languageChoice, e12, e4850, e4951 ->
                viewModel.saveSetup(sec, languageChoice, e12, e4850, e4951)
            }
        )
    }

    if (showStatusDisclaimer) {
        AlertDialog(
            onDismissRequest = { showStatusDisclaimer = false },
            title = {
                Text(
                    text = Localization.get("academic_status", lang),
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = if (lang == "om") TimetableData.TIMETABLE_STATUS_OM else TimetableData.TIMETABLE_STATUS_EN,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Source is tentative timetable for Academic Year 2019 E.C. Pending official school verification. Unknown or ambiguous teacher codes require administration confirmation.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showStatusDisclaimer = false }) {
                    Text(Localization.get("btn_close", lang))
                }
            }
        )
    }
}
