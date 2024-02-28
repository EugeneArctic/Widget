package com.example.demo.widget.repository

import com.example.demo.widget.model.WidgetInterface
import java.util.concurrent.ConcurrentSkipListSet

interface WidgetRepository {
    fun findById(id: Long): WidgetInterface?
    fun findAll(): ConcurrentSkipListSet<WidgetInterface>
    fun create(widget: WidgetInterface): WidgetInterface
    fun delete(id: Long): WidgetInterface?
    fun update(id:Long, widget: WidgetInterface): WidgetInterface?


}