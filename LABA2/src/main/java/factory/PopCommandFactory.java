package factory;

import command.PopCommand;
import interfaces.Command;
import interfaces.CommandFactory;

import java.util.List;

public class PopCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    PopCommand command = new PopCommand();
    command.build(arguments);
    return command;
  }
}
