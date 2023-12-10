package kubiakdev.com.domain.authorization.sign.up

import kubiakdev.com.app.authentication.sign.up.SignUpResponse
import kubiakdev.com.util.Response

interface SignUpUserUseCase {

    suspend fun signUpUser(email: String, password: String, publicKey: String): Response<SignUpResponse>
}
