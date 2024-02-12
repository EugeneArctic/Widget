package com.example.demo.widget.service

import com.example.demo.widget.model.Widget
import kotlinx.coroutines.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger
@SpringBootTest
class WidgetConcurrentReadTest {

    @Test
    fun concurrentlyReadingWidget(): Unit = runBlocking {
        val widgetService = WidgetService()
        val widgetId = 5

        val updateRequest1 = Widget(
            id = widgetId,
            x = 100,
            y = -100,
            z = 1,
            width = 20,
            height = 20,
            dateLastUpdate = LocalDateTime.now()
        )
        val updateRequest2 = Widget(
            id = widgetId,
            x = -100,
            y = 100,
            z = 3,
            width = 20,
            height = 20,
            dateLastUpdate = LocalDateTime.now()
        )

        // Предполагается, что createWidget создает новый виджет с указанным ID
        widgetService.createWidget(
            Widget(
                id = widgetId,
                x = 100,
                y = -100,
                z = 1,
                width = 20,
                height = 20,
                dateLastUpdate = null
            )
        )

        val listUpdateRequest = List(30) { updateRequest1 } + List(10) { updateRequest2 }

        val readIterations = 100

        val readCount = AtomicInteger()


        val readResults = mutableListOf<Widget>()

        coroutineScope {

            listUpdateRequest.shuffled().forEach { widget ->
                launch(Dispatchers.Default) {
                   val widget = widgetService.updateWidget(widgetId, widget)
                    println("${Thread.currentThread().name} widget: $widget ${LocalDateTime.now()}")
                }
            }

            repeat(readIterations) {
                launch(Dispatchers.Default) {
                    val widget = widgetService.getWidgetById(widgetId)
                    synchronized(readResults) {
                        if (widget != null) {
                            readResults.add(widget)
                            println("чтение $widget")
                        }
                    }
                    readCount.incrementAndGet()
                }
            }
        }

        assertThat(readCount.get()).isEqualTo(readIterations)
        assertThat(readResults.size).isEqualTo(readIterations)

        val firstReadResult = readResults.first()
        assertThat(readResults.all { it == firstReadResult }).isTrue()
    }
}




