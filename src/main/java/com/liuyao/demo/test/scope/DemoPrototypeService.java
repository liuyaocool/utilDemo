package com.wisely.highlight_spring4.ch2.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

//2：编写prototype的Bean
@Service
@Scope("prototype") //声明scope为prototype
public class DemoPrototypeService {
}
