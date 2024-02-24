package com.example.demo.widget.model

import jakarta.persistence.*

import java.time.LocalDateTime

@Entity
class WidgetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) override var id: Long = 0, // Значение по умолчанию для id, так как оно будет генерироваться автоматически
    override var x: Int,
    override var y: Int,
    @Column(name = "z_index") override // Изменение имени столбца для соответствия соглашениям о наименовании базы данных, если это необходимо
    var  zIndex: Int,
    override var width: Int,
    override var height: Int,
    @Column(name = "date_last_update") override var dateLastUpdate: LocalDateTime
) : WidgetInterface {

    constructor() : this(
        id = 0,
        x = 0,
        y = 0,
        zIndex = 0,
        width = 0,
        height = 0,
        dateLastUpdate = LocalDateTime.now()
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