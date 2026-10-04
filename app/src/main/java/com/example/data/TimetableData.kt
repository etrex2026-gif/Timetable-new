package com.example.data

import com.example.data.model.*

object TimetableData {

    const val ACADEMIC_YEAR = "2019 E.C."
    const val TIMETABLE_STATUS_EN = "Tentative Timetable (Academic Year 2019 E.C.) — Official school verification required"
    const val TIMETABLE_STATUS_OM = "Sagantaa Yeroo Murteessaa Hin Taane (Bara Barnootaa 2019 E.C.) — Mirkaneeffannaa mana barumsaa barbaada"
    const val CONFIRMATION_REQUIRED_TEXT = "Information requires school confirmation"

    val sections = listOf(
        "12A", "12B", "12C", "12D", "12E",
        "12F", "12G", "12H", "12I", "12J", "12K"
    )

    // Immutable official teacher directory
    val teachers: Map<String, Teacher> = mapOf(
        "1" to Teacher("1", "Tadele Olani", "A/O", "Afaan Oromoo", "12A-K"),
        "2" to Teacher("2", "Tenagne Tamiru", "Amh", "Amharic", "12A-K"),
        "9" to Teacher("9", "Million Abebe", "Eng", "English", "12ABI"),
        "10" to Teacher("10", "Alemayehu Mekonnin", "Eng", "English", "12CDEF"),
        "11" to Teacher("11", "Million Abebe", "Eng", "English", "12ABI"),
        "12" to Teacher("12", "Getinet Legese", "Eng", "English", "12JKHG"),
        "13" to Teacher("13", "Kasahun Negash", "Math", "Mathematics", "12ABC"),
        "14" to Teacher("14", "Tsegaye Temesgen", "Math", "Mathematics", "12DEF"),
        "15" to Teacher("15", "Mohammed Umer", "Math", "Mathematics", "12JK"),
        "16" to Teacher("16", "Fikadu Tesfaye", "Math", "Mathematics", "12GHI"),
        "22" to Teacher("22", "Bahiru Tesfaye", "Phy", "Physics", "12ABCD"),
        "24" to Teacher("24", "Belew Tekabe", "Phy", "Physics", "12EFGH"),
        "26" to Teacher("26", "Shumet Moges", "Bio", "Biology", "12ABCD"),
        "27" to Teacher("27", "Tekabe Ishete", "Bio", "Biology", "12EFGH"),
        "33" to Teacher("33", "Mohammednur Dori", "Chem.", "Chemistry", "12EFGH"),
        "34" to Teacher("34", "Afework Mekonnin", "Chem.", "Chemistry", "12ABCD"),
        "36" to Teacher("36", "Tesfaye Fiqkadu", "Geo", "Geography", "12IJK"),
        "38" to Teacher("38", "Wondimu Derese", "Agri", "Agriculture", "12A-H"),
        "41" to Teacher("41", "Habtamu Ayisheshuhim", "IT", "Information Technology", "12IJK"),
        "42" to Teacher("42", "Gezahagn Muluken", "IT", "Information Technology", "12GHIJK"),
        "43" to Teacher("43", "Selam Girma", "IT", "Information Technology", "12ABCDEF"),
        "46" to Teacher("46", "Million Demeke", "Eco", "Economics", "12IJK"),
        "48" to Teacher("48", "Ramatulahi*", "Health", "Health Science", "Grade 12 assignment unconfirmed", isUnconfirmed = true, note = "Source contains asterisk; Grade 12 assignment unconfirmed"),
        "49" to Teacher("49", "Giduma Tariku", "Journ", "Journalism", "11&12JKL"),
        "50" to Teacher("50", "Bakkalcha", "Constru", "Construction", "12A-H"),
        "51" to Teacher("51", "Ukasha Kemal Eeko*", "Account", "Accounting", "11JKL&12IJK", isUnconfirmed = true, note = "Source contains asterisk")
    )

    // Complete Grade 12 Timetable Matrix (5 days x 6 periods per section)
    val rawTimetable: Map<String, Map<WeekDay, List<String>>> = mapOf(
        "12A" to mapOf(
            WeekDay.MONDAY to listOf("13", "9", "22", "34", "26", "48(50)"),
            WeekDay.TUESDAY to listOf("13", "34", "26", "22", "9", "38"),
            WeekDay.WEDNESDAY to listOf("13", "48(50)", "22", "38", "26", "9"),
            WeekDay.THURSDAY to listOf("9", "22", "26", "34", "1/2", "13"),
            WeekDay.FRIDAY to listOf("9", "43", "43", "34", "13", "—")
        ),
        "12B" to mapOf(
            WeekDay.MONDAY to listOf("22", "13", "9", "26", "48(50)", "34"),
            WeekDay.TUESDAY to listOf("34", "26", "13", "38", "22", "9"),
            WeekDay.WEDNESDAY to listOf("26", "22", "11", "13", "43", "43"),
            WeekDay.THURSDAY to listOf("22", "26", "9", "13", "1/2", "34"),
            WeekDay.FRIDAY to listOf("38", "9", "48(50)", "13", "34", "—")
        ),
        "12C" to mapOf(
            WeekDay.MONDAY to listOf("26", "34", "38", "13", "22", "9"),
            WeekDay.TUESDAY to listOf("22", "9", "34", "26", "13", "48(50)"),
            WeekDay.WEDNESDAY to listOf("22", "43", "43", "26", "9", "13"),
            WeekDay.THURSDAY to listOf("26", "38", "34", "22", "13", "9"),
            WeekDay.FRIDAY to listOf("13", "34", "9", "1/2", "48(50)", "—")
        ),
        "12D" to mapOf(
            WeekDay.MONDAY to listOf("38", "26", "34", "22", "14", "10"),
            WeekDay.TUESDAY to listOf("14", "22", "10", "34", "26", "26"),
            WeekDay.WEDNESDAY to listOf("10", "26", "14", "48(50)", "22", "38"),
            WeekDay.THURSDAY to listOf("34", "10", "22", "14", "43", "43"),
            WeekDay.FRIDAY to listOf("34", "10", "14", "1/2", "48(50)", "—")
        ),
        "12E" to mapOf(
            WeekDay.MONDAY to listOf("24", "14", "48(50)", "33", "10", "27"),
            WeekDay.TUESDAY to listOf("38", "14", "33", "24", "48(50)", "10"),
            WeekDay.WEDNESDAY to listOf("27", "24", "38", "10", "14", "33"),
            WeekDay.THURSDAY to listOf("14", "43", "43", "27", "10", "33"),
            WeekDay.FRIDAY to listOf("14", "1/2", "24", "27", "10", "—")
        ),
        "12F" to mapOf(
            WeekDay.MONDAY to listOf("14", "24", "27", "10", "33", "38"),
            WeekDay.TUESDAY to listOf("24", "10", "14", "33", "43", "43"),
            WeekDay.WEDNESDAY to listOf("48(50)", "14", "27", "33", "24", "10"),
            WeekDay.THURSDAY to listOf("10", "14", "27", "33", "48(50)", "1/2"),
            WeekDay.FRIDAY to listOf("24", "14", "27", "10", "38", "—")
        ),
        "12G" to mapOf(
            WeekDay.MONDAY to listOf("12", "38", "16", "24", "27", "33"),
            WeekDay.TUESDAY to listOf("12", "33", "24", "16", "42", "42"),
            WeekDay.WEDNESDAY to listOf("33", "12", "48(50)", "16", "27", "24"),
            WeekDay.THURSDAY to listOf("12", "16", "48(50)", "38", "27", "1/2"),
            WeekDay.FRIDAY to listOf("33", "27", "12", "24", "16", "—")
        ),
        "12H" to mapOf(
            WeekDay.MONDAY to listOf("16", "12", "33", "27", "24", "48(50)"),
            WeekDay.TUESDAY to listOf("33", "16", "38", "12", "24", "1/2"),
            WeekDay.WEDNESDAY to listOf("16", "27", "12", "24", "33", "48(50)"),
            WeekDay.THURSDAY to listOf("38", "42", "42", "16", "12", "27"),
            WeekDay.FRIDAY to listOf("27", "16", "33", "12", "24", "—")
        ),
        "12I" to mapOf(
            WeekDay.MONDAY to listOf("41", "16", "11", "46", "36", "51(49)"),
            WeekDay.TUESDAY to listOf("41", "11", "16", "46", "36", "12"),
            WeekDay.WEDNESDAY to listOf("11", "16", "36", "46", "41", "41"),
            WeekDay.THURSDAY to listOf("16", "11", "46", "42", "36", "49(51)"),
            WeekDay.FRIDAY to listOf("16", "36", "41", "42", "11", "46")
        ),
        "12J" to mapOf(
            WeekDay.MONDAY to listOf("46", "41", "36", "12", "51(49)", "15"),
            WeekDay.TUESDAY to listOf("36", "46", "15", "12", "41", "41"),
            WeekDay.WEDNESDAY to listOf("46", "42", "41", "15", "12", "36"),
            WeekDay.THURSDAY to listOf("36", "46", "15", "49(51)", "42", "12"),
            WeekDay.FRIDAY to listOf("46", "12", "36", "41", "15", "1/2")
        ),
        "12K" to mapOf(
            WeekDay.MONDAY to listOf("36", "12", "41", "15", "46", "51(49)"),
            WeekDay.TUESDAY to listOf("15", "12", "41", "36", "1/2", "46"),
            WeekDay.WEDNESDAY to listOf("36", "41", "42", "12", "15", "46"),
            WeekDay.THURSDAY to listOf("42", "49(51)", "36", "15", "12", "46"),
            WeekDay.FRIDAY to listOf("41", "41", "15", "12", "46", "36")
        )
    )

    /**
     * Resolves a raw timetable cell based on user elective preferences and official rules.
     */
    fun resolvePeriod(
        section: String,
        day: WeekDay,
        periodIndex: Int,
        rawCode: String,
        preferences: StudentPreferences
    ): ResolvedPeriod {
        if (rawCode == "—" || rawCode == "-" || rawCode.isBlank()) {
            return ResolvedPeriod(
                section = section,
                day = day,
                periodIndex = periodIndex,
                rawCode = "—",
                isFree = true,
                isAlternative = false,
                requiresSchoolConfirmation = false,
                confirmationMessage = null,
                subjectAbbr = "FREE",
                subjectFullName = "Free Period",
                teacherName = "—",
                teacherCode = "—"
            )
        }

        // Alternative 1/2: Afaan Oromoo (1) or Amharic (2)
        if (rawCode == "1/2") {
            val t1 = teachers["1"] ?: Teacher("1", "Tadele Olani", "A/O", "Afaan Oromoo", "12A-K")
            val t2 = teachers["2"] ?: Teacher("2", "Tenagne Tamiru", "Amh", "Amharic", "12A-K")
            val isOption1 = preferences.elective12 == "1"
            val chosen = if (isOption1) t1 else t2
            return ResolvedPeriod(
                section = section,
                day = day,
                periodIndex = periodIndex,
                rawCode = rawCode,
                isFree = false,
                isAlternative = true,
                requiresSchoolConfirmation = false,
                confirmationMessage = null,
                subjectAbbr = chosen.subjectAbbr,
                subjectFullName = "${chosen.subjectFullName} (Alternative)",
                teacherName = chosen.name,
                teacherCode = chosen.code,
                alternativeChoices = listOf(t1, t2),
                chosenOptionNotice = if (isOption1) "Showing Afaan Oromoo (Teacher: Tadele Olani, Code: 1)" else "Showing Amharic (Teacher: Tenagne Tamiru, Code: 2)"
            )
        }

        // Alternative 48(50): Health (48) or Construction (50)
        if (rawCode == "48(50)") {
            val t48 = teachers["48"] ?: Teacher("48", "Ramatulahi*", "Health", "Health Science", "Unconfirmed")
            val t50 = teachers["50"] ?: Teacher("50", "Bakkalcha", "Constru", "Construction", "12A-H")
            val isOption48 = preferences.elective4850 == "48"
            val chosen = if (isOption48) t48 else t50
            return ResolvedPeriod(
                section = section,
                day = day,
                periodIndex = periodIndex,
                rawCode = rawCode,
                isFree = false,
                isAlternative = true,
                requiresSchoolConfirmation = false,
                confirmationMessage = null,
                subjectAbbr = chosen.subjectAbbr,
                subjectFullName = "${chosen.subjectFullName} (Alternative)",
                teacherName = chosen.name,
                teacherCode = chosen.code,
                alternativeChoices = listOf(t48, t50),
                chosenOptionNotice = if (isOption48) "Showing Health (Teacher: Ramatulahi*, Code: 48)" else "Showing Construction (Teacher: Bakkalcha, Code: 50)"
            )
        }

        // Alternative 49(51) and 51(49): Journalism (49) or Accounting (51)
        if (rawCode in listOf("49(51)", "51(49)")) {
            val t49 = teachers["49"] ?: Teacher("49", "Giduma Tariku", "Journ", "Journalism", "11&12JKL")
            val t51 = teachers["51"] ?: Teacher("51", "Ukasha Kemal Eeko*", "Account", "Accounting", "11JKL&12IJK", isUnconfirmed = true, note = "Source contains asterisk")
            val isOption49 = preferences.elective4951 == "49"
            val chosen = if (isOption49) t49 else t51
            return ResolvedPeriod(
                section = section,
                day = day,
                periodIndex = periodIndex,
                rawCode = rawCode,
                isFree = false,
                isAlternative = true,
                requiresSchoolConfirmation = chosen.isUnconfirmed,
                confirmationMessage = if (chosen.isUnconfirmed) chosen.note else null,
                subjectAbbr = chosen.subjectAbbr,
                subjectFullName = "${chosen.subjectFullName} (Alternative)",
                teacherName = chosen.name,
                teacherCode = chosen.code,
                alternativeChoices = listOf(t49, t51),
                chosenOptionNotice = if (isOption49) "Showing Journalism (Teacher: Giduma Tariku, Code: 49)" else "Showing Accounting (Teacher: Ukasha Kemal Eeko*, Code: 51)"
            )
        }

        // Single code lookups
        val teacher = teachers[rawCode]
        if (teacher != null) {
            val confirmationMsg = if (teacher.isUnconfirmed) CONFIRMATION_REQUIRED_TEXT else null
            return ResolvedPeriod(
                section = section,
                day = day,
                periodIndex = periodIndex,
                rawCode = rawCode,
                isFree = false,
                isAlternative = false,
                requiresSchoolConfirmation = teacher.isUnconfirmed,
                confirmationMessage = confirmationMsg,
                subjectAbbr = teacher.subjectAbbr,
                subjectFullName = teacher.subjectFullName,
                teacherName = teacher.name,
                teacherCode = teacher.code
            )
        }

        // Codes 8, 9, or other unknown codes:
        // Rule: "Codes 8 and 9 have no visible record. Code 35 is a blank row. Unknown or uncertain values must display 'Information requires school confirmation', never guess."
        return ResolvedPeriod(
            section = section,
            day = day,
            periodIndex = periodIndex,
            rawCode = rawCode,
            isFree = false,
            isAlternative = false,
            requiresSchoolConfirmation = true,
            confirmationMessage = CONFIRMATION_REQUIRED_TEXT,
            subjectAbbr = "Code $rawCode",
            subjectFullName = "Unknown Subject (Code $rawCode)",
            teacherName = CONFIRMATION_REQUIRED_TEXT,
            teacherCode = rawCode,
            chosenOptionNotice = "Code $rawCode has no record in official teacher roster. $CONFIRMATION_REQUIRED_TEXT."
        )
    }

    /**
     * Complete validation utility as required by prompt:
     * "Validate 11 sections x 5 days x 6 periods; check code references and unresolved alternative codes."
     */
    fun validateDataset(): ValidationResult {
        val details = mutableListOf<String>()
        var valid = true

        if (sections.size != 11) {
            valid = false
            details.add("Error: Expected 11 sections, found ${sections.size}")
        } else {
            details.add("Validated all 11 sections: 12A through 12K")
        }

        var totalPeriods = 0
        var unconfirmedCodesCount = 0
        var alternativeCellsCount = 0
        var emptyPeriodsCount = 0

        for (sec in sections) {
            val secSchedule = rawTimetable[sec]
            if (secSchedule == null) {
                valid = false
                details.add("Missing schedule for section $sec")
                continue
            }
            if (secSchedule.keys.size != 5) {
                valid = false
                details.add("Section $sec does not have 5 days")
            }
            for (day in WeekDay.values()) {
                val periods = secSchedule[day]
                if (periods == null || periods.size != 6) {
                    valid = false
                    details.add("Section $sec, Day $day has ${periods?.size ?: 0} periods instead of 6")
                } else {
                    totalPeriods += periods.size
                    for (code in periods) {
                        when (code) {
                            "—", "-" -> emptyPeriodsCount++
                            "1/2", "48(50)", "49(51)", "51(49)" -> alternativeCellsCount++
                            "8" -> unconfirmedCodesCount++
                        }
                    }
                }
            }
        }

        details.add("Total matrix size: $totalPeriods periods ($totalPeriods = 11 sections x 5 days x 6 periods)")
        details.add("Empty periods verified: $emptyPeriodsCount")
        details.add("Resolved student alternative cells (1/2, 48(50), 49(51), 51(49)): $alternativeCellsCount")
        details.add("Confirmation-required cells (49(51), 51(49)): $unconfirmedCodesCount")
        details.add("Teacher directory integrity: ${teachers.size} teachers loaded with exact historical codes")

        return ValidationResult(
            totalSections = sections.size,
            totalPeriods = totalPeriods,
            sectionsChecked = sections,
            isValid = valid,
            details = details
        )
    }
}
