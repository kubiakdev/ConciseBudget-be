package kubiakdev.com.di

import com.google.firebase.auth.FirebaseAuth
import kubiakdev.com.app.authorization.firebase.FirebaseAppInitializer
import kubiakdev.com.app.authorization.sign.`in`.SignInUserUseCaseImpl
import kubiakdev.com.app.authorization.sign.up.SignUpUserUseCaseImpl
import kubiakdev.com.domain.authorization.sign.`in`.SignInUserUseCase
import kubiakdev.com.domain.authorization.sign.up.SignUpUserUseCase
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAppInitializer() }
    single<SignUpUserUseCase> { SignUpUserUseCaseImpl() }
    single<SignInUserUseCase> { SignInUserUseCaseImpl() }
    single<FirebaseAuth> { FirebaseAuth.getInstance() }
}