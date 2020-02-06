package dev.qrivi.fapp.server.constants

object SecurityConstants {
    const val AUTH_LOGIN_URL = "/authenticate"

    const val JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y\$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf"

    const val TOKEN_HEADER = "Authorization"
    const val TOKEN_PREFIX = "Bearer "
    const val TOKEN_TYPE = "JWT"
    const val TOKEN_TTL = 864000000
    const val TOKEN_ISSUER = "fappserver"
    const val TOKEN_AUDIENCE = "fappclient"
}
