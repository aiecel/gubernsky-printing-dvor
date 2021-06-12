package com.aiecel.gubernskytypography.util;

public interface PageCounter {
    String getSupportedExtension();
    int count(byte[] data);
}
