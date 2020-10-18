package dev.qrivi.fapp.server.controller

import dev.qrivi.fapp.server.persistence.entity.User
import dev.qrivi.fapp.server.persistence.entity.UserStatus
import dev.qrivi.fapp.server.persistence.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/dummy")
class DummyController(private val userRepository: UserRepository) {

    @GetMapping("/hello")
    fun hello(@RequestParam(value = "name", defaultValue = "World") name: String): String {
        return "Hello $name"
    }

    @GetMapping("/setup")
    fun setup(): String {
        val kristof = User(
            email = "hello@kristofdewil.de",
            name = "Kristof Dewilde",
            password = "hashed",
            status = UserStatus.JOINED,
            subscriptions = emptySet(),
            readItems = emptySet()
        )
        val notKristof = User(
            email = "bye@kristofdewil.de",
            name = "Not Kristof Dewilde",
            password = "hashed",
            status = UserStatus.JOINED,
            subscriptions = emptySet(),
            readItems = emptySet()
        )

        userRepository.save(kristof)
        userRepository.save(notKristof)

        return "Ok"
    }

    // gets all users
    @GetMapping("/users")
    fun getAllUsers(): List<User> = userRepository.findAll()

    // creates a user
    @PostMapping("/users")
    fun createUser(@Valid @RequestBody user: User): User = userRepository.save(user)

    // gets a single user
    @GetMapping("users/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> =
        userRepository.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())

    // updates a user
    @PutMapping("/users/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody updatedUser: User):
        ResponseEntity<User> =
            userRepository.findById(id).map {
                val newUser = it.copy(name = updatedUser.name, email = updatedUser.email)
                ResponseEntity.ok().body(userRepository.save(newUser))
            }.orElse(ResponseEntity.notFound().build())

    // deletes a user
    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> =
        userRepository.findById(id).map {
            userRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
}
