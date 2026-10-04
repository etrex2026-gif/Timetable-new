package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

enum class WeekDay(val code: String, val shortNameEn: String, val shortNameOm: String, val fullNameEn: String, val fullNameOm: String) {
    MONDAY("mon", "Mon", "Wix", "Monday", "Wiixata"),
    TUESDAY("tue", "Tue", "Kib", "Tuesday", "Kibxata"),
    WEDNESDAY("wed", "Wed", "Roob", "Wednesday", "Roobii"),
    THURSDAY("thu", "Thu", "Kam", "Thursday", "Kamisa"),
    FRIDAY("fri", "Fri", "Jim", "Friday", "Jimaata")
}

data class Teacher(
    val code: String,
    val name: String,
    val subjectAbbr: String,
    val subjectFullName: String,
    val assignment: String,
    val isUnconfirmed: Boolean = false,
    val note: String? = null
)

data class StudentPreferences(
    val section: String = "12A",
    val language: String = "en", // "en" or "om"
    val elective12: String = "1", // "1" (Afaan Oromoo) or "2" (Amharic)
    val elective4850: String = "48", // "48" (Health) or "50" (Construction)
    val elective4951: String = "49", // "49" (Journalism) or "51" (Accounting)
    val themeMode: String = "system", // "system", "light", "dark"
    val isFirstRunCompleted: Boolean = false
) {
    val isValid: Boolean
        get() = isFirstRunCompleted &&
                section in listOf("12A", "12B", "12C", "12D", "12E", "12F", "12G", "12H", "12I", "12J", "12K") &&
                language in listOf("en", "om") &&
                elective12 in listOf("1", "2") &&
                elective4850 in listOf("48", "50") &&
                elective4951 in listOf("49", "51")
}

data class ResolvedPeriod(
    val section: String,
    val day: WeekDay,
    val periodIndex: Int, // 1 to 6
    val rawCode: String,
    val isFree: Boolean,
    val isAlternative: Boolean,
    val requiresSchoolConfirmation: Boolean,
    val confirmationMessage: String?,
    val subjectAbbr: String,
    val subjectFullName: String,
    val teacherName: String,
    val teacherCode: String,
    val alternativeChoices: List<Teacher> = emptyList(),
    val chosenOptionNotice: String? = null
) {
    val periodLabel: String get() = "P$periodIndex"

    val subjectColor: Color
        get() = when {
            isFree -> SubjectFree
            requiresSchoolConfirmation -> SubjectWarning
            rawCode == "1/2" -> SubjectElectiveLang
            rawCode == "48(50)" -> SubjectVocational
            rawCode in listOf("49(51)", "51(49)") -> SubjectJournAcc
            rawCode in listOf("13", "14", "15", "16") -> SubjectMath
            rawCode in listOf("9", "10", "11", "12") -> SubjectEnglish
            rawCode in listOf("22", "24") -> SubjectPhysics
            rawCode in listOf("33", "34") -> SubjectChemistry
            rawCode in listOf("26", "27") -> SubjectBiology
            rawCode == "36" -> SubjectGeography
            rawCode == "38" -> SubjectAgri
            rawCode in listOf("41", "42", "43") -> SubjectIT
            rawCode == "46" -> SubjectEco
            rawCode == "1" -> SubjectElectiveLang
            rawCode == "2" -> SubjectElectiveLang
            rawCode == "48" -> SubjectVocational
            rawCode == "50" -> SubjectVocational
            rawCode == "49" -> SubjectJournAcc
            rawCode == "51" -> SubjectJournAcc
            else -> SubjectWarning
        }
}

data class SectionSchedule(
    val section: String,
    val schedule: Map<WeekDay, List<String>>
)

data class ValidationResult(
    val totalSections: Int,
    val totalPeriods: Int,
    val sectionsChecked: List<String>,
    val isValid: Boolean,
    val details: List<String>
)
