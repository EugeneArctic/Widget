package com.example.demo.widget.model

import java.time.LocalDateTime

//Глоссарий
//Виджет — это объект на плоскости в системе координат, который имеет координаты
//(X, Y), Z-индекс - являются целыми числами (могут быть отрицательными).
//ширина и высота - — это целые числа > 0.
//дату последнего изменения
//уникальный идентификатор.
//
data class Widget(
    var id: Int? = null ,
    var x: Int,
    var y: Int,
    var z: Int,
    var width: Int,
    var height: Int,
    var dateLastUpdate: LocalDateTime?
)

