package arcana.resource

import arcana.api.Resource
import arcana.util.asInputStream
import java.io.InputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile

class JarResource(
    override val path: String,
    private val jarFile: JarFile,
    private val jarEntry: JarEntry
) : Resource {

    override val contentStream: InputStream get() {
        return try {
            jarFile.getInputStream(jarEntry)
        } catch (e: Exception) {
            e.printStackTrace()
            "".asInputStream()
        }
    }
}
