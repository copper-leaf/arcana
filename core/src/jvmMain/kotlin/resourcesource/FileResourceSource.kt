package arcana.resourcesource

import arcana.api.Resource
import arcana.api.ResourceSource
import arcana.resource.FileResource
import arcana.util.normalize
import java.io.File
import java.nio.file.Path

class FileResourceSource(
    private val baseDirectory: Path,
    private val ignoredFilenames: Set<String>
) : ResourceSource {

// Implementation
// ---------------------------------------------------------------------------------------------------------------------

    override fun getResourceEntry(fileName: String): Resource? {
        return baseDirectory
            .resolve(fileName.normalize()).toFile()
            .takeIf { it.exists() && !it.isDirectory }
            ?.asResource()
    }

    override fun getResourceEntries(
        dirName: String,
        fileExtensions: Set<String>,
        recursive: Boolean
    ): Sequence<Resource> {
        return fileSequenceAt(dirName, recursive)
            .filterNotNull()
            .filter { it.isMatchingExtension(fileExtensions) }
            .filterNot { it.isIgnored() }
            .map { it.asResource() }
    }

// Helpers
// ---------------------------------------------------------------------------------------------------------------------

    private fun fileSequenceAt(dirName: String, recursive: Boolean): Sequence<File?> {
        val resolvedTargetDir = baseDirectory.resolve(dirName.normalize()).toFile()

        val fileSequence: Sequence<File?>? =  if (resolvedTargetDir.exists() && resolvedTargetDir.isDirectory) {
            if(recursive) {
                resolvedTargetDir.walkTopDown()
            }
            else {
                resolvedTargetDir.listFiles()?.asSequence()
            }
        } else {
            emptySequence()
        }

        return fileSequence ?: emptySequence()
    }

    private fun File.isMatchingExtension(fileExtensions: Set<String>): Boolean {
        return if(fileExtensions.isNotEmpty())
            extension in fileExtensions
        else
            false
    }

    private fun File.isIgnored(): Boolean {
        return if(ignoredFilenames.isNotEmpty())
            name in ignoredFilenames
        else
            false
    }

    private fun File.asResource(): Resource {
        return FileResource(this, baseDirectory.toFile())
    }

}
