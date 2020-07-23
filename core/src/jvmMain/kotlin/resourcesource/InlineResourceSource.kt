package arcana.resourcesource

import arcana.api.Resource
import arcana.api.ResourceSource
import arcana.resource.InlineResource
import arcana.resource.StringResource
import java.util.regex.Pattern

class InlineResourceSource : ResourceSource {

    override fun getResourceEntry(fileName: String): Resource? {
        val m = inlineFilenamePattern.matcher(fileName)

        if (m.find()) {
            val actualFileName = m.group(2)
            val fileContent = m.group(3)
            return InlineResource(
                StringResource(actualFileName, fileContent)
            )
        }

        return null
    }

    override fun getResourceEntries(dirName: String, fileExtensions: Array<String>?, recursive: Boolean): List<Resource> {
        return emptyList()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    private val _hashCode: Int by lazy {
        javaClass.hashCode()
    }
    override fun hashCode(): Int {
        return _hashCode
    }

    companion object {
        private val inlineFilenamePattern = Pattern.compile("^(inline:(.*?):)(.*)", Pattern.DOTALL)
    }
}


