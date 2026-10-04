package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.StudentPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesManager(context: Context) {

    companion object {
        const val PREFS_KEY = "chercher_grade12_preferences_v1"
        private const val KEY_SECTION = "selected_section"
        private const val KEY_LANGUAGE = "selected_language"
        private const val KEY_ELECTIVE_12 = "elective_12"
        private const val KEY_ELECTIVE_4850 = "elective_4850"
        private const val KEY_ELECTIVE_4951 = "elective_4951"
        private const val KEY_THEME = "theme_mode"
        private const val KEY_FIRST_RUN_DONE = "first_run_completed"

        const val STORAGE_DISCLAIMER_EN =
            "Your preferences (section, language, personal elective choices) are stored strictly on this device under '$PREFS_KEY'. " +
            "Official school timetable records and teacher assignments are immutable bundled data and cannot be altered. " +
            "Preferences will remain on this device unless app data is cleared or the app is uninstalled."

        const val STORAGE_DISCLAIMER_OM =
            "Filannoowwan keessan (kutaa, afaan, filannoo barnootaa) meeshaa kana irratti qofa qindaa'ina '$PREFS_KEY' jalatti kuufamu. " +
            "Galmeen sagantaa mana barumsaa fi ramaddiin barsiisotaa hin jijjiiramu, hin haqamus. " +
            "Hanga daataan appii haqamutti ykn appiin balleeffamutti meeshaa kana irratti tura."
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

    private val _preferences = MutableStateFlow(loadPreferences())
    val preferences: StateFlow<StudentPreferences> = _preferences.asStateFlow()

    fun loadPreferences(): StudentPreferences {
        try {
            val section = sharedPreferences.getString(KEY_SECTION, "12A") ?: "12A"
            val language = sharedPreferences.getString(KEY_LANGUAGE, "en") ?: "en"
            val elective12 = sharedPreferences.getString(KEY_ELECTIVE_12, "1") ?: "1"
            val elective4850 = sharedPreferences.getString(KEY_ELECTIVE_4850, "48") ?: "48"
            val elective4951 = sharedPreferences.getString(KEY_ELECTIVE_4951, "49") ?: "49"
            val themeMode = sharedPreferences.getString(KEY_THEME, "system") ?: "system"
            val isFirstRunCompleted = sharedPreferences.getBoolean(KEY_FIRST_RUN_DONE, false)

            val prefs = StudentPreferences(
                section = section,
                language = language,
                elective12 = elective12,
                elective4850 = elective4850,
                elective4951 = elective4951,
                themeMode = themeMode,
                isFirstRunCompleted = isFirstRunCompleted
            )

            // Requirement: "If preferences are invalid or incomplete, return the student to the setup screen rather than silently choosing an option."
            if (isFirstRunCompleted && !prefs.isValid) {
                return StudentPreferences(isFirstRunCompleted = false)
            }

            return prefs
        } catch (e: Exception) {
            // Corrupted or unreadable preferences fallback to setup
            return StudentPreferences(isFirstRunCompleted = false)
        }
    }

    fun savePreferences(prefs: StudentPreferences) {
        sharedPreferences.edit().apply {
            putString(KEY_SECTION, prefs.section)
            putString(KEY_LANGUAGE, prefs.language)
            putString(KEY_ELECTIVE_12, prefs.elective12)
            putString(KEY_ELECTIVE_4850, prefs.elective4850)
            putString(KEY_ELECTIVE_4951, prefs.elective4951)
            putString(KEY_THEME, prefs.themeMode)
            putBoolean(KEY_FIRST_RUN_DONE, true)
            apply()
        }
        _preferences.value = prefs.copy(isFirstRunCompleted = true)
    }

    fun updateSection(newSection: String) {
        val current = _preferences.value
        savePreferences(current.copy(section = newSection))
    }

    fun updateLanguage(newLang: String) {
        val current = _preferences.value
        savePreferences(current.copy(language = newLang))
    }

    fun updateElective12(choice: String) {
        val current = _preferences.value
        savePreferences(current.copy(elective12 = choice))
    }

    fun updateElective4850(choice: String) {
        val current = _preferences.value
        savePreferences(current.copy(elective4850 = choice))
    }

    fun updateElective4951(choice: String) {
        val current = _preferences.value
        savePreferences(current.copy(elective4951 = choice))
    }

    fun updateThemeMode(theme: String) {
        val current = _preferences.value
        savePreferences(current.copy(themeMode = theme))
    }

    fun resetToDefaults() {
        val defaultPrefs = StudentPreferences(
            section = "12A",
            language = "en",
            elective12 = "1",
            elective4850 = "48",
            elective4951 = "49",
            themeMode = "system",
            isFirstRunCompleted = true
        )
        savePreferences(defaultPrefs)
    }

    fun clearAllPreferences() {
        sharedPreferences.edit().clear().apply()
        _preferences.value = StudentPreferences(isFirstRunCompleted = false)
    }
}
