package com.example.demo.widget.service

import com.example.demo.widget.model.Widget
import com.example.demo.widget.model.WidgetDTO
import com.example.demo.widget.repository.WidgetMemoryRepositoryImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.ConcurrentSkipListSet

class WidgetServiceTest {
   private val widgetRepositoryImpl = WidgetMemoryRepositoryImpl()

    @Test
    fun addWidgetByZOrder1() {
        val widgetService = WidgetService(widgetRepositoryImpl)
        val widget1 = WidgetDTO(null, 111, 111, 1, 111, 111, null)
        val widget2 = WidgetDTO(null, 222, 222, 2, 222, 222, null)
        val widget3 = WidgetDTO(null, 333, 333, 3, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = WidgetDTO(null, 555, 555, 2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::zIndex)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(4, 555, 555, 2, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 3, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 4, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${transformResult(widgetService.getAllWidgets())}")
        assertEquals(transformResult(correctWidgetResult), transformResult(widgetService.getAllWidgets()))
    }

    @Test
    fun addWidgetByZOrder2() {
        val widgetService = WidgetService(widgetRepositoryImpl)
        val widget1 = WidgetDTO(null, 111, 111, 1, 111, 111, null)
        val widget2 = WidgetDTO(null, 222, 222, 5, 222, 222, null)
        val widget3 = WidgetDTO(null, 333, 333, 6, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = WidgetDTO(null, 555, 555, 2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::zIndex)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(4, 555, 555, 2, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 5, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 6, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${transformResult(widgetService.getAllWidgets())}")
        assertEquals(transformResult(correctWidgetResult), transformResult(widgetService.getAllWidgets()))
    }


    @Test
    fun addWidgetByZOrder3() {
        val widgetService = WidgetService(widgetRepositoryImpl)
        val widget1 = WidgetDTO(null, 111, 111, 1, 111, 111, null)
        val widget2 = WidgetDTO(null, 222, 222, 2, 222, 222, null)
        val widget3 = WidgetDTO(null, 333, 333, 4, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = WidgetDTO(null, 555, 555, 2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::zIndex)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(4, 555, 555, 2, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 3, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 4, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${transformResult(widgetService.getAllWidgets())}")
        assertEquals(transformResult(correctWidgetResult), transformResult(widgetService.getAllWidgets()))
    }


    @Test
    fun addWidgetByZOrder4() {

        val widgetService = WidgetService(widgetRepositoryImpl)

        val widget1 = WidgetDTO(null, 111, 111, 1, 111, 111, null)
        val widget2 = WidgetDTO(null, 222, 222, 2, 222, 222, null)
        val widget3 = WidgetDTO(null, 333, 333, 3, 333, 333, null)
        val widget4 = WidgetDTO(null, 444, 444, 4, 444, 444, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)
        widgetService.createWidget(widget4)


        val newWidget = WidgetDTO(null, 555, 555, 2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::zIndex)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(5, 555, 555, 2, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 3, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 4, 333, 333, atStartOfDay()),
                    Widget(4, 444, 444, 5, 444, 444, atStartOfDay())
                )
            )
        }

        println("z order: \n${transformResult(widgetService.getAllWidgets())}")
        assertEquals(transformResult(correctWidgetResult), transformResult(widgetService.getAllWidgets()))
    }

    @Test
    fun addWidgetByZOrder5() {

        val widgetService = WidgetService(widgetRepositoryImpl)

        val widget1 = WidgetDTO(null, 111, 111, -1, 111, 111, null)
        val widget2 = WidgetDTO(null, 222, 222, -2, 222, 222, null)
        val widget3 = WidgetDTO(null, 333, 333, -3, 333, 333, null)
        val widget4 = WidgetDTO(null, 444, 444, -4, 444, 444, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)
        widgetService.createWidget(widget4)


        val newWidget = WidgetDTO(null, 555, 555, -2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::zIndex)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 0, 111, 111, atStartOfDay()),
                    Widget(5, 555, 555, -1, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, -2, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, -3, 333, 333, atStartOfDay()),
                    Widget(4, 444, 444, -4, 444, 444, atStartOfDay())
                )
            )
        }

        println("z order: \n${transformResult(widgetService.getAllWidgets())}")
        assertEquals(transformResult(correctWidgetResult), transformResult(widgetService.getAllWidgets()))
    }

    @Test
    fun addWidgetByZOrder6() {
        val widgetService = WidgetService(widgetRepositoryImpl)
        val widget1 = WidgetDTO(null, 111, 111, -1, 111, 111, null)
        val widget2 = WidgetDTO(null, 222, 222, -5, 222, 222, null)
        val widget3 = WidgetDTO(null, 333, 333, -6, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = WidgetDTO(null, 555, 555, -2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::zIndex)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, -1, 111, 111, atStartOfDay()),
                    Widget(4, 555, 555, -2, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, -5, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, -6, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${transformResult(widgetService.getAllWidgets())}")
        assertEquals(transformResult(correctWidgetResult), transformResult(widgetService.getAllWidgets()))
    }

    @Test
    fun addWidgetByZOrder7() {
        val widgetService = WidgetService(widgetRepositoryImpl)
        val widget1 = WidgetDTO(null, 111, 111, -1, 111, 111, null)
        val widget2 = WidgetDTO(null, 222, 222, -2, 222, 222, null)
        val widget3 = WidgetDTO(null, 333, 333, -4, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = WidgetDTO(null, 555, 555, -2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::zIndex)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 0, 111, 111, atStartOfDay()),
                    Widget(4, 555, 555, -1, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, -2, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, -4, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${transformResult(widgetService.getAllWidgets())}")
        assertEquals(transformResult(correctWidgetResult), transformResult(widgetService.getAllWidgets()))
    }


    @Test
    fun addWidgetByZOrder8() {
        val widgetService = WidgetService(widgetRepositoryImpl)
        val widget1 = WidgetDTO(null, 111, 111, -10, 111, 111, null)
        val widget2 = WidgetDTO(null, 222, 222, 20, 222, 222, null)
        val widget3 = WidgetDTO(null, 333, 333, 5, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = WidgetDTO(null, 555, 555, -10, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::zIndex)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, -9, 111, 111, atStartOfDay()),
                    Widget(4, 555, 555, -10, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 20, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 5, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${transformResult(widgetService.getAllWidgets())}")
        assertEquals(transformResult(correctWidgetResult), transformResult(widgetService.getAllWidgets()))
    }

    @Test
    fun updateWidgetDTO() {

        val widgetService = WidgetService(widgetRepositoryImpl)

        val widget1 = WidgetDTO(null, 111, 111, 1, 111, 111, null)
        val widget2 = WidgetDTO(null, 222, 222, 2, 222, 222, null)
        val widget3 = WidgetDTO(null, 333, 333, 3, 333, 333, null)
        val widget4 = WidgetDTO(null, 444, 444, 4, 444, 444, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)
        widgetService.createWidget(widget4)


        val newWidget = WidgetDTO(null, 555, 555, 2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::zIndex)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(5, 555, 555, 2, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 3, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 4, 333, 333, atStartOfDay()),
                    Widget(4, 444, 444, 5, 444, 444, atStartOfDay())
                )
            )
        }

        println("z order: \n${transformResult(widgetService.getAllWidgets())}")
        assertEquals(
            transformResult(correctWidgetResult),
            transformResult(widgetService.getAllWidgets())
        )

        val updatedWidget = widgetService.updateWidget(
            5, WidgetDTO(id = null, x = 100, y = -100, zIndex = 3, width = 20, height = 20, dateLastUpdate = null)
        )

        println("updatedWidget: \n${updatedWidget}")

        println("after update z order: \n${transformResult(widgetService.getAllWidgets())}")


        val correctWidgetResultAfterUpdate = ConcurrentSkipListSet(compareBy(Widget::zIndex)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(5, 555, 555, 3, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 4, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 5, 333, 333, atStartOfDay()),
                    Widget(4, 444, 444, 6, 444, 444, atStartOfDay())
                )
            )
        }


        assertEquals(transformResult(correctWidgetResultAfterUpdate), transformResult(widgetService.getAllWidgets()))

        val updatedWidget2 = widgetService.updateWidget(
            5, WidgetDTO(id = null, x = 100, y = -100, zIndex = 1, width = 20, height = 20, dateLastUpdate = null)
        )

        println("updatedWidget: \n${updatedWidget2}")

        println("after update z order: \n${transformResult(widgetService.getAllWidgets())}")

        val correctWidgetResultAfterUpdate2 = ConcurrentSkipListSet(compareBy(Widget::zIndex)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 2, 111, 111, atStartOfDay()),
                    Widget(5, 555, 555, 1, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 4, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 5, 333, 333, atStartOfDay()),
                    Widget(4, 444, 444, 6, 444, 444, atStartOfDay())
                )
            )
        }

        assertEquals(transformResult(correctWidgetResultAfterUpdate2), transformResult(widgetService.getAllWidgets()))

    }


}



