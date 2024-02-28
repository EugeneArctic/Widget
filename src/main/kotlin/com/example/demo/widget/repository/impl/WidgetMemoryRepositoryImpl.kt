package com.example.demo.widget.repository.impl

import com.example.demo.widget.model.WidgetInterface
import com.example.demo.widget.repository.WidgetRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentSkipListSet

@Profile("in-mem")
@Repository
class WidgetMemoryRepositoryImpl : WidgetRepository {
    private var indexWidget: Int = 0
    private val widgetList =
        ConcurrentSkipListSet(compareBy(WidgetInterface::zIndex)) //private val widgetList = TreeSet(compareBy(Widget::z)) // для провоцирования гонки

    override fun findById(id: Long): WidgetInterface? = widgetList.find { it.id == id }
    override fun findAll(): ConcurrentSkipListSet<WidgetInterface> = widgetList//widgetList.sortedBy { it.z }

    override fun create(widget: WidgetInterface): WidgetInterface {
        if (widget.id.toInt() == -1) {
            indexWidget++
            widget.id = indexWidget.toLong()
        }
        widgetList.add(widget)
        return widget
    }


    override fun delete(id: Long): WidgetInterface? {
        val deletedWidget = findById(id)
        if (deletedWidget != null) {
            widgetList.remove(deletedWidget)
        }
        return deletedWidget
    }



    override fun update(id: Long, widget: WidgetInterface): WidgetInterface? {
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