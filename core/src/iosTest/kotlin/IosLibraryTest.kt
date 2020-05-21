package arcana

import kotlin.test.Test
import kotlin.test.assertEquals

class IosLibraryTest {
    @Test
    fun testSomeLibraryMethod() {
        assertEquals("iOS", ArcanaPlatform().platformName)
    }
}
