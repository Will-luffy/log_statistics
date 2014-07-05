package com.willluffy.statistics.file;

import com.google.common.annotations.VisibleForTesting;
import com.willluffy.statistics.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by dongshuwang on 14-7-5.
 */
@Service
public class FileLocator {

    private static final Logger logger = LoggerFactory.getLogger(FileLocator.class);

    @Value("${scan.in}")
    private String scanIn;

    @Autowired
    @Qualifier("fileValidator")
    private Validator validator;

    public void findFile() {
        logger.info("scan in {} for file", scanIn);
        File file = createFile(scanIn);
        search(file);
    }

    private void search(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                searchFiles(files);
            } else {
                validator.validate(file);
            }
        }
    }

    private void searchFiles(File[] files) {
        if (files != null && files.length > 0) {
            for (File file : files) {
                search(file);
            }
        }
    }

    @VisibleForTesting
    private File createFile(String scanIn) {
        return new File(scanIn);
    }
}
