package arcana.resource

import arcana.api.Resource
import arcana.util.asInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Files

class FileResource(
    private val file: File,
    private val baseDirectory: File = file.parentFile
) : Resource {

    override val path: String by lazy {
        pathFromFile(
            file,
            baseDirectory
        )
    }

    override val contentStream: InputStream get() {
        return try {
            FileInputStream(file)
        } catch (e: Exception) {
            e.printStackTrace()
            "".asInputStream()
        }
    }

    override fun canUpdate(): Boolean {
        return true
    }
    override fun update(newContent: InputStream) {
        Files.write(file.toPath(), newContent.readBytes())
    }

    override fun canDelete(): Boolean {
        return true
    }
    override fun delete() {
        file.delete()
    }

    companion object {
        private fun pathFromFile(file: File, baseFile: File): String {
            return pathFromFile(
                file,
                baseFile.absolutePath
            )
        }

        private fun pathFromFile(
            file: File,
            basePath: String
        ): String {
            var baseFilePath = basePath
            var filePath = file.path
            // normalise Windows-style backslashes to common forward slashes
            baseFilePath = baseFilePath.replace("\\\\".toRegex(), "/")
            filePath = filePath.replace("\\\\".toRegex(), "/")
            // Remove the common base path from the actual file path
            if (filePath.startsWith(baseFilePath)) {
                filePath = filePath.replace(baseFilePath.toRegex(), "")
            }
            if (filePath.startsWith("/")) {
                filePath = filePath.removePrefix("/")
            }
            // if the path is not a child of the base path (i.e. still has relative path segments), strip those away. The
            // resolved "path" of this resource will be the portion after those relative segments.
            filePath = filePath.split("/".toRegex()).toTypedArray()
                .filter { it: String -> !(it == ".." || it == ".") }
                .joinToString("/", "", "", -1, "", null)
            return filePath
        }
    }

}
