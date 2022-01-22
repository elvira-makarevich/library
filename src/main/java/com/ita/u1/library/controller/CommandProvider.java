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
        commands.put(CommandName.CHECK_UNIQUENESS_PASSPORT_NUMBER, new CheckUniquenessPassportNumber());
        commands.put(CommandName.CHECK_UNIQUENESS_EMAIL, new CheckUniquenessEmail());
        commands.put(CommandName.GO_TO_MOST_POPULAR_BOOKS_PAGE, new GoToMostPopularBooksPage());
        commands.put(CommandName.VIEW_ALL_BOOKS, new ViewAllBooks());
        commands.put(CommandName.GO_TO_ALL_CLIENTS_PAGE, new GoToAllClientsPage());
        commands.put(CommandName.VIEW_ALL_CLIENTS, new ViewAllClients());
        commands.put(CommandName.GO_TO_NEW_ORDER_PAGE, new GoToNewOrderPage());
        commands.put(CommandName.FIND_CLIENT, new FindClient());
        commands.put(CommandName.FIND_BOOK, new FindBook());
        commands.put(CommandName.SAVE_ORDER, new SaveOrder());
        commands.put(CommandName.CHECK_CLIENT_ACTIVE_ORDER, new CheckClientActiveOrder());
        commands.put(CommandName.GO_TO_CLOSE_ORDER_PAGE, new GoToCloseOrderPage());
        commands.put(CommandName.FIND_ORDER_INFO, new FindOrderInfo());
        commands.put(CommandName.GO_TO_BOOK_VIOLATION_PAGE, new GoToBookViolationPage());
        commands.put(CommandName.INDICATE_BOOK_VIOLATION, new IndicateBookViolation());
        commands.put(CommandName.CLOSE_ORDER, new CloseOrder());
        commands.put(CommandName.FIND_THE_MOST_POPULAR_BOOKS, new FindTheMostPopularBooks());
        commands.put(CommandName.FIND_BOOK_COVER, new FindBookCover());
        commands.put(CommandName.GO_TO_PROFITABILITY_PAGE, new GoToProfitabilityPage());
        commands.put(CommandName.CHECK_PROFITABILITY, new CheckProfitability());
        commands.put(CommandName.GO_TO_COPY_BOOK_EDIT_PAGE, new GoToCopyBookEditPage());
        commands.put(CommandName.CHANGE_COST_PER_DAY, new ChangeCostPerDay());
        commands.put(CommandName.GO_TO_WRITING_OFF_BOOKS_PAGE, new GoToWritingOffBooksPage());
        commands.put(CommandName.FIND_BOOKS_FOR_WRITING_OFF, new FindBooksForWritingOff());
        commands.put(CommandName.WRITE_OFF_BOOKS, new WriteOffBooks());
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
