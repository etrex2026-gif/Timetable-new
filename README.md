# Chercher Secondary School — Grade 12 Smart Timetable

An Android application built with **Kotlin** and **Jetpack Compose** tailored exclusively for **Chercher Secondary School Grade 12** students across sections **12A through 12K**.

---

## 📌 Product Overview

- **Target Audience:** Exclusively Chercher Secondary School Grade 12 students in sections 12A–12K.
- **Academic Year Status:** Tentative Timetable, Academic Year 2019 E.C. (Official school verification required; do not call current unless verified).
- **Core Technology:** Kotlin, Jetpack Compose, Material Design 3, Kotlin Coroutines, StateFlow, Android PrintManager, SharedPreferences.
- **Aesthetic:** Polished Navy/Indigo/Blue UI (`#0B1120`, `#1E293B`, `#2563EB`, `#38BDF8`), adaptive layout (phones & tablets), light/dark/system theme.
- **Bilingual Interface:** English and Afaan Oromoo interface translations. Teacher names and source codes are preserved exactly as in official records.

---

## 🗄️ Read-Only & Storage Separation Policy

- **Immutable Official Data:** Official timetable records and teacher assignments are immutable bundled static data. No student controls are provided to add, edit, delete, or modify official records.
- **Student Preferences:** Stored locally on the user's device under key `chercher_grade12_preferences_v1`.
- **Tamper Protection:** Client-side UI protection ensures local display preferences never mutate the distributed official schedule. Local preferences may be reset or cleared without affecting official data.

---

## ⚙️ Alternative Subject Resolution Rules

1. **Code `1/2` (Language Alternative):**
   - Option 1: **Afaan Oromoo** (Teacher: Tadele Olani, Code 1)
   - Option 2: **Amharic** (Teacher: Tenagne Tamiru, Code 2)
   - Only the student's assigned option is displayed based on their preference.
2. **Code `48(50)` (Vocational Alternative):**
   - Option 1: **Health** (Teacher: Ramatulahi*, Code 48)
   - Option 2: **Construction** (Teacher: Bakkalcha, Code 50)
   - Only the assigned option is displayed.
3. **Codes `49(51)` and `51(49)` (Unresolved Pairs):**
   - Does not infer meaning or auto-assign subjects.
   - Cells are prominently tagged with: *"Information requires school confirmation"*.
4. **Unknown Codes (e.g. Code `9`, Code `8`, Code `35`):**
   - Displayed as requiring official school confirmation. Never guessed.
5. **Dash `—`:**
   - Interpreted as an official Free Period.

---

## 📚 Bundled Sections & Matrix

The application bundles all 11 sections with complete 5 days × 6 periods matrices (330 periods total):
- **12A, 12B, 12C, 12D, 12E, 12F, 12G, 12H** (Natural Science stream)
- **12I, 12J, 12K** (Social Science stream)

---

## 📱 Features

- **First-Run Setup Wizard:** Prompts student for section (12A–12K), language (English / Afaan Oromoo), and confirmed personal elective choices.
- **Today's Classes:** Timeline view of all 6 periods for the active day with period badges, teacher info, and elective tags.
- **Weekly Schedule Matrix:** Interactive 5-day × 6-period grid with color-coded subjects; tap any cell to inspect full teacher, code, and status details.
- **Teacher Directory:** Full catalog of all 24 teachers with codes, subjects, and assigned Grade 12 sections. Includes filter by subject and reverse schedule lookup.
- **Global & Scoped Search:** Search by subject, teacher name, teacher code, weekday, or period (scoped to selected section or all Grade 12).
- **Print & Export Schedule:** Formats the weekly timetable into an elegant, high-contrast document ready for physical printing or PDF export via Android PrintManager.
- **Built-in Validation Utility:** Runs integrity checks over all 11 sections, 330 periods, and teacher assignments.

---

## 🚀 Building & Running

```bash
# Compile and build the debug APK
gradle assembleDebug

# Run unit and Robolectric tests
gradle :app:testDebugUnitTest
```
