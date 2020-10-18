package dev.qrivi.fapp.server.common

import javax.persistence.*

@MappedSuperclass
open class Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "id_seq", allocationSize = 50)
    @Column(name = "id")
    val id: Long = 0
}
