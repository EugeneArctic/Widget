package com.example.demo.widget.service

import com.example.demo.widget.model.Widget
import com.example.demo.widget.model.WidgetDTO
import com.example.demo.widget.repository.WidgetRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
class WidgetConcurrentReadTest() {
    val widgetRepositoryImpl = WidgetRepositoryImpl()

    @Test
    fun concurrentlyReadingWidget(): Unit = runBlocking {
        val widgetService = WidgetService(widgetRepositoryImpl)
        val widgetId = 5L

        val updateRequest1 = WidgetDTO(
            id = widgetId, x = 100, y = -100, zIndex = 1, width = 20, height = 20, dateLastUpdate = LocalDateTime.now()
        )
        val updateRequest2 = WidgetDTO(
            id = widgetId, x = -100, y = 100, zIndex = 3, width = 20, height = 20, dateLastUpdate = LocalDateTime.now()
        )

        // Предполагается, что createWidget создает новый виджет с указанным ID
        widgetService.createWidget(
            WidgetDTO(
                id = widgetId, x = 100, y = -100, zIndex = 1, width = 20, height = 20, dateLastUpdate = null
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




