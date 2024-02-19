package com.example.demo.widget.model

import java.time.LocalDateTime


 open class Widget(
     open var id: Long,
     open var x: Int,
     open var y: Int,
     open var zIndex: Int,
     open var width: Int,
     open var height: Int,
     open var dateLastUpdate: LocalDateTime
)

