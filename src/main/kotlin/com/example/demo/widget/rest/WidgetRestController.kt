package com.example.demo.widget.rest

import com.example.demo.widget.exception.*
import com.example.demo.widget.model.DeleteWidget
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
    @GetMapping
    fun getAllWidgetsMethod(): List<Widget> {
        return widgetService.getAllWidgets()
    }

    @GetMapping("/{id}")
    fun getWidgetsById(@PathVariable id: Int):  ResponseEntity<Widget?> {
        widgetService.checkIdCorrect(id)
        val widget = widgetService.getWidgetById(id)
        return ResponseEntity(widget, HttpStatus.OK)
    }

    @PostMapping
    fun postMethod(@RequestBody widget: Widget):ResponseEntity<Widget> {
        widgetService.checkValidParameters(widget)
        val newWidget = widgetService.createWidget(widget)
        return ResponseEntity(newWidget, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun putMethod(@PathVariable id: Int, @RequestBody widget: Widget): ResponseEntity<Widget?> {
        widgetService.checkIdCorrect(id)
        widgetService.checkValidParameters(widget)
        val updateWidget = widgetService.updateWidget(id,widget)
        return ResponseEntity(updateWidget, HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteMethod(@PathVariable id: Int): ResponseEntity<DeleteWidget?> {
        widgetService.checkIdCorrect(id)
        val updateWidget = widgetService.deleteWidget(id)
        val answer = updateWidget?.let { DeleteWidget("widget with id = $id was deleted", it) }
        return ResponseEntity(answer, HttpStatus.NO_CONTENT)
    }


    @ExceptionHandler(value = [WidgetNotFound::class])
    fun handleNotFoundWidget(ex: WidgetNotFound): ResponseEntity<ApiError> {
        val error = ApiError(400, ex.message ?: "Ошибка в параметрах" )
        println(" Ошибка проверки параметров: ${ex.message}")
        return ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(value = [ParameterValueNotFound::class])
    fun handleParameterIsNull(ex: ParameterValueNotFound): ResponseEntity<ApiError> {
        val error = ApiError(400, ex.message ?: "Ошибка в параметрах" )
        println(" Ошибка проверки параметров: ${ex.message}")
        return ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [ParameterValueIsNegative::class])
    fun handleParameterIsNegative(ex: ParameterValueIsNegative): ResponseEntity<ApiError> {
        val error = ApiError(400, ex.message ?: "Отрицательное значение" )
        return ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [ParameterValueNotUnique::class])
    fun handleParameterIsNotUnique(ex: ParameterValueNotUnique): ResponseEntity<ApiError> {
        val error = ApiError(400, ex.message ?: "Параметр Z не уникальный" )
        return ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST)
    }




}


