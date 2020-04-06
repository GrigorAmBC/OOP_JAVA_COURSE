package ru.nsu.fit.grigor.stack_calculator.factory;

import ru.nsu.fit.grigor.stack_calculator.command.PrintCommand;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;
import ru.nsu.fit.grigor.stack_calculator.interfaces.CommandFactory;

import java.util.List;

public class PrintCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    PrintCommand command = new PrintCommand();
    command.build(arguments);
    return command;
  }
}