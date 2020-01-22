package factory;

import command.MultiplyCommand;
import interfaces.Command;
import interfaces.CommandFactory;

import java.util.List;

public class MultiplyCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    MultiplyCommand command = new MultiplyCommand();
    command.build(arguments);
    return command;
  }
}
