package com.aiecel.gubernskytypography.util;

import com.aiecel.gubernskytypography.exception.ExtensionNotSupportedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
@Getter
public class PageCounterFactory {
    private final Collection<PageCounter> pageCounters;

    public PageCounter getPageCounter(String extension) {
        for (PageCounter pageCounter : pageCounters) {
            if (pageCounter.getSupportedExtension().equals(extension)) {
                return pageCounter;
            }
        }
        throw new ExtensionNotSupportedException("Files with extension " + extension + "are not supported");
    }
}
