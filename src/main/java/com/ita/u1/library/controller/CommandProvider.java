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
        commands.put(CommandName.CHECK_UNIQUENESS_PASSPORT_NUMBER, new CheckUniquenessPassportNumber());
        commands.put(CommandName.CHECK_UNIQUENESS_EMAIL, new CheckUniquenessEmail());
        commands.put(CommandName.GO_TO_ALL_BOOKS_PAGE, new GoToAllBooksPage());
        commands.put(CommandName.VIEW_ALL_BOOKS, new ViewAllBooks());
        commands.put(CommandName.VIEW_BOOK, new ViewBook());
        commands.put(CommandName.GO_TO_ALL_CLIENTS_PAGE, new GoToAllClientsPage());
        commands.put(CommandName.VIEW_ALL_CLIENTS, new ViewAllClients());
        commands.put(CommandName.VIEW_CLIENT, new ViewClient());
        commands.put(CommandName.GO_TO_NEW_ORDER_PAGE, new GoToNewOrderPage());
        commands.put(CommandName.FIND_CLIENT, new FindClient());
        commands.put(CommandName.FIND_BOOK, new FindBook());

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
