package com.aiecel.gubernskytypography.util;

import com.aiecel.gubernskytypography.exception.FileDownloadException;

import java.net.URL;

public interface FileDownloader {
    byte[] download(URL url) throws FileDownloadException;
}
