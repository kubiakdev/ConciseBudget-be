package kubiakdev.com.app.friends

import kubiakdev.com.data.database.dao.UserDao
import kubiakdev.com.domain.model.user.User
import kubiakdev.com.util.mapper.toDomainModel

class LoadUserUseCase(
    private val dao: UserDao,
) {

    suspend fun loadUser(userAuthId: String): User? = dao.getByAuthUid(userAuthId)?.toDomainModel()
}