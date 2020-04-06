package ru.nsu.fit.grigor.stack_calculator.factory;

import ru.nsu.fit.grigor.stack_calculator.command.DivideCommand;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;
import ru.nsu.fit.grigor.stack_calculator.interfaces.CommandFactory;

import java.util.List;

public class DivideCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    DivideCommand command = new DivideCommand();
    command.build(arguments);
    return command;
  }
}
