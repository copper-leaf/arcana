package arcana.api

import java.io.InputStream

interface Resource {

    val path: String
    val contentStream: InputStream

    fun canUpdate(): Boolean {
        return false
    }

    fun canDelete(): Boolean {
        return false
    }

    fun update(newContent: InputStream) {
    }

    fun delete() {
    }
}
