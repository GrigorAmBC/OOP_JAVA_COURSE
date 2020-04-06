package ru.nsu.fit.grigor.stack_calculator.factory;

import ru.nsu.fit.grigor.stack_calculator.command.SQRTCommand;
import ru.nsu.fit.grigor.stack_calculator.interfaces.Command;
import ru.nsu.fit.grigor.stack_calculator.interfaces.CommandFactory;

import java.util.List;

public class SQRTCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    SQRTCommand command = new SQRTCommand();
    command.build(arguments);
    return command;
  }
}
