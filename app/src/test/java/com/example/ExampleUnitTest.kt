package com.example

import com.example.data.TimetableData
import com.example.data.model.StudentPreferences
import com.example.data.model.WeekDay
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun validateDataset_structureAndCounts() {
    val validation = TimetableData.validateDataset()
    assertTrue("Dataset must be 100% structurally valid", validation.isValid)
    assertEquals("Must have 11 sections (12A-12K)", 11, validation.totalSections)
    assertEquals("Must have exactly 330 periods (11 x 5 x 6)", 330, validation.totalPeriods)
  }

  @Test
  fun validateTeacherDirectory() {
    val teachers = TimetableData.teachers
    assertEquals("Should have all 26 registered teachers", 26, teachers.size)

    // Check specific teachers
    val t9 = teachers["9"]
    assertNotNull(t9)
    assertEquals("Million Abebe", t9?.name)
    assertEquals("Eng", t9?.subjectAbbr)

    val t1 = teachers["1"]
    assertNotNull(t1)
    assertEquals("Tadele Olani", t1?.name)
    assertEquals("A/O", t1?.subjectAbbr)

    val t2 = teachers["2"]
    assertNotNull(t2)
    assertEquals("Tenagne Tamiru", t2?.name)
    assertEquals("Amh", t2?.subjectAbbr)

    val t13 = teachers["13"]
    assertNotNull(t13)
    assertEquals("Kasahun Negash", t13?.name)
    assertEquals("Math", t13?.subjectAbbr)

    val t48 = teachers["48"]
    assertNotNull(t48)
    assertEquals("Ramatulahi*", t48?.name)
    assertTrue("Code 48 must be marked unconfirmed", t48?.isUnconfirmed == true)

    val t51 = teachers["51"]
    assertNotNull(t51)
    assertEquals("Ukasha Kemal Eeko*", t51?.name)
    assertTrue("Code 51 must be marked unconfirmed", t51?.isUnconfirmed == true)
  }

  @Test
  fun validateAlternativeHandling_1_2() {
    // With preference 1 (Afaan Oromoo)
    val prefs1 = StudentPreferences(elective12 = "1")
    val res1 = TimetableData.resolvePeriod("12A", WeekDay.THURSDAY, 5, "1/2", prefs1)
    assertEquals("A/O", res1.subjectAbbr)
    assertEquals("Tadele Olani", res1.teacherName)
    assertEquals("1", res1.teacherCode)
    assertTrue(res1.isAlternative)

    // With preference 2 (Amharic)
    val prefs2 = StudentPreferences(elective12 = "2")
    val res2 = TimetableData.resolvePeriod("12A", WeekDay.THURSDAY, 5, "1/2", prefs2)
    assertEquals("Amh", res2.subjectAbbr)
    assertEquals("Tenagne Tamiru", res2.teacherName)
    assertEquals("2", res2.teacherCode)
    assertTrue(res2.isAlternative)
  }

  @Test
  fun validateAlternativeHandling_48_50() {
    // With preference 48 (Health)
    val prefs48 = StudentPreferences(elective4850 = "48")
    val res48 = TimetableData.resolvePeriod("12A", WeekDay.MONDAY, 6, "48(50)", prefs48)
    assertEquals("Health", res48.subjectAbbr)
    assertEquals("Ramatulahi*", res48.teacherName)
    assertEquals("48", res48.teacherCode)

    // With preference 50 (Construction)
    val prefs50 = StudentPreferences(elective4850 = "50")
    val res50 = TimetableData.resolvePeriod("12A", WeekDay.MONDAY, 6, "48(50)", prefs50)
    assertEquals("Constru", res50.subjectAbbr)
    assertEquals("Bakkalcha", res50.teacherName)
    assertEquals("50", res50.teacherCode)
  }

  @Test
  fun validateAlternativeHandling_49_51_and_51_49() {
    // With preference 49 (Journalism) for 49(51) cell
    val prefs49 = StudentPreferences(elective4951 = "49")
    val res49 = TimetableData.resolvePeriod("12I", WeekDay.THURSDAY, 6, "49(51)", prefs49)
    assertEquals("Journ", res49.subjectAbbr)
    assertEquals("Giduma Tariku", res49.teacherName)
    assertEquals("49", res49.teacherCode)
    assertTrue(res49.isAlternative)

    // With preference 51 (Accounting) for 49(51) cell
    val prefs51 = StudentPreferences(elective4951 = "51")
    val res51 = TimetableData.resolvePeriod("12I", WeekDay.THURSDAY, 6, "49(51)", prefs51)
    assertEquals("Account", res51.subjectAbbr)
    assertEquals("Ukasha Kemal Eeko*", res51.teacherName)
    assertEquals("51", res51.teacherCode)
    assertTrue(res51.isAlternative)

    // Test 51(49) cell with preference 49
    val res5149_with49 = TimetableData.resolvePeriod("12I", WeekDay.MONDAY, 6, "51(49)", prefs49)
    assertEquals("Journ", res5149_with49.subjectAbbr)
    assertEquals("Giduma Tariku", res5149_with49.teacherName)

    // Test 51(49) cell with preference 51
    val res5149_with51 = TimetableData.resolvePeriod("12I", WeekDay.MONDAY, 6, "51(49)", prefs51)
    assertEquals("Account", res5149_with51.subjectAbbr)
    assertEquals("Ukasha Kemal Eeko*", res5149_with51.teacherName)
  }

  @Test
  fun validateCode9_mapsToEnglishTeacherMillion() {
    val prefs = StudentPreferences()
    val res9 = TimetableData.resolvePeriod("12A", WeekDay.MONDAY, 2, "9", prefs)
    assertEquals("Eng", res9.subjectAbbr)
    assertEquals("Million Abebe", res9.teacherName)
    assertEquals("9", res9.teacherCode)
    assertEquals("English", res9.subjectFullName)
    assertFalse("Code 9 is verified English teacher Million", res9.requiresSchoolConfirmation)
  }

  @Test
  fun validateFreePeriod() {
    val prefs = StudentPreferences()
    val resFree = TimetableData.resolvePeriod("12A", WeekDay.FRIDAY, 6, "—", prefs)
    assertTrue("Empty dash period must be free", resFree.isFree)
    assertEquals("FREE", resFree.subjectAbbr)
  }
}
