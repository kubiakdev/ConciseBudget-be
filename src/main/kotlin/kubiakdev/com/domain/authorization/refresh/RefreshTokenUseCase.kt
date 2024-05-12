package kubiakdev.com.domain.authorization.refresh

import kubiakdev.com.app.authentication.refresh.RefreshTokenResponse
import kubiakdev.com.util.Response

interface RefreshTokenUseCase {

    suspend fun refreshToken(refreshToken: String): Response<RefreshTokenResponse>
}
