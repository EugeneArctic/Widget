package com.example.demo.widget.rest

import com.example.demo.widget.exception.*
import com.example.demo.widget.model.Widget
import com.example.demo.widget.model.WidgetDTO
import com.example.demo.widget.service.WidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value = ["/widget"])
class WidgetRestController(@Autowired private val widgetService: WidgetService) {

    @GetMapping
    fun getAllWidgetsMethod(): List<Widget> {
        return widgetService.getAllWidgets().toList()
    }

    @GetMapping("/{id}")
    fun getWidgetsById(@PathVariable id: Int): ResponseEntity<Widget?> {
        widgetService.checkIdCorrect(id)
        val widget = widgetService.getWidgetById(id)
        return ResponseEntity(widget, HttpStatus.OK)
    }

    @PostMapping
    fun postWidget(@RequestBody widget: WidgetDTO): ResponseEntity<Widget> {
        widgetService.checkValidParameters(widget)
        val newWidget = widgetService.createWidget(widget)
        return ResponseEntity(newWidget, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun putWidget(@PathVariable id: Int, @RequestBody widget: WidgetDTO): ResponseEntity<Widget?> {
        widgetService.checkIdCorrect(id)
        widgetService.checkValidParameters(widget)
        val updateWidget = widgetService.updateWidget(id, widget)
        return ResponseEntity(updateWidget, HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteWidget(@PathVariable id: Int): ResponseEntity<Widget?> {
        widgetService.checkIdCorrect(id)
        val updateWidget = widgetService.deleteWidget(id)

        return ResponseEntity(updateWidget, HttpStatus.NO_CONTENT)
    }


    @ExceptionHandler(value = [WidgetNotFound::class, ParameterValueNotFound::class, ParameterValueIsNegative::class, ParameterValueNotUnique::class])
    fun handleErrors(ex: RuntimeException): ResponseEntity<ApiError> {

        val exMessage = when (ex) {
            is ParameterValueIsNegative -> {
                ex.message ?: NEGATIVE_VALUE
            }

            is ParameterValueNotUnique -> {
                ex.message ?: Z_NOT_UNIQUE
            }

            is WidgetNotFound -> {
                ex.message ?: ERROR_NOT_FOUND
            }

            is ParameterValueNotFound -> {
                ex.message ?: ERROR_NULL_VALIDATE_PARAMETER
            }

            else -> {
                ex.message ?: NOT_RECOGNIZE
            }
        }
        val error = ApiError(400, ex.message ?: ERROR_NOT_FOUND)

        println(exMessage)
        return ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST)
    }


    companion object {
        const val ERROR_NOT_FOUND = "Виджет не найден"
        const val ERROR_NULL_VALIDATE_PARAMETER = "Ошибка проверки параметров null"
        const val NEGATIVE_VALUE = "Отрицательное значение"
        const val Z_NOT_UNIQUE = "Параметр Z не уникальный"
        const val NOT_RECOGNIZE = "Неизвестная ошибка"
    }

}


