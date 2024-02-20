package com.example.demo.widget.model

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime


 open class Widget(
     open var id: Long,
     @NotNull
     open var x: Int,
     @NotNull
     open var y: Int,
     open var zIndex: Int,
     @NotNull
     open var width: Int,
     @NotNull
     open var height: Int,
     open var dateLastUpdate: LocalDateTime
)

