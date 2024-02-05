package com.example.demo.widget.exception

class ParameterValueNotFound(value:String=""):RuntimeException("Parameter(s): $value  must not be null")