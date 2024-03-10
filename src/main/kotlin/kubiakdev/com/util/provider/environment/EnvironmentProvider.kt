package kubiakdev.com.util.provider.environment

import kubiakdev.com.domain.util.environment.Environment

val environment = Environment.getByName(System.getenv("environment")) ?: Environment.Test