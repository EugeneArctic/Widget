package com.example.demo.widget.service

import com.example.demo.widget.model.Widget
import kotlinx.coroutines.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentSkipListSet
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
class WidgetConcurrentUpdateTest {
    @SpringBootTest
    class WidgetConcurrentUpdateTest {
        @Test
        @Throws(InterruptedException::class)
        fun concurrentlyUpdatingWidget(): Unit = runBlocking {
            val widgetService = WidgetService()
            val widgetId = 5

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

            val listUpdateRequest = List(30) { updateRequest1 } + List(10) { updateRequest2 }
            val mutableListUpdateRequest =  ConcurrentSkipListSet(compareBy(Widget::z))

            // Запуск потоков для конкурентного обновления
            val updateCount = AtomicInteger()
            coroutineScope {
                listUpdateRequest.shuffled().forEach { widget ->
                    launch(Dispatchers.Default) {
                        val widget1 = widgetService.updateWidget(widgetId, widget)
                        if (widget1 != null) {
                            mutableListUpdateRequest.add(widget1)
                        }
                        println("${Thread.currentThread().name} widget: $widget1 ${LocalDateTime.now()}")
                        updateCount.incrementAndGet()
                    }
                }
            }

            assertThat(updateCount.get()).isEqualTo(40)

            val updatedWidget: Widget? = widgetService.findWidgetById(widgetId)

            println("updatedWidget: $updatedWidget ${LocalDateTime.now()}")
            assertThat(updatedWidget).matches { widget ->
                mutableListUpdateRequest.any { it == widget }
//                (widget?.equals(updateRequest1) == true) || (widget?.equals(updateRequest2) == true)
            }
        }
    }
}

