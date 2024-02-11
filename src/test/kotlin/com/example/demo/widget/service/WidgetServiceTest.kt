package com.example.demo.widget.service

import com.example.demo.widget.model.Widget
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentSkipListSet

class WidgetServiceTest {

    private fun atStartOfDay(): LocalDateTime = LocalDateTime.now().withHour(0).withMinute(0).withHour(0).withSecond(0).withNano(0)

    @Test
    fun addWidgetByZOrder1() {
        val widgetService = WidgetService()
        val widget1 = Widget(null,111,111,1,111,111,null)
        val widget2 = Widget(null,222,222,2,222,222, null)
        val widget3 = Widget(null,333,333,3,333,333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = Widget(null,555,555,2,555,555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(5,555,555,2,555,555, atStartOfDay()),
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
        val widget1 = Widget(null,111,111,1,111,111,null)
        val widget2 = Widget(null,222,222,5,222,222, null)
        val widget3 = Widget(null,333,333,6,333,333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = Widget(null,555,555,2,555,555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(5,555,555,2,555,555, atStartOfDay()),
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
        val widget1 = Widget(null,111,111,1,111,111,null)
        val widget2 = Widget(null,222,222,2,222,222, null)
        val widget3 = Widget(null,333,333,4,333,333, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)

        val newWidget = Widget(null,555,555,2,555,555, atStartOfDay())
        widgetService.createWidget(newWidget)


        val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(5,555,555,2,555,555, atStartOfDay()),
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

        val widget1 = Widget(null,111,111,1,111,111,null)
        val widget2 = Widget(null,222,222,2,222,222, null)
        val widget3 = Widget(null,333,333,3,333,333, null)
        val widget4 = Widget(null,444,444,4,444,444, null)

        widgetService.createWidget(widget1)
        widgetService.createWidget(widget2)
        widgetService.createWidget(widget3)
        widgetService.createWidget(widget4)


        val newWidget = Widget(null,555,555,2,555,555, atStartOfDay())
        widgetService.createWidget(newWidget)


         val correctWidgetResult = ConcurrentSkipListSet(compareBy(Widget::z)).apply {
            addAll(
                setOf(
                    Widget(1, 111, 111, 1, 111, 111, atStartOfDay()),
                    Widget(5,555,555,2,555,555, atStartOfDay()),
                    Widget(2, 222, 222, 3, 222, 222, atStartOfDay()),
                    Widget(3, 333, 333, 4, 333, 333, atStartOfDay()),
                    Widget(4, 444, 444, 5, 444, 444, atStartOfDay())
                )
            )
        }

        println("z order: \n${widgetService.getAllWidgets().map { it.z }}")
        assertEquals(correctWidgetResult.map { it.z }, widgetService.getAllWidgets().map { it.z })
    }
}