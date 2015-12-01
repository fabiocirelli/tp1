package edu.iut.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationInfoLogTest {

    @Test
    public void testSetMessage() throws Exception {
        ApplicationInfoLog info = new ApplicationInfoLog();
        info.setMessage("test");

        if(!info.message.equals("test")){
            fail("setMessage ne fonctionne pas");
        }
    }
}