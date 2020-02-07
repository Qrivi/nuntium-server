package dev.qrivi.fapp.server.constants

object SecurityConstants {
    const val AUTH_LOGIN_URL = "/authenticate"

    const val JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y\$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf"
    const val PASSWORD_SALT = "1234hoedjevanpapier"

    const val TOKEN_HEADER = "Authorization"
    const val TOKEN_PREFIX = "Bearer "
    const val TOKEN_TYPE = "JWT"
    const val TOKEN_TTL = 24L // 24 hours
    const val TOKEN_ISSUER = "fappserver"
    const val TOKEN_AUDIENCE = "fappclient"

    const val REFRESH_TTL = 720L // 720 hours, aka 30 days
}
