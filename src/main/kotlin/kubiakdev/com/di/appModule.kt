package kubiakdev.com.di

import com.google.firebase.auth.FirebaseAuth
import kubiakdev.com.app.authentication.firebase.FirebaseAppInitializer
import kubiakdev.com.app.authentication.sign.`in`.SignInUserUseCaseImpl
import kubiakdev.com.app.authentication.sign.up.CreateUserUseCase
import kubiakdev.com.app.authentication.sign.up.SignUpUserUseCaseImpl
import kubiakdev.com.data.database.dao.FriendsDao
import kubiakdev.com.data.database.dao.TransactionDao
import kubiakdev.com.data.database.dao.UserDao
import kubiakdev.com.domain.authorization.sign.`in`.SignInUserUseCase
import kubiakdev.com.domain.authorization.sign.up.SignUpUserUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAppInitializer() }
    single<SignUpUserUseCase> { SignUpUserUseCaseImpl() }
    single<SignInUserUseCase> { SignInUserUseCaseImpl() }
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
    singleOf(::CreateUserUseCase)

    singleOf(::UserDao)
    singleOf(::FriendsDao)
    singleOf(::TransactionDao)
}