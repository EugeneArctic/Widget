package com.example.demo.widget.exception

class ParameterValueIsNegative(value:String=""):RuntimeException("Parameter(s): $value  must be positive")
class ParameterValueNotFound(value:String=""):RuntimeException("Parameter(s): $value  must not be null")
class ParameterValueNotUnique(value:String=""):RuntimeException("Parameter value 'Z' must be unique. Z = $value  already exists.")
class WidgetNotFound(value:String=""):RuntimeException("Widget with id = $value doesn't exist.")

