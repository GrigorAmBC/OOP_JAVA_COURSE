package factory;

import command.SubstractCommand;
import interfaces.Command;
import interfaces.CommandFactory;

import java.util.List;

public class SubstractCommandFactory extends CommandFactory {

  @Override
  public Command factoryMethod(List<String> arguments) {
    SubstractCommand command = new SubstractCommand();
    command.build(arguments);
    return command;
  }
}
