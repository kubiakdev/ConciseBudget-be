package kubiakdev.com.domain.util.environment

enum class Environment(private val value: String) {
    Prod("prod"), Test("test");

    companion object {
        fun getByName(value: String?): Environment? = Environment.entries.find { it.value == value }
    }
}