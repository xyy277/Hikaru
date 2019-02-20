package com.gsafety.hikaru.repository;

import com.gsafety.hikaru.entity.test.Sunday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : SundayRepository
 * Author : zhoujiajun
 * Date : 2019/2/19 11:31
 * Version : 1.0
 * Description : 
 ******************************/
public interface SundayRepository extends JpaRepository<Sunday, String>  {

    @Query("select s from Sunday s where s.temperature = :temperture")
    Sunday findByTemperature(double temperature);
}
