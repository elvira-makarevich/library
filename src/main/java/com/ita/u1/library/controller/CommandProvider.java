package com.ita.u1.library.controller;

import com.ita.u1.library.controller.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPage());
        commands.put(CommandName.ADD_NEW_AUTHOR, new AddNewAuthor());
        commands.put(CommandName.FIND_AUTHOR, new FindAuthor());
        commands.put(CommandName.ADD_NEW_BOOK, new AddNewBook());
        commands.put(CommandName.ADD_NEW_CLIENT, new AddNewClient());
        commands.put(CommandName.GO_TO_ADD_NEW_AUTHOR_PAGE, new GoToAddNewAuthorPage());
        commands.put(CommandName.GO_TO_ADD_NEW_BOOK_PAGE, new GoToAddNewBookPage());
        commands.put(CommandName.GO_TO_ADD_NEW_CLIENT_PAGE, new GoToAddNewClientPage());
        commands.put(CommandName.FIND_AUTHOR_IMAGE, new FindAuthorImage());
        commands.put(CommandName.GO_TO_AUTHOR_INFO_PAGE, new GoToAuthorInfoPage());
    }

    public Command findCommand(String name) {

        if (name == null) {
            name = CommandName.UNKNOWN_COMMAND.toString();
        }
        CommandName commandName;
        try {
            commandName = CommandName.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            commandName = CommandName.UNKNOWN_COMMAND;
        }
        Command command = commands.get(commandName);
        return command;
    }

}
