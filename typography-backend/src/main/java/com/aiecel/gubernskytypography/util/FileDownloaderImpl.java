package com.aiecel.gubernskytypography.util;

import com.aiecel.gubernskytypography.exception.FileDownloadException;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

@Component
public class FileDownloaderImpl implements FileDownloader {
    @Override
    public byte[] download(URL url) throws FileDownloadException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(url.openStream())) {
            byte[] chunk = new byte[1024];
            int n;

            while ((n = bufferedInputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, n);
            }

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new FileDownloadException();
        }
    }
}
