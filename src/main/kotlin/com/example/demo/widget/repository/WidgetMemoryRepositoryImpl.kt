package com.example.demo.widget.repository.sql

import com.example.demo.widget.model.Widget
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentSkipListSet

@Repository
class WidgetMemoryRepositoryImpl : WidgetRepository {
    private var indexWidget: Int = 0
    private val widgetList =
        ConcurrentSkipListSet(compareBy(Widget::zIndex)) //private val widgetList = TreeSet(compareBy(Widget::z)) // для провоцирования гонки

    override fun findById(id: Long): Widget? = widgetList.find { it.id == id }
    override fun findAll() = widgetList//widgetList.sortedBy { it.z }

    override fun create(widget: Widget): Widget {
        if (widget.id.toInt() == -1) {
            indexWidget++
            widget.id = indexWidget.toLong()
        }
        widgetList.add(widget)
        return widget
    }


    override fun delete(id: Long): Widget? {
        val deletedWidget = findById(id)
        if (deletedWidget != null) {
            widgetList.remove(deletedWidget)
        }
        return deletedWidget
    }


    override fun update(id: Long, widget: Widget): Widget? {
        val foundWidget = findById(id)
        foundWidget?.apply {
            zIndex = widget.zIndex
            x = widget.x
            y = widget.y
            width = widget.width
            height = widget.height
            dateLastUpdate = LocalDateTime.now()
        }
        return foundWidget
    }


}