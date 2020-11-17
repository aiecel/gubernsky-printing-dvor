package com.aiecel.gubernskyprintingdvor.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class MessageHandler<M> {
    public abstract M onMessage(M message, Chatter<M> chatter);
}
