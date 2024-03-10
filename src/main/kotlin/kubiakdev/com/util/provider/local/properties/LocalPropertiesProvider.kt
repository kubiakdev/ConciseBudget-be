package kubiakdev.com.util.provider.local.properties

import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

fun getLocalProperty(key: String): String {
    val properties = Properties()
    val localProperties = File("local.properties")
    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else {
        error("File from not found")
    }

    return properties.getProperty(key)
}