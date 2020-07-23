package arcana.resource

import arcana.api.Resource
import arcana.util.asInputStream
import java.io.InputStream

class StringResource(
    override val path: String,
    private val hardcodedString: String
) : Resource {
    override val contentStream: InputStream get() = hardcodedString.asInputStream()
}
