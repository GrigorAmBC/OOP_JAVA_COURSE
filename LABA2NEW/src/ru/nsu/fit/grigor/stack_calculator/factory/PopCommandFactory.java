package ru.nsu.fit.grigor.stack_calculator.factory;

import ru.nsu.fit.grigor.stack_calculator.command.PopCommand;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;
import ru.nsu.fit.grigor.stack_calculator.interfaces.CommandFactory;

import java.util.List;

public class PopCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    PopCommand command = new PopCommand();
    command.build(arguments);
    return command;
  }
}
