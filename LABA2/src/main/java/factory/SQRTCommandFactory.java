package factory;

import command.SQRTCommand;
import interfaces.Command;
import interfaces.CommandFactory;

import java.util.List;

public class SQRTCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    SQRTCommand command = new SQRTCommand();
    command.build(arguments);
    return command;
  }
}
