package com.example.demo.widget.model

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class WidgetDTO(
    var id: Long?,
    @NotNull
    var x: Int?,
    @NotNull
    var y: Int?,
    var zIndex: Int?,
    @NotNull
    var width: Int?,
    @NotNull
    var height: Int?,
    var dateLastUpdate: LocalDateTime?
)
