package com.example.demo.widget.service

import com.example.demo.widget.model.Widget
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.ConcurrentSkipListSet

class WidgetServiceTest {

    @Test
    fun addWidgetByZOrder1() {
        val widgetService = WidgetService()
        val widget1 = Widget(null, 111, 111, 1, 111, 111, null)
        val widget2 = Widget(null, 222, 222, 2, 222, 222, null)
        val widget3 = Widget(null, 333, 333, 3, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = Widget(null, 555, 555, 2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(5, 555, 555, 2, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 3, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 4, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${widgetService.getAllWidgets().map { it.z }}")
        assertEquals(correctWidgetResult.map { it.z }, widgetService.getAllWidgets().map { it.z })
    }

    @Test
    fun addWidgetByZOrder2() {
        val widgetService = WidgetService()
        val widget1 = Widget(null, 111, 111, 1, 111, 111, null)
        val widget2 = Widget(null, 222, 222, 5, 222, 222, null)
        val widget3 = Widget(null, 333, 333, 6, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = Widget(null, 555, 555, 2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(5, 555, 555, 2, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 5, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 6, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${widgetService.getAllWidgets().map { it.z }}")
        assertEquals(correctWidgetResult.map { it.z }, widgetService.getAllWidgets().map { it.z })
    }


    @Test
    fun addWidgetByZOrder3() {
        val widgetService = WidgetService()
        val widget1 = Widget(null, 111, 111, 1, 111, 111, null)
        val widget2 = Widget(null, 222, 222, 2, 222, 222, null)
        val widget3 = Widget(null, 333, 333, 4, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = Widget(null, 555, 555, 2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(5, 555, 555, 2, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 3, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 4, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${widgetService.getAllWidgets().map { it.z }}")
        assertEquals(correctWidgetResult.map { it.z }, widgetService.getAllWidgets().map { it.z })
    }


    @Test
    fun addWidgetByZOrder4() {

        val widgetService = WidgetService()

        val widget1 = Widget(null, 111, 111, 1, 111, 111, null)
        val widget2 = Widget(null, 222, 222, 2, 222, 222, null)
        val widget3 = Widget(null, 333, 333, 3, 333, 333, null)
        val widget4 = Widget(null, 444, 444, 4, 444, 444, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)
        widgetService.createWidget(widget4)


        val newWidget = Widget(null, 555, 555, 2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
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

        println("z order: \n${widgetService.getAllWidgets().map { it.z }}")
        assertEquals(correctWidgetResult.map { it.z }, widgetService.getAllWidgets().map { it.z })
    }

    @Test
    fun addWidgetByZOrder5() {

        val widgetService = WidgetService()

        val widget1 = Widget(null, 111, 111, -1, 111, 111, null)
        val widget2 = Widget(null, 222, 222, -2, 222, 222, null)
        val widget3 = Widget(null, 333, 333, -3, 333, 333, null)
        val widget4 = Widget(null, 444, 444, -4, 444, 444, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)
        widgetService.createWidget(widget4)


        val newWidget = Widget(null, 555, 555, -2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
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

        println("z order: \n${widgetService.getAllWidgets().map { it.z }}")
        assertEquals(correctWidgetResult.map { it.z }, widgetService.getAllWidgets().map { it.z })
    }

    @Test
    fun addWidgetByZOrder6() {
        val widgetService = WidgetService()
        val widget1 = Widget(null, 111, 111, -1, 111, 111, null)
        val widget2 = Widget(null, 222, 222, -5, 222, 222, null)
        val widget3 = Widget(null, 333, 333, -6, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = Widget(null, 555, 555, -2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, -1, 111, 111, atStartOfDay()),
                    Widget(5, 555, 555, -2, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, -5, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, -6, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${widgetService.getAllWidgets().map { it.z }}")
        assertEquals(correctWidgetResult.map { it.z }, widgetService.getAllWidgets().map { it.z })
    }

    @Test
    fun addWidgetByZOrder7() {
        val widgetService = WidgetService()
        val widget1 = Widget(null, 111, 111, -1, 111, 111, null)
        val widget2 = Widget(null, 222, 222, -2, 222, 222, null)
        val widget3 = Widget(null, 333, 333, -4, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = Widget(null, 555, 555, -2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 0, 111, 111, atStartOfDay()),
                    Widget(5, 555, 555, -1, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, -2, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, -4, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${widgetService.getAllWidgets().map { it.z }}")
        assertEquals(correctWidgetResult.map { it.z }, widgetService.getAllWidgets().map { it.z })
    }


    @Test
    fun addWidgetByZOrder8() {
        val widgetService = WidgetService()
        val widget1 = Widget(null, 111, 111, -10, 111, 111, null)
        val widget2 = Widget(null, 222, 222, 20, 222, 222, null)
        val widget3 = Widget(null, 333, 333, 5, 333, 333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = Widget(null, 555, 555, -10, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, -9, 111, 111, atStartOfDay()),
                    Widget(5, 555, 555, -10, 555, 555, atStartOfDay()),
                    Widget(2, 222, 222, 20, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 5, 333, 333, atStartOfDay()),
                )
            )
        }

        println("z order: \n${widgetService.getAllWidgets().map { it.z }}")
        assertEquals(correctWidgetResult.map { it.z }, widgetService.getAllWidgets().map { it.z })
    }

    @Test
    fun updateWidget() {

        val widgetService = WidgetService()

        val widget1 = Widget(null, 111, 111, 1, 111, 111, null)
        val widget2 = Widget(null, 222, 222, 2, 222, 222, null)
        val widget3 = Widget(null, 333, 333, 3, 333, 333, null)
        val widget4 = Widget(null, 444, 444, 4, 444, 444, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)
        widgetService.createWidget(widget4)


        val newWidget = Widget(null, 555, 555, 2, 555, 555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
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

        println("z order: \n${widgetService.getAllWidgets().map { it.z }}")
        assertEquals(correctWidgetResult.map { it.z }, widgetService.getAllWidgets().map { it.z })

        val updatedWidget = widgetService.updateWidget(
            5, Widget(x = 100, y = -100, z = 3, width = 20, height = 20, dateLastUpdate = null)
        )

        println("updatedWidget: \n${updatedWidget}")

        println("after update z order: \n${widgetService.getAllWidgets().map { it.z }}")


        val correctWidgetResultAfterUpdate = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
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


        assertEquals(correctWidgetResultAfterUpdate.map { it.z }, widgetService.getAllWidgets().map { it.z })

        val updatedWidget2 = widgetService.updateWidget(
            5, Widget(x = 100, y = -100, z = 1, width = 20, height = 20, dateLastUpdate = null)
        )

        println("updatedWidget: \n${updatedWidget2}")

        println("after update z order: \n${widgetService.getAllWidgets().map { it.z }}")

        val correctWidgetResultAfterUpdate2 = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
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

        assertEquals(correctWidgetResultAfterUpdate2.map { it.z }, widgetService.getAllWidgets().map { it.z })

    }

}



