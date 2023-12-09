package kubiakdev.com.app.authentication.sign.up

import kubiakdev.com.data.database.dao.UserDao
import kubiakdev.com.data.database.model.user.UserEntity

class CreateUserUseCase(
    private val dao: UserDao,
) {

    suspend fun createUserInDatabase(
        authId: String,
        email: String,
        publicKey: String,
    ) {
        dao.addUser(
            UserEntity(
                authUid = authId,
                email = email,
                publicKey = publicKey,
            )
        )
    }
}
