package kubiakdev.com.app.friends

import kubiakdev.com.data.database.dao.FriendsDao
import kubiakdev.com.data.database.model.friend.toDomainModel
import kubiakdev.com.domain.model.friend.Friends

class LoadFriendsUseCase(
    private val dao: FriendsDao,
    private val createFriendsUseCase: CreateFriendsUseCase,
) {

    suspend fun loadFriends(userId: String): Friends? {
        val friends = dao.loadAll(userId)
        return if (friends == null) {
            createFriendsUseCase.createFriendsList(userId)
            dao.loadAll(userId)?.toDomainModel()
        } else {
            friends.toDomainModel()
        }
    }
}