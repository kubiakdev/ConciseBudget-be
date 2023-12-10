package kubiakdev.com.app.friends

import kubiakdev.com.data.database.dao.FriendsDao
import kubiakdev.com.data.database.model.friend.FriendsEntity

class CreateFriendsUseCase(
    private val dao: FriendsDao,
) {

    suspend fun createFriendsList(userId: String): String? =
        dao.create(FriendsEntity(ownerId = userId, friends = emptyList()))?.toHexString()
}