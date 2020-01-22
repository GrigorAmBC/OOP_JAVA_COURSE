package factory;

import command.PushCommand;
import interfaces.Command;
import interfaces.CommandFactory;

import java.util.List;

public class PushCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    PushCommand command = new PushCommand();
    command.build(arguments);
    return command;
  }
}
