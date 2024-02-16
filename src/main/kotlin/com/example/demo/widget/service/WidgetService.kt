package com.example.demo.widget.service

import com.example.demo.widget.exception.ParameterValueIsNegative
import com.example.demo.widget.exception.ParameterValueNotFound
import com.example.demo.widget.exception.WidgetNotFound
import com.example.demo.widget.model.Widget
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentSkipListSet

@Service
class WidgetService {

    private var indexWidget: Int = 0
    private val widgetList =
        ConcurrentSkipListSet(compareBy(Widget::z)) //private val widgetList = TreeSet(compareBy(Widget::z)) // для провоцирования гонки


    @Synchronized
    fun getWidgetById(id: Int): Widget? {
        return findWidgetById(id)
    }

    @Synchronized
    fun deleteWidget(id: Int): Widget? {
        val foundWidget = findWidgetById(id)
        if (foundWidget != null) {
            widgetList.remove(foundWidget)
        }
        return foundWidget
    }


    @Synchronized
    fun updateWidget(id: Int, widget: Widget): Widget? {
        val foundWidget = findWidgetById(id)
        if (foundWidget != null) {
            rewriteWidgetParameters(widget, foundWidget)
        }
        return foundWidget
    }

    @Synchronized
    fun createWidget(widget: Widget): Widget {
        if (widget.id == null) {
            indexWidget++
            widget.id = indexWidget
        }

        handlerForNullZ(widget)
        widget.dateLastUpdate = LocalDateTime.now()
        addWidgetByZOrder(widget)
        return widget
    }

    private fun handlerForNullZ(widget: Widget) {
        if (widget.z == null) {
            if (widgetList.isNotEmpty()) {
                val maxZ = widgetList.maxBy { it.z!! }.z?.plus(1)
                widget.z = maxZ
            } else {
                widget.z = 1
            }
        }
    }

    fun checkValidParameters(widget: Widget) {
        val nullValueParameterList = listOf(
            "x" to widget.x, "y" to widget.y,
//          "z" to widget.z,
            "height" to widget.height, "width" to widget.width
        ).filter { it.second == null }

        val negativeValueParameterList = if (nullValueParameterList.isEmpty() || nullValueParameterList.any {
                it !in (listOf(
                    "height" to widget.height, "width" to widget.width
                ))
            }) {
            listOf("height" to widget.height, "width" to widget.width).filter { it.second!! < 0 }
        } else {
            listOf()
        }

        require(nullValueParameterList.isEmpty()) { throw ParameterValueNotFound(nullValueParameterList.joinToString(", ") { "«${it.first}»" }) }
        require(negativeValueParameterList.isEmpty()) {
            throw ParameterValueIsNegative(negativeValueParameterList.joinToString(
                ", "
            ) { "«${it.first}»" })
        }
//        require(widgetList.none { it.z == widget.z }) { throw ParameterValueNotUnique(widget.z.toString()) }
    }


    fun checkIdCorrect(id: Int) {
        requireNotNull(findWidgetById(id)) { throw WidgetNotFound(id.toString()) }

    }


    fun addWidgetByZOrder(widget: Widget) {
        changeZIndexWidgetList(widget)
        widgetList.add(widget)
    }


    private fun changeZIndexWidgetList(widget: Widget) {
        val updateWidget = widgetList.find { it.z == widget.z }
        var previousZ = 0

        if (updateWidget != null) {
            widgetList.tailSet(updateWidget).forEachIndexed { _, widget1 ->
                val nextZ = widget1.z?.plus(1)!!
                if (previousZ == 0 || (nextZ - previousZ) == 1) {
                    widget1.z = nextZ
                }
                previousZ = widget1.z!!
            }
        }
    }

    private fun changeZIndexWidgetListForUpdate(updateWidget: Widget) {
        var previousZ = 0
        widgetList.tailSet(updateWidget).forEachIndexed { _, widget1 ->
            val nextZ = widget1.z?.plus(1)!!
            if (widget1 != updateWidget && previousZ == 0 || (nextZ - previousZ) == 1) {
                widget1.z = nextZ
            }
            previousZ = widget1.z!!
        }
        //Если z предыдущего элемента меньше на единицу текущего (z + 1 ) то мы наращиваем текущий z = z+1
        //Если разница текущего (z + 1 ) и предыдущего z больше или меньше 1 то мы не нарщиваем текущий z
    }


    fun getAllWidgets() = widgetList.sortedBy { it.z }//widgetList.values

    fun findWidgetById(id: Int) = widgetList.find { it.id == id }

    fun rewriteWidgetParameters(newParametersWidget: Widget, foundWidget: Widget) {
        foundWidget.apply {
            z = newParametersWidget.z
            x = newParametersWidget.x
            y = newParametersWidget.y
            width = newParametersWidget.width
            height = newParametersWidget.height
            dateLastUpdate = LocalDateTime.now()
        }
        handlerForNullZ(foundWidget)
        changeZIndexWidgetListForUpdate(foundWidget)
    }
}

