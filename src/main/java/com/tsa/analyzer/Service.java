package com.tsa.analyzer;

import java.io.File;

public class Service {

    static Object[] checkParameters(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("You should provide only TWO arguments:\n" +
                    " the first is a PATH to a file, the second is a WORD to find");
        }
        Object[] parameters = new Object[2];
        parameters[1] = args[1];
        File file = new File(args[0]);
        parameters[0] = file;
        if (!file.canExecute()) {
            throw new RuntimeException("there is a problem with the Path: " + file.getAbsolutePath());
        }
        return parameters;
    }

}
