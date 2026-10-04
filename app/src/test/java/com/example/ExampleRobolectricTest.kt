package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.PreferencesManager
import com.example.data.model.StudentPreferences
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Smart Timetable", appName)
  }

  @Test
  fun `preferences key and defaults are correct`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val manager = PreferencesManager(context)
    val prefs = manager.loadPreferences()
    assertEquals("12A", prefs.section)
    assertEquals("en", prefs.language)
    assertEquals("1", prefs.elective12)
    assertEquals("48", prefs.elective4850)
    assertEquals("49", prefs.elective4951)
  }

  @Test
  fun `save and reload permanent preferences across launches`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val manager = PreferencesManager(context)

    // Save student choices
    val custom = StudentPreferences(
      section = "12C",
      language = "om",
      elective12 = "2", // Amharic
      elective4850 = "50", // Construction
      elective4951 = "51", // Accounting
      themeMode = "dark",
      isFirstRunCompleted = true
    )
    manager.savePreferences(custom)

    // Simulate relaunching the app by reading afresh
    val reloaded = manager.loadPreferences()
    assertEquals("12C", reloaded.section)
    assertEquals("om", reloaded.language)
    assertEquals("2", reloaded.elective12)
    assertEquals("50", reloaded.elective4850)
    assertEquals("51", reloaded.elective4951)
    assertEquals("dark", reloaded.themeMode)
    assertTrue("Should be configured", reloaded.isFirstRunCompleted)
  }

  @Test
  fun `corrupted preferences return student to setup`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val sp = context.getSharedPreferences(PreferencesManager.PREFS_KEY, Context.MODE_PRIVATE)

    // Inject invalid section
    sp.edit()
      .putString("selected_section", "INVALID_SECTION_99")
      .putBoolean("first_run_completed", true)
      .commit()

    val manager = PreferencesManager(context)
    val reloaded = manager.loadPreferences()
    assertFalse("Invalid preferences must trigger setup screen", reloaded.isFirstRunCompleted)
  }
}
