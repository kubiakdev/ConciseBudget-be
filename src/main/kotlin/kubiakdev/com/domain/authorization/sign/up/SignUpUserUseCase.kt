package kubiakdev.com.domain.authorization.sign.up

import kubiakdev.com.app.authorization.sign.up.SignUpResponse
import kubiakdev.com.util.Response

interface SignUpUserUseCase {

    suspend fun signUpUser(email: String, password: String): Response<SignUpResponse>
}
