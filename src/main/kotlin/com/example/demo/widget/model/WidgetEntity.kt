package com.example.demo.widget.model

import jakarta.persistence.*

import java.time.LocalDateTime

@Entity
class WidgetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Long = 0, // Значение по умолчанию для id, так как оно будет генерироваться автоматически
    override var x: Int,
    override var y: Int,
    @Column(name = "z_index") // Изменение имени столбца для соответствия соглашениям о наименовании базы данных, если это необходимо
    override var  zIndex: Int,
    override var width: Int,
    override var height: Int,
    @Column(name = "date_last_update")
    override var dateLastUpdate: LocalDateTime
) : Widget(id, x, y, zIndex, width, height, dateLastUpdate) {

    constructor() : this(
        id = 0, // ID будет сгенерировано автоматически
        x = 0,
        y = 0,
        zIndex = 0, // Устанавливаем zIndex в значение по умолчанию
        width = 0,
        height = 0,
        dateLastUpdate = LocalDateTime.now() // Устанавливаем текущее время
    )
    constructor(x: Int, y: Int, zIndex: Int, width: Int, height: Int) : this(
        x = x,
        y = y,
        zIndex = zIndex, // Устанавливаем zIndex в значение по умолчанию
        width = width,
        height = height,
        // ID будет сгенерировано автоматически
        dateLastUpdate = LocalDateTime.now() // Устанавливаем текущее время
    )

}

