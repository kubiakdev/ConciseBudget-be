package kubiakdev.com.app.friends

import kubiakdev.com.data.database.dao.FriendsDao
import kubiakdev.com.data.database.model.friend.toEntityModel
import kubiakdev.com.domain.model.friend.Friend

class RemoveFriendUseCase(
    private val dao: FriendsDao,
    private val loadFriendsUseCase: LoadFriendsUseCase,
) {

    suspend fun removeFriend(userId: String, friend: Friend) {
        val friends = loadFriendsUseCase.loadFriends(userId = userId)!!
        val modifiedFriends = friends.apply { this.friends.toMutableList().remove(friend) }
        dao.update(modifiedFriends.toEntityModel())
    }
}
