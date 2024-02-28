package com.example.demo.widget.service

import com.example.demo.widget.model.Widget
import com.example.demo.widget.model.WidgetDTO
import com.example.demo.widget.model.WidgetInterface
import java.time.LocalDateTime

fun mapToWidgetInterface(widgetDTO: WidgetDTO): WidgetInterface {
    val widget = widgetDTO.let { dto ->
        Widget(
            id = dto.id ?: -1,
            x = dto.x ?: -1,
            y = dto.y ?: -1,
            zIndex = dto.zIndex ?: -1,
            width = dto.width ?: -1,
            height = dto.height ?: -1,
            dateLastUpdate = dto.dateLastUpdate ?: LocalDateTime.now().minusDays(1)
        )
    }
    return widget
}

fun mapToDTOWidget(widgetInterface : WidgetInterface): WidgetDTO {
    val widgetDTO = widgetInterface.let { widget ->
        WidgetDTO(
            id = widget.id,
            x = widget.x,
            y = widget.y,
            zIndex = widget.zIndex,
            width = widget.width,
            height = widget.height,
            dateLastUpdate = widget.dateLastUpdate
        )
    }
    return widgetDTO
}
