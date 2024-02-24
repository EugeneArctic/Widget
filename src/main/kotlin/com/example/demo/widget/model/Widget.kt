package com.example.demo.widget.model

import java.time.LocalDateTime


class Widget(
    override var id: Long,
    override var x: Int,
    override var y: Int,
    override var zIndex: Int,
    override var width: Int,
    override var height: Int,
    override var dateLastUpdate: LocalDateTime
) : WidgetInterface {

}

