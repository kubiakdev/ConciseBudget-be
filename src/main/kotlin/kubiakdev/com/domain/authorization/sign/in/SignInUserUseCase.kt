package kubiakdev.com.domain.authorization.sign.`in`

import kubiakdev.com.app.authorization.sign.`in`.SignInResponse
import kubiakdev.com.util.Response

interface SignInUserUseCase {
    suspend fun signInUser(email: String, password: String): Response<SignInResponse>
}
