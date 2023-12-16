package kubiakdev.com.app.friends

import kubiakdev.com.data.database.dao.FriendsDao
import kubiakdev.com.data.database.model.friend.toEntityModel
import kubiakdev.com.domain.model.friend.Friend
import kubiakdev.com.domain.model.friend.toFriendsModel

class AddFriendUseCase(
    private val dao: FriendsDao,
    private val loadFriendsUseCase: LoadFriendsUseCase,
) {

    suspend fun addFriend(userAuthId: String, friend: Friend) {
        val friends = loadFriendsUseCase.loadFriends(userAuthId = userAuthId)!!.toFriendsModel()
        val modifiedFriends = friends.copy(friends = friends.friends.toMutableList().apply { add(friend) })
        dao.update(modifiedFriends.toEntityModel())
    }
}