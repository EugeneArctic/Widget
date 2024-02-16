package com.example.demo.widget.service

import com.example.demo.widget.model.Widget
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentSkipListSet

fun atStartOfDay(): LocalDateTime = LocalDateTime.now().withHour(0).withMinute(0).withHour(0).withSecond(0).withNano(0)
fun transformResult(correctWidgetResultAfterUpdate2: ConcurrentSkipListSet<Widget>) =
    correctWidgetResultAfterUpdate2.map { "ZIndex:${it.zIndex} [${it.id}]" }