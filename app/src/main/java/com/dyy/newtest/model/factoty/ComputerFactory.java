package com.dyy.newtest.model.factoty;

import com.dyy.newtest.model.AsusComputer;
import com.dyy.newtest.model.HpComputer;
import com.dyy.newtest.model.LenvonComputer;
import com.dyy.newtest.model.modelInterface.Computer;

/**
 * Created by DY on 2018/1/15.
 */

public class ComputerFactory {
    public static Computer create(String type){
        Computer computer=null;
        switch (type){
            case "联想":
                computer = new LenvonComputer();
                break;
            case "惠普":
                computer = new HpComputer();
                break;
            case "华硕":
                computer = new AsusComputer();
                break;
        }
        return computer;
    }
}
