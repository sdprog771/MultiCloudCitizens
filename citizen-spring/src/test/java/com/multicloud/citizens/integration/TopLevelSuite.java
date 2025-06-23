package com.multicloud.citizens.integration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectClasses;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SelectClasses( {FirstIT.class, SecondIT.class, ThirdIT.class} )
public class TopLevelSuite {

}
