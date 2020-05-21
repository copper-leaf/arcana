package arcana

actual class ArcanaPlatform {
    actual val platformName: String = "JS"
}

fun ArcanaPlatform.bootstrapJs(): Library {
    return bootstrapCommon()
}
