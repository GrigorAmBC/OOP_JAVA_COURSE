package ru.nsu.fit.grigor.stack_calculator.factory;

import ru.nsu.fit.grigor.stack_calculator.command.PushCommand;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;
import ru.nsu.fit.grigor.stack_calculator.interfaces.CommandFactory;

import java.util.List;

public class PushCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    PushCommand command = new PushCommand();
    command.build(arguments);
    return command;
  }
}
