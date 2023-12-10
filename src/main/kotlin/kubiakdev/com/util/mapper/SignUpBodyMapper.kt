package kubiakdev.com.util.mapper

import kubiakdev.com.app.authentication.sign.up.SignUpBodyRouteModel
import kubiakdev.com.domain.route.model.sign.up.SignUpBody

fun SignUpBodyRouteModel.toDomainModel() = SignUpBody(
    email = email!!,
    password = password!!,
    publicKey = publicKey!!,
)
