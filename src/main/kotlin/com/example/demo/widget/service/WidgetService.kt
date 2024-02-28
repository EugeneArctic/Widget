package com.example.demo.widget.service

import com.example.demo.widget.exception.ParameterValueIsNegative
import com.example.demo.widget.exception.ParameterValueNotFound
import com.example.demo.widget.exception.WidgetNotFound
import com.example.demo.widget.model.WidgetDTO
import com.example.demo.widget.model.WidgetInterface
import com.example.demo.widget.repository.WidgetRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class WidgetService(private val widgetRepository: WidgetRepository) {

    @Synchronized
    fun getWidgetById(id: Long): WidgetInterface? {
        return widgetRepository.findById(id)
    }

    @Synchronized
    fun deleteWidget(id: Long): WidgetInterface? {
        return widgetRepository.delete(id)
    }

    @Synchronized
    fun updateWidget(id: Long, widgetDTO: WidgetDTO): WidgetInterface? {
        mapToWidgetInterface(widgetDTO)
        val updateWidget = widgetRepository.findById(id)
        updateWidget?.let {
            val widget = handlerForNullZ(widgetDTO)
            widget.id = it.id
            changeZIndexWidgetListForUpdate(widget)
            widgetRepository.update(id, widget)
        }
        return updateWidget
    }

    @Synchronized
    fun createWidget(widgetDTO: WidgetDTO): WidgetInterface {
        val widget = if (widgetDTO.zIndex == null) {
            handlerForNullZ(widgetDTO)
        } else {
            mapToWidgetInterface(widgetDTO)
        }
        changeZIndexWidgetList(widget)
        widget.dateLastUpdate = LocalDateTime.now()
        widgetRepository.create(widget)
        return widget
    }

    private fun handlerForNullZ(widgetDTO: WidgetDTO): WidgetInterface {
        val widgetConcurrentSkipListSet = widgetRepository.findAll()
        if (widgetConcurrentSkipListSet.isNotEmpty()) {
            val maxZ = widgetConcurrentSkipListSet.maxBy { it.zIndex }.zIndex.plus(1)
            widgetDTO.zIndex = maxZ
        } else {
            widgetDTO.zIndex = 1
        }

        return mapToWidgetInterface(widgetDTO)
    }

    fun checkValidParameters(widget: WidgetDTO) {
        val nullValueParameterList = listOf(
            "x" to widget.x, "y" to widget.y, "height" to widget.height, "width" to widget.width
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
    }


    fun checkIdCorrect(id: Long) {
        requireNotNull(widgetRepository.findById(id)) { throw WidgetNotFound(id.toString()) }
    }


    private fun changeZIndexWidgetListForUpdate(updateWidget: WidgetInterface) {
        var previousZ = 0
        widgetRepository.findAll().tailSet(updateWidget).forEachIndexed { _, widget1 ->
            val nextZ = widget1.zIndex.plus(1)
            if (widget1 != updateWidget && previousZ == 0 || (nextZ - previousZ) == 1) {
                widget1.zIndex = nextZ
            }
            previousZ = widget1.zIndex
        }
        //Если z предыдущего элемента меньше на единицу текущего (z + 1 ) то мы наращиваем текущий z = z+1
        //Если разница текущего (z + 1 ) и предыдущего z больше или меньше 1 то мы не нарщиваем текущий z
    }


    fun getAllWidgets() = widgetRepository.findAll()//widgetList.values


    private fun changeZIndexWidgetList(widget: WidgetInterface) {
        val widgetConcurrentSkipListSet = widgetRepository.findAll()
        val updateWidget = widgetConcurrentSkipListSet.find { it.zIndex == widget.zIndex }
        var previousZ = 0
        if (updateWidget != null) {
            widgetConcurrentSkipListSet.tailSet(updateWidget).forEachIndexed { _, widget1 ->
                val nextZ = widget1.zIndex.plus(1)
                if (previousZ == 0 || (nextZ - previousZ) == 1) {
                    widget1.zIndex = nextZ
                }
                previousZ = widget1.zIndex
            }
        }
    }


}

