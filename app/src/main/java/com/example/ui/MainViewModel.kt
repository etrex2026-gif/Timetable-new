package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.PreferencesManager
import com.example.data.TimetableData
import com.example.data.model.*
import kotlinx.coroutines.flow.*
import java.util.Calendar

enum class NavigationTab {
    TODAY, WEEKLY, TEACHERS, SEARCH, SETTINGS
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val prefsManager = PreferencesManager(application.applicationContext)
    val preferences: StateFlow<StudentPreferences> = prefsManager.preferences

    private val _currentTab = MutableStateFlow(NavigationTab.TODAY)
    val currentTab: StateFlow<NavigationTab> = _currentTab.asStateFlow()

    // Browsing section (can temporarily switch to view another section while keeping saved preference)
    private val _browsingSection = MutableStateFlow("12A")
    val browsingSection: StateFlow<String> = _browsingSection.asStateFlow()

    // Selected day for Today view
    private val _selectedDay = MutableStateFlow(detectInitialDay())
    val selectedDay: StateFlow<WeekDay> = _selectedDay.asStateFlow()

    // Search state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchAllSections = MutableStateFlow(false)
    val searchAllSections: StateFlow<Boolean> = _searchAllSections.asStateFlow()

    // Teacher search and subject filter
    private val _teacherSearch = MutableStateFlow("")
    val teacherSearch: StateFlow<String> = _teacherSearch.asStateFlow()

    private val _teacherSubjectFilter = MutableStateFlow<String?>(null)
    val teacherSubjectFilter: StateFlow<String?> = _teacherSubjectFilter.asStateFlow()

    // Detail dialogs
    private val _selectedPeriodDetail = MutableStateFlow<ResolvedPeriod?>(null)
    val selectedPeriodDetail: StateFlow<ResolvedPeriod?> = _selectedPeriodDetail.asStateFlow()

    private val _selectedTeacherDetail = MutableStateFlow<Teacher?>(null)
    val selectedTeacherDetail: StateFlow<Teacher?> = _selectedTeacherDetail.asStateFlow()

    private val _showValidationDialog = MutableStateFlow(false)
    val showValidationDialog: StateFlow<Boolean> = _showValidationDialog.asStateFlow()

    private val _showSetupWizard = MutableStateFlow(false)
    val showSetupWizard: StateFlow<Boolean> = _showSetupWizard.asStateFlow()

    // 5-second opening splash screen state
    private val _showSplash = MutableStateFlow(true)
    val showSplash: StateFlow<Boolean> = _showSplash.asStateFlow()

    init {
        // Sync browsing section with saved student section initially
        val initialSection = preferences.value.section
        _browsingSection.value = initialSection

        // Show setup wizard if first run not completed or preferences invalid
        if (!preferences.value.isValid) {
            _showSetupWizard.value = true
        }
    }

    fun dismissSplash() {
        _showSplash.value = false
    }

    private fun detectInitialDay(): WeekDay {
        val cal = Calendar.getInstance()
        return when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> WeekDay.MONDAY
            Calendar.TUESDAY -> WeekDay.TUESDAY
            Calendar.WEDNESDAY -> WeekDay.WEDNESDAY
            Calendar.THURSDAY -> WeekDay.THURSDAY
            Calendar.FRIDAY -> WeekDay.FRIDAY
            else -> WeekDay.MONDAY // Weekend defaults to Monday
        }
    }

    fun setTab(tab: NavigationTab) {
        _currentTab.value = tab
    }

    fun setBrowsingSection(section: String) {
        _browsingSection.value = section
    }

    fun setSelectedDay(day: WeekDay) {
        _selectedDay.value = day
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSearchAllSections(all: Boolean) {
        _searchAllSections.value = all
    }

    fun setTeacherSearch(query: String) {
        _teacherSearch.value = query
    }

    fun setTeacherSubjectFilter(filter: String?) {
        _teacherSubjectFilter.value = filter
    }

    fun openPeriodDetail(period: ResolvedPeriod) {
        _selectedPeriodDetail.value = period
    }

    fun closePeriodDetail() {
        _selectedPeriodDetail.value = null
    }

    fun openTeacherDetail(teacher: Teacher) {
        _selectedTeacherDetail.value = teacher
    }

    fun closeTeacherDetail() {
        _selectedTeacherDetail.value = null
    }

    fun setValidationDialogVisible(visible: Boolean) {
        _showValidationDialog.value = visible
    }

    fun setSetupWizardVisible(visible: Boolean) {
        _showSetupWizard.value = visible
    }

    fun saveSetup(section: String, language: String, elective12: String, elective4850: String, elective4951: String) {
        prefsManager.savePreferences(
            StudentPreferences(
                section = section,
                language = language,
                elective12 = elective12,
                elective4850 = elective4850,
                elective4951 = elective4951,
                themeMode = preferences.value.themeMode,
                isFirstRunCompleted = true
            )
        )
        _browsingSection.value = section
        _showSetupWizard.value = false
    }

    fun saveElectives(elective12: String, elective4850: String, elective4951: String) {
        val current = preferences.value
        prefsManager.savePreferences(
            current.copy(
                elective12 = elective12,
                elective4850 = elective4850,
                elective4951 = elective4951
            )
        )
    }

    fun updateSectionPref(section: String) {
        prefsManager.updateSection(section)
        _browsingSection.value = section
    }

    fun updateLanguagePref(lang: String) {
        prefsManager.updateLanguage(lang)
    }

    fun updateElective12(choice: String) {
        prefsManager.updateElective12(choice)
    }

    fun updateElective4850(choice: String) {
        prefsManager.updateElective4850(choice)
    }

    fun updateElective4951(choice: String) {
        prefsManager.updateElective4951(choice)
    }

    fun updateThemeMode(theme: String) {
        prefsManager.updateThemeMode(theme)
    }

    fun resetPreferences() {
        prefsManager.resetToDefaults()
        _browsingSection.value = "12A"
    }

    fun clearPreferences() {
        prefsManager.clearAllPreferences()
        _browsingSection.value = "12A"
        _showSetupWizard.value = true
    }

    // Resolves periods for today's schedule
    val todayPeriods: StateFlow<List<ResolvedPeriod>> = combine(
        _browsingSection,
        _selectedDay,
        preferences
    ) { section, day, prefs ->
        val rawCodes = TimetableData.rawTimetable[section]?.get(day) ?: emptyList()
        rawCodes.mapIndexed { index, code ->
            TimetableData.resolvePeriod(section, day, index + 1, code, prefs)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Resolves full weekly schedule
    val weeklySchedule: StateFlow<Map<WeekDay, List<ResolvedPeriod>>> = combine(
        _browsingSection,
        preferences
    ) { section, prefs ->
        val schedule = TimetableData.rawTimetable[section] ?: emptyMap()
        schedule.mapValues { (day, codes) ->
            codes.mapIndexed { index, code ->
                TimetableData.resolvePeriod(section, day, index + 1, code, prefs)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    // Filtered search results
    val searchResults: StateFlow<List<ResolvedPeriod>> = combine(
        _searchQuery,
        _searchAllSections,
        _browsingSection,
        preferences
    ) { query, searchAll, activeSection, prefs ->
        if (query.trim().isEmpty()) return@combine emptyList()

        val q = query.trim().lowercase()
        val sectionsToSearch = if (searchAll) TimetableData.sections else listOf(activeSection)
        val results = mutableListOf<ResolvedPeriod>()

        for (sec in sectionsToSearch) {
            val secSchedule = TimetableData.rawTimetable[sec] ?: continue
            for (day in WeekDay.values()) {
                val codes = secSchedule[day] ?: continue
                codes.forEachIndexed { idx, code ->
                    val resolved = TimetableData.resolvePeriod(sec, day, idx + 1, code, prefs)
                    val matches = resolved.subjectAbbr.lowercase().contains(q) ||
                            resolved.subjectFullName.lowercase().contains(q) ||
                            resolved.teacherName.lowercase().contains(q) ||
                            resolved.teacherCode.lowercase().contains(q) ||
                            resolved.rawCode.lowercase().contains(q) ||
                            day.fullNameEn.lowercase().contains(q) ||
                            day.fullNameOm.lowercase().contains(q) ||
                            "p${idx + 1}".contains(q) ||
                            "period ${idx + 1}".contains(q)

                    if (matches) {
                        results.add(resolved)
                    }
                }
            }
        }
        results
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Teacher directory filtered
    val filteredTeachers: StateFlow<List<Teacher>> = combine(
        _teacherSearch,
        _teacherSubjectFilter
    ) { query, subjectFilter ->
        val q = query.trim().lowercase()
        TimetableData.teachers.values.filter { teacher ->
            val matchesQuery = q.isEmpty() ||
                    teacher.name.lowercase().contains(q) ||
                    teacher.code.lowercase().contains(q) ||
                    teacher.subjectAbbr.lowercase().contains(q) ||
                    teacher.subjectFullName.lowercase().contains(q) ||
                    teacher.assignment.lowercase().contains(q)

            val matchesFilter = subjectFilter == null || teacher.subjectAbbr == subjectFilter
            matchesQuery && matchesFilter
        }.sortedBy { it.code.toIntOrNull() ?: 999 }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Find all periods taught by a specific teacher across Grade 12
    fun getTeacherPeriods(teacherCode: String): List<ResolvedPeriod> {
        val prefs = preferences.value
        val list = mutableListOf<ResolvedPeriod>()
        for (sec in TimetableData.sections) {
            val schedule = TimetableData.rawTimetable[sec] ?: continue
            for (day in WeekDay.values()) {
                val codes = schedule[day] ?: continue
                codes.forEachIndexed { idx, code ->
                    if (code == teacherCode ||
                        (teacherCode in listOf("9", "11") && code in listOf("9", "11")) ||
                        (code == "1/2" && (teacherCode == "1" || teacherCode == "2")) ||
                        (code == "48(50)" && (teacherCode == "48" || teacherCode == "50")) ||
                        (code in listOf("49(51)", "51(49)") && (teacherCode == "49" || teacherCode == "51"))
                    ) {
                        list.add(TimetableData.resolvePeriod(sec, day, idx + 1, code, prefs))
                    }
                }
            }
        }
        return list
    }
}
