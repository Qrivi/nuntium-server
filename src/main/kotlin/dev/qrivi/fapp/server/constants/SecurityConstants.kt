package dev.qrivi.fapp.server.constants

object SecurityConstants {
    const val TOKEN_HEADER = "Authorization"

    const val TOKEN_TYPE = "JWT"
    const val TOKEN_PREFIX = "Bearer "
    const val TOKEN_ISSUER = "fappserver"
    const val TOKEN_AUDIENCE = "fappclient"
    const val TOKEN_TTL = 24L // 24 hours
    const val REFRESH_TTL = 720L // 720 hours, aka 30 days

    const val JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y\$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf" // temp early dev only

    val PASSWORD_BLACKLIST = listOf(
            "password", "pass1234", "12345678", "01234567", "baseball", "trustno1", "superman", "testtest", "computer",
            "michelle", "123456789", "0123456789", "012345678", "1234567890", "corvette", "00000000"
    )
}
