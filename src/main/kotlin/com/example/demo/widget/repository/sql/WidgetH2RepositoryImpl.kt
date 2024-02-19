package com.example.demo.widget.repository.sql

import com.example.demo.widget.model.Widget
import com.example.demo.widget.model.WidgetEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentSkipListSet

@Repository
class WidgetH2RepositoryImpl(@Autowired val widgetEntityRepository: WidgetEntityRepository) : WidgetRepository {


    override fun findById(id: Long): Widget? =
        widgetEntityRepository.findById(id).get()

    override fun findAll(): ConcurrentSkipListSet<Widget> {
        val zIndexComparator = Comparator<Widget> { w1, w2 -> w1.zIndex.compareTo(w2.zIndex) }

        val widgetSet = ConcurrentSkipListSet(zIndexComparator).apply {
            addAll(widgetEntityRepository.findAll().map { widgetEntity ->
                // Convert WidgetEntity to Widget
                Widget(
                    id = widgetEntity.id,
                    x = widgetEntity.x,
                    y = widgetEntity.y,
                    zIndex = widgetEntity.zIndex,
                    width = widgetEntity.width,
                    height = widgetEntity.height,
                    dateLastUpdate = widgetEntity.dateLastUpdate
                )
            })
        }
        return widgetSet
    }


    override fun create(widget: Widget): Widget {
        val widgetEntity = WidgetEntity(
            widget.x, // Значение X
            widget.y, // Значение Y
            widget.zIndex, // Z-индекс
            widget.width, // Ширина
            widget.height // Высота
        )

        return widgetEntityRepository.saveAndFlush(widgetEntity)
    }


    override fun delete(id: Long): Widget? {
        val deletedWidget = findById(id)
        if (deletedWidget != null) {
            val widgetEntity = WidgetEntity(
                deletedWidget.id ,
                deletedWidget.x, // Значение X
                deletedWidget.y, // Значение Y
                deletedWidget.zIndex, // Z-индекс
                deletedWidget.width, // Ширина
                deletedWidget.height ,
                deletedWidget.dateLastUpdate// Высота
            )
            widgetEntityRepository.delete(widgetEntity)
        }
        return deletedWidget
    }


    @Transactional
    override fun update (id: Long, widget: Widget): Widget? {
        // Ищем существующий виджет по ID
        val foundWidgetEntity = widgetEntityRepository.findById(id).orElse(null) ?: return null

        // Обновляем поля найденного виджета данными из предоставленного виджета
        with(foundWidgetEntity) {
            x = widget.x
            y = widget.y
            zIndex = widget.zIndex
            width = widget.width
            height = widget.height
            dateLastUpdate = LocalDateTime.now()
        }

        // Сохраняем изменения в базе данных
        val updatedWidgetEntity = widgetEntityRepository.save(foundWidgetEntity)

        return Widget(
            id = updatedWidgetEntity.id,
            x = updatedWidgetEntity.x,
            y = updatedWidgetEntity.y,
            zIndex = updatedWidgetEntity.zIndex,
            width = updatedWidgetEntity.width,
            height = updatedWidgetEntity.height,
            dateLastUpdate = updatedWidgetEntity.dateLastUpdate
        )

    }






}