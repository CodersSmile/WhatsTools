package com.whatstools.cleaner;

import java.io.File;

class CleanMain {
    static float objCleanMain;

    static void methodCleanMain(File file) {
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                if (file2.isFile()) {
                    objCleanMain = ((float) file2.length()) + objCleanMain;
                    file2.delete();
                } else if (file2.isDirectory()) {
                    methodCleanMain(file2);
                }
            }
        }
    }
}
