package com.example.demo.widget.exception

class ParameterValueNotUnique(value:String=""):RuntimeException("Parameter value 'Z' must be unique. Z = $value  already exists.")