package dev.qrivi.fapp.server.controller

import dev.qrivi.fapp.server.persistence.entity.Account
import dev.qrivi.fapp.server.persistence.entity.AccountStatus
import dev.qrivi.fapp.server.persistence.repository.AccountRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/dummy")
class DummyController(private val accountRepository: AccountRepository) {

    @GetMapping("/hello")
    fun hello(@RequestParam(value = "name", defaultValue = "World") name: String): String {
        return "Hello $name"
    }

    @GetMapping("/setup")
    fun setup(): String {
        val kristof = Account(
            uuid = UUID.randomUUID().toString(),
            email = "hello@kristofdewil.de",
            name = "Kristof Dewilde",
            password = "hashed",
            status = AccountStatus.JOINED,
            subscriptions = mutableSetOf(),
            readItems = mutableSetOf()
        )
        val notKristof = Account(
            uuid = UUID.randomUUID().toString(),
            email = "bye@kristofdewil.de",
            name = "Not Kristof Dewilde",
            password = "hashed",
            status = AccountStatus.JOINED,
            subscriptions = mutableSetOf(),
            readItems = mutableSetOf()
        )

        accountRepository.save(kristof)
        accountRepository.save(notKristof)

        return "Ok"
    }

    // gets all users
    @GetMapping("/users")
    fun getAllUsers(): List<Account> = accountRepository.findAll()

//    // creates a user
//    @PostMapping("/users")
//    fun createUser(@Valid @RequestBody user: User): User = userRepository.save(user)
//
//    // gets a single user
//    @GetMapping("users/{id}")
//    fun getUserById(@PathVariable id: Long): ResponseEntity<User> =
//        userRepository.findById(id).map {
//            ResponseEntity.ok(it)
//        }.orElse(ResponseEntity.notFound().build())
//
//    // updates a user
//    @PutMapping("/users/{id}")
//    fun updateUser(@PathVariable id: Long, @Valid @RequestBody updatedUser: User):
//        ResponseEntity<User> =
//            userRepository.findById(id).map {
//                val newUser = it.copy(name = updatedUser.name, email = updatedUser.email)
//                ResponseEntity.ok().body(userRepository.save(newUser))
//            }.orElse(ResponseEntity.notFound().build())
//
//    // deletes a user
//    @DeleteMapping("/users/{id}")
//    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> =
//        userRepository.findById(id).map {
//            userRepository.delete(it)
//            ResponseEntity<Void>(HttpStatus.OK)
//        }.orElse(ResponseEntity.notFound().build())
}
