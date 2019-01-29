package zjj;

import org.junit.Test;
import savvy.wit.framework.core.base.service.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : StreamTest
 * Author : zhoujiajun
 * Date : 2018/12/28 9:12
 * Version : 1.0
 * Description : 
 ******************************/
public class StreamTest {

    private static Log log = LogFactory.getLog();

    public static<T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        List<T> firstElements = stream
                .limit(SIZE + 1)
                .collect(Collectors.toList());

        log.log(title + " : ");
        log.log(firstElements);
    }

    @Test
    public void test() throws IOException {
        Path path = Paths.get("G:\\GitHub\\hikaru\\hikaru-server\\hikaru-core\\src\\main\\resources\\properties\\db.properties");
        String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        Stream<String> words = Stream.of(contents.split("\\PL+"));
        show("words", words);

    }
}
