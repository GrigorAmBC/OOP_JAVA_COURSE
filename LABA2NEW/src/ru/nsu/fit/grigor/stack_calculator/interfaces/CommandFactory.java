package ru.nsu.fit.grigor.stack_calculator.interfaces;

import java.util.List;

public abstract class CommandFactory {

  public abstract Command factoryMethod(List<String> arguments);

}