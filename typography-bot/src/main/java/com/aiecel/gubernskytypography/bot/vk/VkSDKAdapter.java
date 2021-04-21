package com.aiecel.gubernskytypography.bot.vk;

import com.aiecel.gubernskytypography.bot.api.*;
import com.aiecel.gubernskytypography.bot.api.adapter.KeyboardAdapter;
import com.aiecel.gubernskytypography.bot.api.adapter.UserMessageAdapter;
import com.aiecel.gubernskytypography.bot.api.keyboard.Button;
import com.vk.api.sdk.objects.messages.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VkSDKAdapter implements UserMessageAdapter<Message>, KeyboardAdapter<Keyboard> {
    @Override
    public UserMessage toUserMessage(Message message) {
        return new UserMessage(new VkUser(message.getFromId()), message.getText(), "vk");
    }

    @Override
    public Keyboard fromKeyboard(com.aiecel.gubernskytypography.bot.api.keyboard.Keyboard keyboard) {
        List<List<KeyboardButton>> buttons = new ArrayList<>();

        for (List<Button> row : keyboard.getButtons()) {
            List<KeyboardButton> newRow = new ArrayList<>();
            for (Button button : row) {
                newRow.add(convertButton(button));
            }
            buttons.add(newRow);
        }

        return new Keyboard()
                .setButtons(buttons)
                .setOneTime(true);
    }

    private KeyboardButton convertButton(Button button) {
        KeyboardButtonColor color;

        switch (button.getType()) {
            case PRIMARY:
                color = KeyboardButtonColor.PRIMARY;
                break;

            case POSITIVE:
                color = KeyboardButtonColor.POSITIVE;
                break;

            case NEGATIVE:
                color = KeyboardButtonColor.NEGATIVE;
                break;

            default:
                color = KeyboardButtonColor.DEFAULT;
                break;
        }

        return new KeyboardButton()
                .setAction(new KeyboardButtonAction()
                        .setLabel(button.getText())
                        .setType(KeyboardButtonActionType.TEXT)
                ).setColor(color);
    }
}
