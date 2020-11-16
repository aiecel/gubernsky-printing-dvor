package com.aiecel.gubernskyprintingdvor.bot;

public interface Chatter<M> {
    M getAnswer(M message);
}
