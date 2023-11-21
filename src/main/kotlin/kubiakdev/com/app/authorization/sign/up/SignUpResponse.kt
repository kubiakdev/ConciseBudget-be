package kubiakdev.com.app.authorization.sign.up

data class SignUpResponse(
    val id: String,
    val token: String,
    val email: String,
)
