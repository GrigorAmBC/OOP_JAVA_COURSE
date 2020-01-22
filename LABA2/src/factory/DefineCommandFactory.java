package factory;

import command.DefineCommand;
import interfaces.Command;
import interfaces.CommandFactory;

import java.util.List;

public class DefineCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    DefineCommand command = new DefineCommand();
    command.build(arguments);
    return command;
  }
}
