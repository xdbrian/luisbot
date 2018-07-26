package com.hackaton.bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController.
 *
 * @author lballena.
 */
@RestController
public class HelloController {


  @GetMapping("/hello")
  public String hello(){
    return "Hola :D";
  }

}
