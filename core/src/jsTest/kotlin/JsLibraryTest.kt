package arcana

import kotlin.test.Test
import kotlin.test.assertEquals

class JsLibraryTest {
    @Test
    fun testSomeLibraryMethod() {
        assertEquals("JS", ArcanaPlatform().platformName)
    }
}
