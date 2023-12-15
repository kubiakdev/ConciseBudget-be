package kubiakdev.com.app.friends

import kubiakdev.com.data.database.dao.FriendsDao
import kubiakdev.com.data.database.model.friend.toEntityModel
import kubiakdev.com.domain.model.friend.Friend
import kubiakdev.com.domain.model.friend.toFriendsModel

class AddFriendUseCase(
    private val dao: FriendsDao,
    private val loadFriendsUseCase: LoadFriendsUseCase,
) {

    suspend fun addFriend(userId: String, friend: Friend) {
        val friends = loadFriendsUseCase.loadFriends(userId = userId).toFriendsModel()
        val modifiedFriends = friends.apply { this.friends.toMutableList().add(friend) }
        dao.update(modifiedFriends.toEntityModel())
    }
}