package ru.nsu.fit.grigor.stack_calculator.factory;

import ru.nsu.fit.grigor.stack_calculator.command.DefineCommand;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;
import ru.nsu.fit.grigor.stack_calculator.interfaces.CommandFactory;

import java.util.List;

public class DefineCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    DefineCommand command = new DefineCommand();
    command.build(arguments);
    return command;
  }
}
