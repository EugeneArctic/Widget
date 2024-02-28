package com.example.demo.widget.model

import java.time.LocalDateTime

interface WidgetInterface {
    var id: Long
    var x: Int
    var y: Int
    var zIndex: Int
    var width: Int
    var height: Int
    var dateLastUpdate: LocalDateTime

}