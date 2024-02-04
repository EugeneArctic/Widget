package com.example.demo.test

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



@RestController
@JsonPropertyOrder("name","date")
class RestResponse()
{
     var nameParam: String = ""
     var dateParam: String = ""

    fun setDate(value:String) {
        dateParam = try {
            LocalDateTime.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).toString()
        } catch (e: Exception) {
            "ошибка формата"
        }
    }
    fun setName(value:String)
    { nameParam = value }

    @RequestMapping(value = ["/hello"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun restMethod(@RequestParam name: String,@RequestParam date:String):RestResponse{

        val result = RestResponse()
        result.setName(name)
        result.setDate(date)
        return  result
    }


}

