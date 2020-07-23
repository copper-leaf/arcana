package arcana.util

import arcana.api.Resource
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset

fun InputStream?.readToString(charset: Charset = Charsets.UTF_8): String = this?.bufferedReader(charset)?.use { it.readText() } ?: ""
fun String?.asInputStream(charset: Charset = Charsets.UTF_8): InputStream = ByteArrayInputStream((this ?: "").toByteArray(charset))

val Resource.content: String get() = contentStream.readToString()

// constants
//----------------------------------------------------------------------------------------------------------------------
var isWindows = File.separator == "\\"

fun String?.normalize(): String {
    var normalizedPath = this
    if (normalizedPath != null) {
        if (isWindows) {
            normalizedPath = normalizedPath.replace("\\\\".toRegex(), "/")
        }
        normalizedPath = normalizedPath.trim().removePrefix("/")
    }
    return normalizedPath ?: ""
}
