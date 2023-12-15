package kubiakdev.com.app.friends

import kubiakdev.com.data.database.dao.UserDao
import kubiakdev.com.domain.model.friend.FriendWithUserData

class FindFriendUseCase(
    private val dao: UserDao,
) {

    suspend fun findUser(email: String): FriendWithUserData? =
        dao.getByEmail(email)?.let { user ->
            FriendWithUserData(
                userId = user.authUid,
                username = "",
                email = user.email,
                publicKey = user.publicKey
            )
        }
}
