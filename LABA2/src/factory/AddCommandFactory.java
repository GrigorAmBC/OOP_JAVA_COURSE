package factory;

import command.AddCommand;
import interfaces.Command;
import interfaces.CommandFactory;

import java.util.List;

public class AddCommandFactory extends CommandFactory {
  @Override
  public Command factoryMethod(List<String> arguments) {
    AddCommand command = new AddCommand();
    command.build(arguments);
    return command;
  }
}
