package com.zippractice1.entity

import com.google.cloud.spring.data.datastore.core.mapping.Entity
import org.springframework.data.annotation.Id
import java.sql.Timestamp

@Entity
data class Photo (
    @Id
    var id: String? = null,
    var uri: String? = null,
    var label: String? = null,
    var ownerId: String? = null,
    var timestamp: com.google.cloud.Timestamp?
)