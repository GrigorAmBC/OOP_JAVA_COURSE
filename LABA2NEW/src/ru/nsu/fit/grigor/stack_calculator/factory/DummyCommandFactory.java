package ru.nsu.fit.grigor.stack_calculator.factory;

import ru.nsu.fit.grigor.stack_calculator.command.DummyCommand;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;
import ru.nsu.fit.grigor.stack_calculator.interfaces.CommandFactory;

import java.util.List;

public class DummyCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    DummyCommand command = new DummyCommand();
    command.build(arguments);
    return command;
  }
}
