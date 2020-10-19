package dev.qrivi.fapp.server.persistence

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class Identifiable {

    @GenericGenerator(
        name = "id_generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = [
            Parameter(name = "sequence_name", value = "id_sequence"),
            Parameter(name = "initial_value", value = "1000"),
            Parameter(name = "increment_size", value = "1")
        ]
    )
    @GeneratedValue(generator = "id_generator")
    @Id
    @Column(name = "id")
    open var id: Long = 0
}
