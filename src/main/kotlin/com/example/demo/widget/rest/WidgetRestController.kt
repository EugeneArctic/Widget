package com.example.demo.widget.rest

import com.example.demo.widget.model.Widget
import com.example.demo.widget.service.WidgetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


//Атрибуты виджета не должны быть пустыми.
//Z-индекс — это уникальная последовательность, общая для всех виджетов, которая определяет порядок виджетов
//(независимо от их координат).
//Допускаются промежутки. Чем выше значение, тем выше виджет находится на плоскости.
@RestController
@RequestMapping(value = ["/widget"])
class WidgetRestController() {

    val widgetService = WidgetService()

    @PostMapping
    fun postMethod(@RequestBody widget: Widget):ResponseEntity<Widget> {
        val newWidget = widgetService.createWidget(widget)
        return ResponseEntity(newWidget, HttpStatus.CREATED)
    }

    @GetMapping
    fun getMethod(): MutableCollection<Widget> {
        return widgetService.getAllWidgets()
    }


}

