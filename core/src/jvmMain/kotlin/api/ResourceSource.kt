package arcana.api

interface ResourceSource {

    fun getResourceEntry(fileName: String): Resource?

    fun getResourceEntries(dirName: String, fileExtensions: Set<String>, recursive: Boolean): Sequence<Resource>

}
