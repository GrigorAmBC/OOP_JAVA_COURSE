package ru.nsu.fit.grigor.stack_calculator.factory;

import ru.nsu.fit.grigor.stack_calculator.command.SubtractCommand;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;
import ru.nsu.fit.grigor.stack_calculator.interfaces.CommandFactory;

import java.util.List;

public class SubtractCommandFactory extends CommandFactory {

  @Override
  public Command factoryMethod(List<String> arguments) {
    SubtractCommand command = new SubtractCommand();
    command.build(arguments);
    return command;
  }
}
