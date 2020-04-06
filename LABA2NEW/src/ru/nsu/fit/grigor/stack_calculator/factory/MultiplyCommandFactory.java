package ru.nsu.fit.grigor.stack_calculator.factory;

import ru.nsu.fit.grigor.stack_calculator.command.MultiplyCommand;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;
import ru.nsu.fit.grigor.stack_calculator.interfaces.CommandFactory;

import java.util.List;

public class MultiplyCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    MultiplyCommand command = new MultiplyCommand();
    command.build(arguments);
    return command;
  }
}
