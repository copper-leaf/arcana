package arcana

import kotlin.test.Test
import kotlin.test.assertEquals

class JvmLibraryTest {
    @Test
    fun testSomeLibraryMethod() {
        assertEquals("JVM", ArcanaPlatform().platformName)
    }
}
