package ru.nsu.fit.grigor.stack_calculator.factory;

import ru.nsu.fit.grigor.stack_calculator.command.AddCommand;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;
import ru.nsu.fit.grigor.stack_calculator.interfaces.CommandFactory;

import java.util.List;

public class AddCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    AddCommand command = new AddCommand();
    command.build(arguments);
    return command;
  }
}
