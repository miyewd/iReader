package com.cnpiec.ireader;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import com.cnpiec.ireader.utils.FileNameUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IReader1ApplicationTests {

	@Test
	public void bookList() {
		String clientId = "108";
		String clientSecret = "fb16547e7264d0aada18694f9e12cc49";
		String stempSign = clientId + clientSecret;
		System.out.println(stempSign);
        String sign = DigestUtils.md5DigestAsHex(stempSign.getBytes());
        System.out.println("===================="+sign);
	}
	
	@Test
    public void bookInfo() {
        String clientId = "108";
        String bookId = "11262509";
        String clientSecret = "fb16547e7264d0aada18694f9e12cc49";
        String stempSign = clientId + clientSecret+bookId;
        System.out.println(stempSign);
        String sign = DigestUtils.md5DigestAsHex(stempSign.getBytes());
        System.out.println("===================="+sign);
    }
	
	
	@Test
    public void bookList2() {
        String clientId = "4";
        String clientSecret = "90c9561f21f25584531cfb744cbd6919";
        String stempSign = clientId + clientSecret;
        String sign = DigestUtils.md5DigestAsHex(stempSign.getBytes());
        System.out.println("===================="+sign);
    }

	@Test
	public void createFile() throws IOException {
	   // 第834章 那小子呢?
	    //File file = new File("iReaderResource/都市超级透视/"  + "第834章 那小子呢" + ".txt");
	    String fileName = "第338/339章 是你！";
	    String characters = FileNameUtils.replaceSpecialCharacters(fileName);
	    System.out.println("++++++++++" + characters);
	    File file = new File("iReaderResource/" + characters + ".txt");
	    
        if (file.createNewFile()) {
            System.out.println("---success");
            System.out.println(file.getPath());
        }else {
            System.out.println("---false");
        }
	}
}
