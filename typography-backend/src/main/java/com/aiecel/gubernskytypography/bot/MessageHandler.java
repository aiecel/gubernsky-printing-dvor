package com.aiecel.gubernskytypography.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class MessageHandler<M> {
    public abstract M getDefaultMessage();
    public abstract M onMessage(M message, Chatter<M> chatter);
}
