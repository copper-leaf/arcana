package arcana

import kotlin.test.Test
import kotlin.test.assertEquals

class AndroidLibraryTest {
    @Test
    fun testSomeLibraryMethod() {
        assertEquals("JVM", ArcanaPlatform().platformName)
    }
}
