package com.example.demo.widget.service

import java.time.LocalDateTime

fun atStartOfDay(): LocalDateTime = LocalDateTime.now().withHour(0).withMinute(0).withHour(0).withSecond(0).withNano(0)
