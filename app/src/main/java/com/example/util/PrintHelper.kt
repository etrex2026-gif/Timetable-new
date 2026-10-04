package com.example.util

import android.content.Context
import android.content.Intent
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.data.TimetableData
import com.example.data.model.StudentPreferences
import com.example.data.model.WeekDay

object PrintHelper {

    fun printSectionSchedule(context: Context, section: String, preferences: StudentPreferences) {
        val lang = preferences.language
        val schedule = TimetableData.rawTimetable[section] ?: return

        val html = buildHtmlSchedule(section, schedule, preferences, lang)

        val webView = WebView(context)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager
                val printAdapter = webView.createPrintDocumentAdapter("Chercher_Timetable_$section")
                val printAttributes = PrintAttributes.Builder()
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4.asLandscape())
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                    .build()
                printManager?.print(
                    "Chercher_Grade12_${section}_Schedule",
                    printAdapter,
                    printAttributes
                )
            }
        }
        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }

    fun shareSectionSchedule(context: Context, section: String, preferences: StudentPreferences) {
        val schedule = TimetableData.rawTimetable[section] ?: return
        val sb = StringBuilder()
        sb.append("🏫 CHERCHER SECONDARY SCHOOL\n")
        sb.append("GRADE 12 SMART TIMETABLE — SECTION $section\n")
        sb.append("Status: Tentative (Academic Year 2019 E.C.)\n\n")

        for (day in WeekDay.values()) {
            sb.append("📅 ${day.fullNameEn.uppercase()}:\n")
            val periods = schedule[day] ?: emptyList()
            periods.forEachIndexed { index, code ->
                val resolved = TimetableData.resolvePeriod(section, day, index + 1, code, preferences)
                val status = if (resolved.isFree) "Free Period" else "${resolved.subjectFullName} (${resolved.teacherName})"
                sb.append("  P${index + 1}: $status [Code: $code]\n")
            }
            sb.append("\n")
        }

        sb.append("Generated with Chercher Grade 12 Smart Timetable App")

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Chercher Grade 12 Section $section Schedule")
            putExtra(Intent.EXTRA_TEXT, sb.toString())
        }
        context.startActivity(Intent.createChooser(intent, "Share Timetable"))
    }

    private fun buildHtmlSchedule(
        section: String,
        schedule: Map<WeekDay, List<String>>,
        preferences: StudentPreferences,
        lang: String
    ): String {
        val title = Localization.get("app_title", lang)
        val statusText = Localization.get("academic_status", lang)

        val daysHeader = WeekDay.values().joinToString("") { day ->
            val dayName = if (lang == "om") day.fullNameOm else day.fullNameEn
            "<th style='padding: 10px; border: 1px solid #1E293B; background: #0F172A; color: white;'>$dayName</th>"
        }

        val rowsBuilder = StringBuilder()
        for (periodIdx in 1..6) {
            rowsBuilder.append("<tr>")
            rowsBuilder.append("<td style='padding: 10px; font-weight: bold; background: #F1F5F9; border: 1px solid #CBD5E1; text-align: center;'>P$periodIdx</td>")
            for (day in WeekDay.values()) {
                val code = schedule[day]?.getOrNull(periodIdx - 1) ?: "—"
                val resolved = TimetableData.resolvePeriod(section, day, periodIdx, code, preferences)
                val bg = if (resolved.isFree) "#F8FAFC" else "#EFF6FF"
                val textColor = if (resolved.requiresSchoolConfirmation) "#B45309" else "#1E293B"
                rowsBuilder.append("<td style='padding: 8px; border: 1px solid #CBD5E1; background: $bg;'>")
                rowsBuilder.append("<div style='font-weight: bold; color: $textColor;'>${resolved.subjectAbbr}</div>")
                rowsBuilder.append("<div style='font-size: 11px; color: #475569;'>${resolved.teacherName}</div>")
                rowsBuilder.append("<div style='font-size: 10px; color: #94A3B8;'>Code: $code</div>")
                rowsBuilder.append("</td>")
            }
            rowsBuilder.append("</tr>")
        }

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="utf-8">
                <title>$title - Section $section</title>
                <style>
                    body { font-family: system-ui, -apple-system, sans-serif; margin: 20px; color: #0F172A; }
                    .header { text-align: center; margin-bottom: 20px; border-bottom: 2px solid #2563EB; padding-bottom: 12px; }
                    .header h1 { margin: 0; color: #0F172A; font-size: 24px; }
                    .header h2 { margin: 4px 0; color: #2563EB; font-size: 18px; }
                    .header p { margin: 2px 0; color: #64748B; font-size: 12px; }
                    table { width: 100%; border-collapse: collapse; font-size: 12px; }
                    .footer { margin-top: 20px; font-size: 10px; color: #64748B; text-align: center; }
                    @media print {
                        body { margin: 0; }
                    }
                </style>
            </head>
            <body>
                <div class="header">
                    <h1>Chercher Secondary School</h1>
                    <h2>Grade 12 Timetable — Section $section</h2>
                    <p>$statusText • Verified Immutable School Dataset</p>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th style='padding: 10px; border: 1px solid #1E293B; background: #0F172A; color: white;'>Period</th>
                            $daysHeader
                        </tr>
                    </thead>
                    <tbody>
                        ${rowsBuilder}
                    </tbody>
                </table>
                <div class="footer">
                    Official Grade 12 Timetable • Generated by Chercher Grade 12 Smart Timetable
                </div>
            </body>
            </html>
        """.trimIndent()
    }
}
