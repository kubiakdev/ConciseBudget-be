package kubiakdev.com.util.mapper

import kubiakdev.com.app.authorization.sign.up.SignUpBodyRouteModel
import kubiakdev.com.domain.route.model.sign.up.SignUpBody

fun SignUpBodyRouteModel.toDomainModel() = SignUpBody(email = email!!, password = password!!)
