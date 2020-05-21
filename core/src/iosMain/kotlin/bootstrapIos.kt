package arcana

actual class ArcanaPlatform {
    actual val platformName: String = "iOS"
}

fun ArcanaPlatform.bootstrapiOS(): Library {
    return bootstrapCommon()
}
