package dev.qrivi.fapp.server.persistence

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.SequenceGenerator

@MappedSuperclass
open class Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "id_seq", allocationSize = 50)
    @Column(name = "id")
    val id: Long = 0
}
