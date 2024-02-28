package com.example.demo.widget.repository.sql

import com.example.demo.widget.model.Widget
import java.util.concurrent.ConcurrentSkipListSet

interface WidgetRepository {
    fun findById(id: Long):Widget?
    fun findAll(): ConcurrentSkipListSet<Widget>
    fun create(widget: Widget): Widget
    fun delete(id: Long): Widget?
    fun update(id:Long, widget: Widget): Widget?


}