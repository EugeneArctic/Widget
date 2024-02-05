package com.example.demo.widget.exception

import java.time.LocalDateTime

data class ApiError(
     var errorCode: Int,
     var description: String,
     val date: LocalDateTime = LocalDateTime.now()
)