package arcana

actual class ArcanaPlatform {
    actual val platformName: String = "JVM"
}

fun ArcanaPlatform.bootstrapJvm(): Library {
    return bootstrapCommon()
}
