package com.example.demo.widget.service

import com.example.demo.widget.exception.ParameterValueIsNegative
import com.example.demo.widget.exception.ParameterValueNotFound
import com.example.demo.widget.exception.ParameterValueNotUnique
import com.example.demo.widget.model.Widget
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Service
class WidgetService {
    private var indexWidget: Int = 0
    private val widgetList = ConcurrentHashMap<Int, Widget>()

    fun createWidget(widget: Widget): Widget {
        val nullValueParameterList = listOf( "x" to widget.x,   "y" to widget.y,  "z" to widget.z , "height" to widget.height,  "width" to widget.width).filter { it.second == null }
        val negativeValueParameterList = if ( nullValueParameterList.isEmpty() || nullValueParameterList.any {
                it !in (listOf(
                    "height" to widget.height,
                    "width" to widget.width
                ))
            }) {
            listOf( "height" to widget.height,  "width" to widget.width).filter { it.second!! < 0  }
         } else {
            listOf()
        }
        val isUniqueZIndex = widget.z !in widgetList.values.map { it.z }

        when {

            nullValueParameterList.isNotEmpty() ->  {
                throw ParameterValueNotFound(nullValueParameterList.joinToString(",") { it.first })
            }

            negativeValueParameterList.isNotEmpty() -> {
                throw ParameterValueIsNegative(negativeValueParameterList.joinToString(",") { it.first })
            }
            
            !isUniqueZIndex -> {
                throw ParameterValueNotUnique(widget.z.toString())
            }
             

            else ->{
                if (widget.id == null) {
                    indexWidget++
                    widget.id = indexWidget
                }
                widget.dateLastUpdate = LocalDateTime.now()
                widgetList[indexWidget] = widget
            }
        }
        
        return widget
    }


    fun getAllWidgets() = widgetList.values

}

