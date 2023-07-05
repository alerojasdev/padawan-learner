package com.rale.tavyraiList.arlanguagedetector;

import java.io.IOException;

public interface LanguageDetectorProvider {
    String languageDetector(String songLyrics) throws Throwable;
}
