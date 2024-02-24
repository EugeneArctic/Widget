package com.example.demo.widget.repository.h2

import com.example.demo.widget.model.WidgetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WidgetEntityRepository: JpaRepository<WidgetEntity, Long?> {

}
