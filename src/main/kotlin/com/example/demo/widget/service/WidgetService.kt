package com.example.demo.widget.service

import com.example.demo.widget.model.Widget
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Service
class WidgetService {
    private var indexWidget: Int = 0
    private val widgetList = ConcurrentHashMap<Int,Widget>()

    fun createWidget(widget: Widget):Widget{
        if (widget.id == null){
            indexWidget++
            widget.id = indexWidget
        }
        widget.dateLastUpdate = LocalDateTime.now()
        widgetList[indexWidget] = widget
        return widget
    }
    fun getAllWidgets() = widgetList.values
}