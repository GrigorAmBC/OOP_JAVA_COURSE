package factory;

import command.DivideCommand;
import interfaces.Command;
import interfaces.CommandFactory;

import java.util.List;

public class DivideCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    DivideCommand command = new DivideCommand();
    command.build(arguments);
    return command;
  }
}
