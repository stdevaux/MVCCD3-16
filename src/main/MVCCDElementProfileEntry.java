package main;

import messages.MessagesBuilder;

public class MVCCDElementProfileEntry extends MVCCDElement {

    public MVCCDElementProfileEntry(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("profile.entry.name"));
    }

    @Override
    public String baliseXMLBegin() {
        return null;
    }

    @Override
    public String baliseXMLEnd() {
        return null;
    }
}
