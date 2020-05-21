package arcana

class Library {
    fun someLibraryMethod(): Boolean {
        return true
    }
}

expect class ArcanaPlatform {
    val platformName: String
}

fun ArcanaPlatform.bootstrapCommon(): Library {
    return Library()
}
