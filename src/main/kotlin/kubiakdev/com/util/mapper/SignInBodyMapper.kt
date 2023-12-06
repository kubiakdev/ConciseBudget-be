package kubiakdev.com.util.mapper

import kubiakdev.com.app.authentication.sign.`in`.SignInBodyRouteModel
import kubiakdev.com.domain.route.model.sign.`in`.SignInBody

fun SignInBodyRouteModel.toDomainModel() = SignInBody(email = email!!, password = password!!)
