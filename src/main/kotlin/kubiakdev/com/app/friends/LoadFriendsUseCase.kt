package kubiakdev.com.app.friends

import kubiakdev.com.data.database.dao.FriendsDao
import kubiakdev.com.domain.model.friend.FriendWithUserData
import kubiakdev.com.domain.model.friend.FriendsWithUserData

class LoadFriendsUseCase(
    private val dao: FriendsDao,
    private val createFriendsUseCase: CreateFriendsUseCase,
    private val loadUserUseCase: LoadUserUseCase,
) {

    suspend fun loadFriends(userAuthId: String): FriendsWithUserData? {
        var friends = dao.loadAll(userAuthId)
        if (friends == null) {
            createFriendsUseCase.createFriendsList(userAuthId)
            friends = dao.loadAll(userAuthId)
        }

        val friendsWithUserData = friends?.friends?.map { friend ->
            val user = loadUserUseCase.loadUser(userAuthId)!!
            FriendWithUserData(
                userId = userAuthId,
                username = friend.username,
                email = user.email,
                publicKey = user.publicKey,
            )
        }

        return friends?.let {
            FriendsWithUserData(
                id = friends.id!!.toHexString(),
                ownerId = friends.ownerId,
                friends = friendsWithUserData!!,
            )
        }
    }
}
