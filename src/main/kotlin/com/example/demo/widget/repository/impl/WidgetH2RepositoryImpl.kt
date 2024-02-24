package com.example.demo.widget.repository.impl

import com.example.demo.widget.model.WidgetEntity
import com.example.demo.widget.model.WidgetInterface
import com.example.demo.widget.repository.WidgetRepository
import com.example.demo.widget.repository.h2.WidgetEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentSkipListSet



@Profile("sqlDB")
@Repository
class WidgetH2RepositoryImpl(@Autowired val widgetEntityRepository: WidgetEntityRepository) : WidgetRepository {


    override fun findById(id: Long): WidgetInterface? = widgetEntityRepository.findById(id).get()


    override fun findAll(): ConcurrentSkipListSet<WidgetInterface> {
        val zIndexComparator = Comparator<WidgetInterface> { w1, w2 -> w1.zIndex.compareTo(w2.zIndex) }

        val widgetSet = ConcurrentSkipListSet(zIndexComparator).apply {
            addAll(widgetEntityRepository.findAll())
        }
        return widgetSet
    }


    override fun create(widget: WidgetInterface): WidgetInterface {
        val widgetEntity = WidgetEntity(
            widget.x, // Значение X
            widget.y, // Значение Y
            widget.zIndex, // Z-индекс
            widget.width, // Ширина
            widget.height // Высота
        )

        return widgetEntityRepository.saveAndFlush(widgetEntity)
    }


    override fun delete(id: Long): WidgetInterface? {
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
    override fun update(id: Long, widget: WidgetInterface): WidgetInterface? {
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
        return widgetEntityRepository.save(foundWidgetEntity)

    }



}