package factory.view;

import factory.model.machine_factory.MachineFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FactoryPanel extends JPanel implements ActionListener {
  private MachineFactory factory;
  private Timer timer = new Timer(50, this);
  private JTextArea workersTextArea = new JTextArea();
  private JTextArea allMachinesTextArea = new JTextArea();
  private JTextArea machinesBuildingTextArea = new JTextArea();

  public FactoryPanel(@NotNull MachineFactory factory) {
    this.factory = factory;
    setPreferredSize(new Dimension(300, 200));
    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 2), "Machine Factory"));
    setLayout(new GridLayout(3, 1));

    Font font = new Font("Tahoma", Font.PLAIN, 20);
    workersTextArea.setFont(font);

    machinesBuildingTextArea.setFont(font);
    allMachinesTextArea.setFont(font);

    add(workersTextArea);
    add(machinesBuildingTextArea);
    add(allMachinesTextArea);

    timer.start();
  }

  private void update() {
    allMachinesTextArea.setText("Machines built: " + factory.getNumberOfMachinesBuilt());
    workersTextArea.setText("Workers: " + factory.getNumberOfWorkers());
    machinesBuildingTextArea.setText("Machines to be built: " + factory.getNumberOfBuildingMachines());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == timer)
      update();
  }
}
