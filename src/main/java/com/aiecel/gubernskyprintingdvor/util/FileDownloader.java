package com.aiecel.gubernskyprintingdvor.util;

import com.aiecel.gubernskyprintingdvor.exception.FileDownloadException;

import java.net.URL;

public interface FileDownloader {
    byte[] download(URL url) throws FileDownloadException;
}
