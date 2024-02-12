package com.example.demo.widget.exception

class WidgetNotFound(value:String=""):RuntimeException("Widget with id = $value doesn't exist.")