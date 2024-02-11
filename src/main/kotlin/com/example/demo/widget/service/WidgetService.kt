package com.example.demo.widget.service

import com.example.demo.widget.exception.ParameterValueIsNegative
import com.example.demo.widget.exception.ParameterValueNotFound
import com.example.demo.widget.model.Widget
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentSkipListSet

@Service
class WidgetService {

    private var indexWidget: Int = 0
//  private val widgetList = ConcurrentHashMap<Int, Widget>()
    private val widgetList = ConcurrentSkipListSet(compareBy(Widget::z))


    fun createWidget(widget: Widget): Widget {
        if (widget.id == null) {
            indexWidget++
            widget.id = indexWidget
        }

        if (widget.z == null) {
            if (widgetList.isNotEmpty()) {
                val maxZ = widgetList.maxBy { it.z!! }.z?.plus(1)
                widget.z = maxZ
            } else {
                widget.z = 1
            }
        }
        widget.dateLastUpdate = LocalDateTime.now()
        addWidgetByZOrder(widget)
        return widget
    }

     fun checkValidParameters(widget: Widget) {

        val nullValueParameterList = listOf(
            "x" to widget.x,
            "y" to widget.y,
//          "z" to widget.z,
            "height" to widget.height,
            "width" to widget.width
        ).filter { it.second == null }

        val negativeValueParameterList = if (nullValueParameterList.isEmpty() || nullValueParameterList.any {
                it !in (listOf(
                    "height" to widget.height,
                    "width" to widget.width
                ))
            }) {
            listOf("height" to widget.height, "width" to widget.width).filter { it.second!! < 0 }
        } else {
            listOf()
        }

        require(nullValueParameterList.isEmpty()) { throw ParameterValueNotFound(nullValueParameterList.joinToString(", ") { "«${it.first}»" }) }
        require(negativeValueParameterList.isEmpty()) {
            throw ParameterValueIsNegative(
                negativeValueParameterList.joinToString(
                    ", "
                ) { "«${it.first}»" })
        }
//        require(widgetList.none { it.z == widget.z }) { throw ParameterValueNotUnique(widget.z.toString()) }
    }







    fun addWidgetByZOrder(widget: Widget) {
        val updateWidget = widgetList.find { it.z == widget.z }
        var previousZ = 0


        if (updateWidget != null) {
            widgetList.tailSet(updateWidget).forEachIndexed { _, widget1 ->
                    val nextZ =  widget1.z?.plus(1)!!
                    if (previousZ == 0 || (nextZ - previousZ) == 1) {
                        widget1.z = nextZ
                    }
                    previousZ = widget1.z!!
            }

        }

        widgetList.add(widget)
    }


    fun getAllWidgets() = widgetList.toList() //widgetList.values

}

