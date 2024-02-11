package com.example.demo.widget.rest

import com.example.demo.widget.model.Widget
import com.example.demo.widget.exception.ApiError
import com.example.demo.widget.exception.ParameterValueIsNegative
import com.example.demo.widget.exception.ParameterValueNotFound
import com.example.demo.widget.exception.ParameterValueNotUnique
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
        widgetService.checkValidParameters(widget)
        val newWidget = widgetService.createWidget(widget)
        return ResponseEntity(newWidget, HttpStatus.CREATED)
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

    @GetMapping
    fun getMethod(): List<Widget> {
        return widgetService.getAllWidgets()
    }


}

//    @PostMapping
//    fun postMethod(@RequestBody widget: Widget): ResponseEntity<out Any> {
//        val response = try {
//            val newWidget = widgetService.createWidget(widget)
//            ResponseEntity(newWidget, HttpStatus.CREATED)
//        } catch (e: Exception) {
//            val error = ApiError(400, "ошибка")
//            ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST)
//        }
//
//        return response
//    }

