package com.example.demo.widget.exception

class ParameterValueIsNegative(value:String=""):RuntimeException("Parameter(s): $value  must not be negative")