package kubiakdev.com.di

import kubiakdev.com.app.authorization.sign.`in`.SignInUserUseCaseImpl
import kubiakdev.com.app.authorization.sign.up.SignUpUserUseCaseImpl
import kubiakdev.com.domain.authorization.sign.`in`.SignInUserUseCase
import kubiakdev.com.domain.authorization.sign.up.SignUpUserUseCase
import org.koin.dsl.module

val appModule = module {
    single<SignUpUserUseCase> { SignUpUserUseCaseImpl() }
    single<SignInUserUseCase> { SignInUserUseCaseImpl() }
}