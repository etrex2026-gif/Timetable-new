package com.example.util

object Localization {

    fun get(key: String, lang: String): String {
        val map = if (lang == "om") oromooMap else englishMap
        return map[key] ?: englishMap[key] ?: key
    }

    private val englishMap = mapOf(
        "app_title" to "Chercher Grade 12 Timetable",
        "app_subtitle" to "Chercher Secondary School • Sections 12A–12K",
        "academic_status" to "Academic Year 2019 E.C. (Tentative)",
        "verified_badge" to "Tentative Timetable",
        "school_confirmation_required" to "Information requires school confirmation",
        "tab_today" to "Today",
        "tab_weekly" to "Weekly",
        "tab_teachers" to "Teachers",
        "tab_search" to "Search",
        "tab_settings" to "Settings",
        
        // Setup / Onboarding
        "setup_welcome" to "Welcome to Chercher Grade 12",
        "setup_desc" to "Set up your section and confirmed personal elective subjects to personalize your schedule display.",
        "setup_section_label" to "Select Your Section (12A–12K)",
        "setup_language_label" to "Interface Language",
        "setup_electives_title" to "Confirmed Personal Alternative Choices",
        "setup_electives_desc" to "A student preference only changes your personal display, not the official school assignment.",
        "setup_elective_12_label" to "Language Alternative (Code 1/2)",
        "setup_elective_12_opt1" to "Afaan Oromoo (Tadele Olani - Code 1)",
        "setup_elective_12_opt2" to "Amharic (Tenagne Tamiru - Code 2)",
        "setup_elective_4850_label" to "Vocational Alternative (Code 48/50)",
        "setup_elective_4850_opt1" to "Health (Ramatulahi* - Code 48)",
        "setup_elective_4850_opt2" to "Construction (Bakkalcha - Code 50)",
        "setup_storage_notice" to "Notice: Preferences are saved locally on this device. Official timetable and teacher data are immutable bundled static data.",
        "setup_btn_save" to "Save & Open Timetable",
        
        // Today Screen
        "today_classes" to "Today's Schedule",
        "no_classes_weekend" to "Weekend • Showing Monday's Schedule",
        "period" to "Period",
        "free_period" to "Free Period (No Class)",
        "assigned_teacher" to "Teacher",
        "subject" to "Subject",
        "room_code" to "Teacher Code",
        "elective_badge" to "Personal Alternative",
        "requires_confirmation_badge" to "Pending Verification",
        
        // Weekly Screen
        "weekly_matrix" to "Weekly Schedule Matrix",
        "table_view" to "Table Matrix",
        "day_view" to "Day Cards",
        "tap_cell_hint" to "Tap any cell to view teacher, code, and official status",
        
        // Teacher Directory
        "teacher_directory" to "Teacher Directory",
        "search_teachers" to "Search by teacher name, code, or subject...",
        "all_subjects" to "All Subjects",
        "teachers_count" to "teachers registered",
        "grade12_assignment" to "Grade 12 Assignment",
        "unconfirmed_status" to "Grade 12 assignment unconfirmed",
        "view_teacher_schedule" to "View classes for this teacher",
        
        // Search
        "search_title" to "Timetable Search",
        "search_placeholder" to "Search subject, teacher, code, day, period...",
        "scope_my_section" to "My Section",
        "scope_all_grade12" to "All Grade 12 (12A–12K)",
        "search_results" to "Found",
        "results_label" to "periods matching your search",
        "no_results" to "No periods found matching your query",
        
        // Settings & About
        "settings_title" to "Preferences & System Info",
        "section_preference" to "Section Preference",
        "language_preference" to "Language / Afaan",
        "theme_preference" to "Theme Mode",
        "theme_system" to "System Default",
        "theme_light" to "Light Mode",
        "theme_dark" to "Dark Navy Mode",
        "elective_preferences" to "Alternative Subject Choices",
        "print_share_title" to "Export & Print Schedule",
        "print_share_desc" to "Generate a print-ready formatted timetable summary for",
        "btn_print" to "Print / Share Weekly Schedule",
        "validation_title" to "Official Dataset Integrity",
        "btn_run_validation" to "View Validation Report",
        "official_data_policy_title" to "Read-Only / Storage Separation",
        "official_data_policy_desc" to "Official timetable and teacher data are immutable bundled static data. Student preference storage is separate and never mutates official records. Edits are purely client-side UI display preferences.",
        "btn_reset_prefs" to "Reset Preferences to Default",
        "btn_clear_storage" to "Clear Stored Preferences",
        "clear_confirm_title" to "Clear Stored Preferences?",
        "clear_confirm_desc" to "This will erase your locally saved section and elective choices on this device. Official school data will remain completely intact.",
        "dialog_confirm" to "Clear",
        "dialog_cancel" to "Cancel",
        
        // Detail Dialog
        "detail_title" to "Period Details",
        "detail_section" to "Section",
        "detail_day" to "Day",
        "detail_period" to "Period",
        "detail_raw_code" to "Raw Timetable Code",
        "detail_official_status" to "Status",
        "detail_assigned_teacher" to "Assigned Teacher",
        "detail_alternatives" to "Official Alternatives for this Cell",
        "btn_close" to "Close"
    )

    private val oromooMap = mapOf(
        "app_title" to "Sagantaa Kutaa 12ffaa Carcher",
        "app_subtitle" to "Mana Barumsaa Qophaa'ina Carcher • Kutaalee 12A–12K",
        "academic_status" to "Bara Barnootaa 2019 E.C. (Yeroo Murteessaa Hin Taane)",
        "verified_badge" to "Sagantaa Yeroo",
        "school_confirmation_required" to "Odeeffannoon mirkaneeffannaa mana barumsaa barbaada",
        "tab_today" to "Har'a",
        "tab_weekly" to "Torbanee",
        "tab_teachers" to "Barsiisota",
        "tab_search" to "Barbaadi",
        "tab_settings" to "Qindaa'ina",
        
        // Setup / Onboarding
        "setup_welcome" to "Baga Nagaan Dhuftan!",
        "setup_desc" to "Sagantaa keessan sirriitti argachuuf kutaa fi filannoo barnootaa keessan mirkanaa'e filadhaa.",
        "setup_section_label" to "Kutaa Keessan Filadhaa (12A–12K)",
        "setup_language_label" to "Afaan Appii",
        "setup_electives_title" to "Filannoo Barnootaa Dhuunfaa Mirkanaa'e",
        "setup_electives_desc" to "Filannoon keessan agarsiisa dhuunfaa qofa jijjiira; ramaddii mana barumsaa hin tuqu.",
        "setup_elective_12_label" to "Filannoo Afaanii (Koodii 1/2)",
        "setup_elective_12_opt1" to "Afaan Oromoo (Tadele Olani - Koodii 1)",
        "setup_elective_12_opt2" to "Amharic (Tenagne Tamiru - Koodii 2)",
        "setup_elective_4850_label" to "Filannoo Oomishaa/Hojii (Koodii 48/50)",
        "setup_elective_4850_opt1" to "Fayyaa / Health (Ramatulahi* - Koodii 48)",
        "setup_elective_4850_opt2" to "Ijaarsa / Constru (Bakkalcha - Koodii 50)",
        "setup_storage_notice" to "Hubachiisa: Filannoon keessan meeshaa kana irratti qofa kuufama. Daataan mana barumsaa hin jijjiiramu.",
        "setup_btn_save" to "Kuusi & Sagantaa Bani",
        
        // Today Screen
        "today_classes" to "Sagantaa Har'aa",
        "no_classes_weekend" to "Sanbata • Sagantaan Wiixataa Agarsiifamaera",
        "period" to "Marsaa",
        "free_period" to "Yeroo Boqonnaa (Kilaasii Hin Qabu)",
        "assigned_teacher" to "Barsiisaa",
        "subject" to "Gosa Barnootaa",
        "room_code" to "Koodii Barsiisaa",
        "elective_badge" to "Filannoo Dhuunfaa",
        "requires_confirmation_badge" to "Mirkaneeffannaa Barbaada",
        
        // Weekly Screen
        "weekly_matrix" to "Gabatee Sagantaa Torbanii",
        "table_view" to "Gabatee Guutuu",
        "day_view" to "Guyyootaan",
        "tap_cell_hint" to "Bal'ina arguuf koodii kamiyyuu tuqi",
        
        // Teacher Directory
        "teacher_directory" to "Galmee Barsiisotaa",
        "search_teachers" to "Maqaa, koodii, ykn gosa barnootaan barbaadi...",
        "all_subjects" to "Gosoota Barnootaa Hunda",
        "teachers_count" to "barsiisota galmaa'an",
        "grade12_assignment" to "Ramaddii Kutaa 12ffaa",
        "unconfirmed_status" to "Ramaddiin kutaa 12ffaa hin mirkanoofne",
        "view_teacher_schedule" to "Sagantaa barsiisaa kanaa ilaali",
        
        // Search
        "search_title" to "Sagantaa Keessaa Barbaadi",
        "search_placeholder" to "Gosa barnootaa, barsiisaa, koodii, guyyaa...",
        "scope_my_section" to "Kutaa Kiyya",
        "scope_all_grade12" to "Kutaalee 12ffaa Hunda (12A–12K)",
        "search_results" to "Argaman",
        "results_label" to "marsaalee barbaaddan wajjin walsiman",
        "no_results" to "Wanti barbaaddan hin argamne",
        
        // Settings & About
        "settings_title" to "Qindaa'ina & Odeeffannoo",
        "section_preference" to "Filannoo Kutaa",
        "language_preference" to "Afaan Appii",
        "theme_preference" to "Bifa Agarsiisaa",
        "theme_system" to "Kan Moobaayilaa",
        "theme_light" to "Ifaa",
        "theme_dark" to "Bifa Dukkanaa'aa (Navy)",
        "elective_preferences" to "Filannoowwan Barnootaa Dhuunfaa",
        "print_share_title" to "Sagantaa Maxxansi / Qoodi",
        "print_share_desc" to "Sagantaa torbanii qulqullinaan qophaa'e maxxansi kutaa:",
        "btn_print" to "Sagantaa Torbanii Maxxansi / Qoodi",
        "validation_title" to "Mirkaneeffannaa Daataa Mana Barumsaa",
        "btn_run_validation" to "Gabaasa Mirkaneeffannaa Ilaali",
        "official_data_policy_title" to "Qulqullina Daataa / Adda Baasuu",
        "official_data_policy_desc" to "Sagantaan mana barumsaa fi odeeffannoon barsiisotaa daataa hin tuqamneedha. Qindaa'inni keessan meeshaa keessan irratti qofa ta'a.",
        "btn_reset_prefs" to "Qindaa'ina Deebisi",
        "btn_clear_storage" to "Qindaa'ina Haqaa",
        "clear_confirm_title" to "Qindaa'inni Haa Haqamu?",
        "clear_confirm_desc" to "Kutaa fi filannoowwan keessan meeshaa kana irraa haqama. Daataan mana barumsaa garuu hin tuqamu.",
        "dialog_confirm" to "Haqi",
        "dialog_cancel" to "Dhiisi",
        
        // Detail Dialog
        "detail_title" to "Bal'ina Marsaa Barnootaa",
        "detail_section" to "Kutaa",
        "detail_day" to "Guyyaa",
        "detail_period" to "Marsaa",
        "detail_raw_code" to "Koodii Sagantaa Isa Jalqabaa",
        "detail_official_status" to "Haala Mirkaneeffannaa",
        "detail_assigned_teacher" to "Barsiisaa Ramadame",
        "detail_alternatives" to "Filannoowwan Koodii Kanaa",
        "btn_close" to "Cufi"
    )
}
