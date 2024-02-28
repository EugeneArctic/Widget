package com.example.demo.widget.rest

import com.example.demo.widget.exception.*
import com.example.demo.widget.model.Widget
import com.example.demo.widget.model.WidgetDTO
import com.example.demo.widget.service.WidgetService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(value = ["/widget"])
class WidgetRestController(@Autowired private val widgetService: WidgetService) {

    @GetMapping
    fun getAllWidgets(): List<Widget> {
        return widgetService.getAllWidgets().toList()
    }

    @Operation(summary = "Get widget by it's id")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Found widget by it's id", content = [
            (Content(mediaType = "application/json", array = (
                    ArraySchema(schema = Schema(implementation = WidgetDTO::class)))))]),
        ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Did not find widget by id", content = [Content()])]
    )
    @GetMapping("/{id}")
    fun getWidgetsById(@PathVariable id: Long): ResponseEntity<Widget?> {
        widgetService.checkIdCorrect(id)
        val widget = widgetService.getWidgetById(id)
        return ResponseEntity(widget, HttpStatus.OK)
    }

    @PostMapping
    fun createWidget(@RequestBody widget: WidgetDTO): ResponseEntity<Widget> {
        widgetService.checkValidParameters(widget)
        val newWidget = widgetService.createWidget(widget)
        return ResponseEntity(newWidget, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateWidget(@PathVariable id: Long, @RequestBody widget: WidgetDTO): ResponseEntity<Widget?> {
        widgetService.checkIdCorrect(id)
        widgetService.checkValidParameters(widget)
        val updateWidget = widgetService.updateWidget(id, widget)
        return ResponseEntity(updateWidget, HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteWidget(@PathVariable id: Long): ResponseEntity<Widget?> {
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


